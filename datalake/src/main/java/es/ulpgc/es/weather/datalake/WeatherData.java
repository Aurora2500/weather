package es.ulpgc.es.weather.datalake;

import java.time.LocalDateTime;

public class WeatherData {
	private final LocalDateTime timestamp;
	private final String stationId;
	private final String stationName;
	private final double maxTemperature;
	private final double minTemperature;
	private  final double lat;
	private final double lon;

	public WeatherData(LocalDateTime timestamp, String stationId, String stationName, double maxTemperature, double minTemperature, double lat, double lon) {
		this.timestamp = timestamp;
		this.stationId = stationId;
		this.stationName = stationName;
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
		this.lat = lat;
		this.lon = lon;
	}

	public LocalDateTime timestamp() {
		return timestamp;
	}

	public WeatherStation station() {
		return new WeatherStation(stationId, stationName, lat, lon);
	}

	public String stationId() {
		return stationId;
	}

	public String stationName() {
		return stationName;
	}

	public double maxTemperature() {
		return maxTemperature;
	}

	public double minTemperature() {
		return minTemperature;
	}

	public double lat() {
		return lat;
	}

	public double lon() {
		return lon;
	}
}
