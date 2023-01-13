package es.ulpgc.es.weather.service.weatherapp;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public interface Datamart {
	Optional<WeatherExtreme> getMinTemperature(LocalDate from, LocalDate to) throws SQLException;
	Optional<WeatherExtreme> getMaxTemperature(LocalDate from, LocalDate to) throws SQLException;
}
