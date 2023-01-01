package es.ulpgc.dacd.weather.feeder;

import es.ulpgc.es.weather.datalake.WeatherData;

import java.io.IOException;
import java.util.stream.Stream;

public interface WeatherFetcher {
	Stream<WeatherData> fetch() throws IOException, InterruptedException;

	Stream<WeatherData> fetch(Area area) throws IOException, InterruptedException;

}
