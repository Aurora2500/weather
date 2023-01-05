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
	private final WeatherFetcher fetcher;
	private final Datalake datalake;
	Set<PlaceTimeKey> writtenData;
	private static final int MILIS_IN_HOUR = 3_600_000;


	public Controller(String apiKey, File datalakePath) {
		datalake = new FileDatalake(datalakePath);
		fetcher = new AemetFetcher(apiKey);
		writtenData = new HashSet<>();
		fillWrittenData();
		System.out.println(writtenData.size());
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
				writtenData.removeIf(key -> key.time().isBefore(LocalDateTime.now().minusHours(24)));

				try {
					List<WeatherData> data = fetcher.fetch()
						.filter(dataPoint -> !writtenData.contains(new PlaceTimeKey(dataPoint)))
						.toList();
					datalake.save(data);
					System.out.println("Saved " + data.size() + " new data points");
				} catch (IOException | InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}, 0, MILIS_IN_HOUR);
	}
}
