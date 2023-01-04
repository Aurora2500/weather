package es.ulpgc.dacd.weather.feeder;

import es.ulpgc.es.weather.datalake.Datalake;
import es.ulpgc.es.weather.datalake.FileDatalake;
import es.ulpgc.es.weather.datalake.WeatherData;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller {
	public Controller(String apiKey, File datalakePath) {
		Datalake datalake = new FileDatalake(datalakePath);
		WeatherFetcher fetcher = new AemetFetcher(apiKey);
		try {
			List<WeatherData> data = fetcher.fetch().toList();
			// System.out.println("Fetched " + data.size() + " records");
			datalake.save(data);


		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
