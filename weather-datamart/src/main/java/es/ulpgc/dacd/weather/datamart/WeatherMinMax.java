package es.ulpgc.dacd.weather.datamart;

import es.ulpgc.es.weather.datalake.WeatherData;
import es.ulpgc.es.weather.datalake.WeatherStation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

public class WeatherMinMax {
	private final LocalDate date;
	private Extreme maximum;
	private Extreme minimum;
	private final String hash;

	public WeatherMinMax(LocalDate date, Stream<WeatherData> weatherData, String hash) {
		this.date = date;
		this.hash = hash;
		weatherData.forEach(this::updateExtremes);
	}

	private void updateExtremes(WeatherData weatherData) {
		if (maximum == null || weatherData.maxTemperature() > maximum.temperature) {
			maximum = new Extreme(weatherData);
		}
		if (minimum == null || weatherData.minTemperature() < minimum.temperature) {
			minimum = new Extreme(weatherData);
		}
	}

	public LocalDate date() {
		return date;
	}

	public Extreme maximum() {
		return maximum;
	}

	public Extreme minimum() {
		return minimum;
	}

	public String hash() {
		return hash;
	}

	public static class Extreme {
		private final LocalTime time;
		private final double temperature;
		private final WeatherStation station;

		public Extreme(WeatherData weatherData) {
			this.time = weatherData.timestamp().toLocalTime();
			this.temperature = weatherData.maxTemperature();
			this.station = weatherData.station();
		}

		public LocalTime time() {
			return time;
		}

		public double temperature() {
			return temperature;
		}

		public WeatherStation station() {
			return station;
		}
	}
}
