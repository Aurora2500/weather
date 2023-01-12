package es.ulpgc.dacd.weather.datamart;

import es.ulpgc.es.weather.datalake.WeatherStation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Datamart {
	List<String> savedStations() throws SQLException;
	Map<LocalDate, String> dateHashes() throws SQLException;
	void addStation(WeatherStation station) throws SQLException;
	void updateMinMax(WeatherMinMax newMinMax) throws SQLException;
}
