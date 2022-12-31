package es.ulpgc.es.weather.datalake;

import java.time.Instant;

public class WeatherData {
	Instant timestamp;
	String stationId;
	String stationName;
	double maxTemperature;
	double minTemperature;
}
