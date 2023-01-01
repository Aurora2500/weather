package es.ulpgc.es.weather.datalake;

import java.time.LocalDateTime;

public class WeatherData {
	LocalDateTime timestamp;
	String stationId;
	String stationName;
	double maxTemperature;
	double minTemperature;

	public WeatherData(LocalDateTime timestamp, String stationId, String stationName, double maxTemperature, double minTemperature) {
		this.timestamp = timestamp;
		this.stationId = stationId;
		this.stationName = stationName;
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
	}
}
