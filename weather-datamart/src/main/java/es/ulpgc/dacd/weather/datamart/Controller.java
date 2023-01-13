package es.ulpgc.dacd.weather.datamart;

import es.ulpgc.es.weather.datalake.Datalake;
import es.ulpgc.es.weather.datalake.FileDatalake;
import es.ulpgc.es.weather.datalake.WeatherData;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class Controller {
	private final Datalake datalake;
	private final Datamart datamart;
	private final Set<String> knownStations;
	private static final long MILLIS_IN_HOUR = 3600_000;

	public Controller(String datamartPath, File datalakePath) {
		try {
			this.datamart = new SqliteDatamart(datamartPath);
			this.knownStations = new HashSet<>(datamart.savedStations());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		datalake = new FileDatalake(datalakePath);
	}

	private void updateDatamart() {
		Map<LocalDate, String> lakeHash = datalake.hash();
		Set<LocalDate> datesToUpdate = daysToUpdate(lakeHash);
		for (LocalDate date : datesToUpdate) {
			System.out.println("Updating " + date);
			String hash = lakeHash.get(date);
			Stream<WeatherData> weatherData = datalake.readDate(date);
			WeatherMinMax newMinMax = new WeatherMinMax(date, weatherData, hash);
			try {
				if(!knownStations.contains(newMinMax.minimum().station().id())) {
					datamart.addStation(newMinMax.minimum().station());
					knownStations.add(newMinMax.minimum().station().id());
				}
				if(!knownStations.contains(newMinMax.maximum().station().id())) {
					datamart.addStation(newMinMax.maximum().station());
					knownStations.add(newMinMax.maximum().station().id());
				}
				datamart.updateMinMax(newMinMax);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Done updating");
	}

	public void run() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				updateDatamart();
			}
		}, 0, MILLIS_IN_HOUR);
	}

	private Set<LocalDate> daysToUpdate(Map<LocalDate, String> lakeHash) {
		Map<LocalDate, String> datamartHash;
		try {
			datamartHash = datamart.dateHashes();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		Set<LocalDate> lakeDates = lakeHash.keySet();
		Set<LocalDate> datamartDates = datamartHash.keySet();

		Set<LocalDate> updateDates = new HashSet<>(lakeDates);
		updateDates.removeAll(datamartDates);

		datamartHash.forEach((date, hash) -> {
			if (lakeHash.containsKey(date) && (!lakeHash.get(date).equals(hash))) {
				updateDates.add(date);
			}
		});

		return updateDates;
	}
}
