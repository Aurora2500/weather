package es.ulpgc.dacd.weather.feeder;

import es.ulpgc.es.weather.datalake.Datalake;
import es.ulpgc.es.weather.datalake.FileDatalake;
import es.ulpgc.es.weather.datalake.WeatherData;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Controller {
	private static final int HOURS = 24;
	private static final int MILLIS_IN_HOUR = 3_600_000;
	private final WeatherFetcher fetcher;
	private final Datalake datalake;
	private final Optional<Area> fetchArea;
	Set<PlaceTimeKey> writtenData;

	private Controller(String apiKey, File datalakePath, Optional<Area> fetchArea) {
		this.fetcher = new AemetFetcher(apiKey);
		this.datalake = new FileDatalake(datalakePath);
		this.fetchArea = fetchArea;
		writtenData = new HashSet<>();
		fillWrittenData();
	}


	public Controller(String apiKey, File datalakePath) {
		this(apiKey, datalakePath, Optional.empty());
	}

	public Controller(String apiKey, File datalakePath, Area area) {
		this(apiKey, datalakePath, Optional.of(area));
	}

	private void fillWrittenData() {
			datalake.readRange(LocalDate.now().minusDays(1), LocalDate.now())
				.map(PlaceTimeKey::new)
				.forEach(writtenData::add);
	}

	public void startLoop() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				writtenData.removeIf(key -> key.time().isBefore(LocalDateTime.now().minusHours(HOURS)));

				try {

					List<WeatherData> data = (fetchArea.isPresent()? fetcher.fetch(fetchArea.get()) : fetcher.fetch())
						.filter(dataPoint -> !writtenData.contains(new PlaceTimeKey(dataPoint)))
						.peek(datapoint -> writtenData.add(new PlaceTimeKey(datapoint)))
						.toList();
					datalake.save(data);
					System.out.println("Saved " + data.size() + " new data points");
				} catch (IOException | InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}, 0, MILLIS_IN_HOUR);
	}
}
