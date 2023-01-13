package es.ulpgc.es.weather.service.weatherapp;

import java.time.LocalDateTime;

public class WeatherExtreme {
	private final LocalDateTime timestamp;
	private final double temperature;
	private final WeatherStation station;

	public WeatherExtreme(LocalDateTime timestamp, double temperature, WeatherStation station) {
		this.timestamp = timestamp;
		this.temperature = temperature;
		this.station = station;
	}

	public LocalDateTime timestamp() {
		return timestamp;
	}

	public double temperature() {
		return temperature;
	}

	public WeatherStation station() {
		return station;
	}

	public static class WeatherStation {
		private final String id;
		private final String name;
		private final double latitude;
		private final double longitude;

		public WeatherStation(String id, String name, double latitude, double longitude) {
			this.id = id;
			this.name = name;
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public String id() {
			return id;
		}

		public String name() {
			return name;
		}

		public double latitude() {
			return latitude;
		}

		public double longitude() {
			return longitude;
		}
	}
}
