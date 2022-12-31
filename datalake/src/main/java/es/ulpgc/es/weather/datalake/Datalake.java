package es.ulpgc.es.weather.datalake;

import java.time.LocalDate;
import java.util.List;

public interface Datalake {
	List<WeatherData> read();

	List<WeatherData> read(LocalDate date);
	void save(List<WeatherData> data);
}
