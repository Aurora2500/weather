package es.ulpgc.es.weather.service;

import es.ulpgc.es.weather.service.application.Application;
import es.ulpgc.es.weather.service.restserver.RestServer;
import es.ulpgc.es.weather.service.weatherapp.Datamart;
import es.ulpgc.es.weather.service.weatherapp.SqliteDatamart;
import es.ulpgc.es.weather.service.weatherapp.WeatherMaxTempService;
import es.ulpgc.es.weather.service.weatherapp.WeatherMinTempService;

import java.io.File;
import java.sql.SQLException;

public class Controller {
	private final Datamart datamart;
	public Controller(File datamartFile) {
		try {
			datamart = new SqliteDatamart(datamartFile);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void run() {
		Application app = new Application("v1", "places");

		app.registerService(new WeatherMinTempService(datamart));
		app.registerService(new WeatherMaxTempService(datamart));

		new RestServer(app).start();
	}
}
