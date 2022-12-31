package es.ulpgc.dacd.weather.feeder;

import es.ulpgc.es.weather.datalake.Datalake;
import es.ulpgc.es.weather.datalake.FileDatalake;

import java.io.File;

public class Controller {
	public Controller(String apiKey, File datalakePath) {
		Datalake datalake = new FileDatalake(datalakePath);
		WeatherFetcher fetcher = new AemetFetcher(apiKey);
	}
}
