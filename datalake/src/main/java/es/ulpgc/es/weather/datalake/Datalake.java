package es.ulpgc.es.weather.datalake;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface Datalake {
	Stream<WeatherData> read();

	Stream<WeatherData> readDate(LocalDate date);
	Stream<WeatherData> readRange(LocalDate from, LocalDate to);
	void save(List<WeatherData> data);
}
