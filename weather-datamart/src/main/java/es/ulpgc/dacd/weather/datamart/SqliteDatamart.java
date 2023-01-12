package es.ulpgc.dacd.weather.datamart;

import es.ulpgc.es.weather.datalake.WeatherStation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteDatamart implements Datamart {
	Connection connection;

	public SqliteDatamart(String path) throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:" + path);
		assertTables();
	}

	private void assertTables() throws SQLException {
		assertStationTable();
		assertDataTables();
	}

	private void assertStationTable() throws SQLException {
		connection.createStatement().execute("CREATE TABLE IF NOT EXISTS stations (" +
			" id TEXT PRIMARY KEY," +
			" name TEXT," +
			" latitude REAL," +
			" longitude REAL" +
			");");
	}

	private void assertDataTables() throws SQLException {
		for(String extreme: List.of("max", "min")) {
			connection.createStatement().execute("CREATE TABLE IF NOT EXISTS " + extreme + "Temp (" +
				" date TEXT PRIMARY KEY," +
				" temperature REAL," +
				" station TEXT," +
				" time TEXT," +
				" hash TEXT," +
				" FOREIGN KEY(station) REFERENCES stations(id)" +
				");");
		}
	}

	public List<String> savedStations() throws SQLException {
		ResultSet resultSet = connection.createStatement().executeQuery("SELECT id FROM stations;");
		List<String> stations = new ArrayList<>();
		while(resultSet.next()) {
			stations.add(resultSet.getString("id"));
		}
		return stations;
	}

	@Override
	public Map<LocalDate, String> dateHashes() throws SQLException {
		ResultSet result = connection.createStatement().executeQuery(
			"SELECT " +
				" max.date, iif(max.hash = min.hash, max.hash, '') as hash " +
				"FROM maxTemp max " +
				"INNER JOIN minTemp min " +
				"ON max.date = min.date;");
		HashMap<LocalDate, String> hashes = new HashMap<>();
		while(result.next()) {
			hashes.put(LocalDate.parse(result.getString("date")), result.getString("hash"));
		}
		return hashes;
	}

	@Override
	public void addStation(WeatherStation station) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO stations(id, name, latitude, longitude) VALUES (?, ?, ?, ?);");
		statement.setString(1, station.id());
		statement.setString(2, station.name());
		statement.setDouble(3, station.latitude());
		statement.setDouble(4, station.longitude());
		statement.execute();
	}

	@Override
	public void updateMinMax(WeatherMinMax newMinMax) throws SQLException {
		PreparedStatement minStatement = connection.prepareStatement("INSERT OR REPLACE INTO minTemp(date, temperature, station, time, hash) VALUES (date(?/1000, 'unixepoch'), ?, ?, ?, ?);");
		minStatement.setDate(1, Date.valueOf(newMinMax.date()));
		minStatement.setDouble(2, newMinMax.minimum().temperature());
		minStatement.setString(3, newMinMax.minimum().station().id());
		minStatement.setString(4, newMinMax.minimum().time().toString());
		minStatement.setString(5, newMinMax.hash());
		minStatement.execute();

		PreparedStatement maxStatement = connection.prepareStatement("INSERT OR REPLACE INTO maxTemp(date, temperature, station, time, hash) VALUES (date(?/1000, 'unixepoch'), ?, ?, ?, ?);");
		maxStatement.setDate(1, Date.valueOf(newMinMax.date()));
		maxStatement.setDouble(2, newMinMax.maximum().temperature());
		maxStatement.setString(3, newMinMax.maximum().station().id());
		maxStatement.setString(4, newMinMax.maximum().time().toString());
		maxStatement.setString(5, newMinMax.hash());
		maxStatement.execute();
	}
}
