package es.ulpgc.es.weather.service.weatherapp;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class SqliteDatamart implements Datamart {
	private final Connection connection;

	public SqliteDatamart(File file) throws SQLException {
		this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
	}

	@Override
	public Optional<WeatherExtreme> getMinTemperature(LocalDate from, LocalDate to) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
			"SELECT " +
				" t.date, t.time, t.temperature, s.id, s.name, s.latitude, s.longitude " +
				"FROM minTemp t " +
				"JOIN stations s ON t.station = s.id " +
				"WHERE t.date BETWEEN ? AND ? " +
				"ORDER BY t.temperature ASC " +
				"LIMIT 1;"
		);
		statement.setString(1, from.toString());
		statement.setString(2, to.toString());
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			String date = resultSet.getString(1);
			String time = resultSet.getString(2);
			LocalDateTime timestamp = LocalDateTime.parse(date + "T" + time);
			double temperature = resultSet.getDouble(3);
			String id = resultSet.getString(4);
			String name = resultSet.getString(5);
			double latitude = resultSet.getDouble(6);
			double longitude = resultSet.getDouble(7);
			return Optional.of(
				new WeatherExtreme(
					timestamp, temperature, new WeatherExtreme.WeatherStation(id, name, latitude, longitude)
				)
			);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<WeatherExtreme> getMaxTemperature(LocalDate from, LocalDate to) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
			"SELECT" +
				" t.date, t.time, t.temperature, s.id, s.name, s.latitude, s.longitude " +
				"FROM maxTemp t " +
				"JOIN stations s ON t.station = s.id " +
				"WHERE t.date BETWEEN ? AND ? " +
				"ORDER BY t.temperature DESC " +
				"LIMIT 1;"
		);
		statement.setString(1, from.toString());
		statement.setString(2, to.toString());
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			String date = resultSet.getString(1);
			String time = resultSet.getString(2);
			LocalDateTime timestamp = LocalDateTime.parse(date + "T" + time);
			double temperature = resultSet.getDouble(3);
			String id = resultSet.getString(4);
			String name = resultSet.getString(5);
			double latitude = resultSet.getDouble(6);
			double longitude = resultSet.getDouble(7);
			return Optional.of(
				new WeatherExtreme(
					timestamp, temperature, new WeatherExtreme.WeatherStation(id, name, latitude, longitude)
				)
			);
		} else {
			return Optional.empty();
		}
	}
}
