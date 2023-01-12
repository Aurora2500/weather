package es.ulpgc.es.weather.service.weatherapp;

import es.ulpgc.es.weather.service.application.Service;
import es.ulpgc.es.weather.service.application.ServiceUnit;

public class WeatherMinTempService extends Service {
	public WeatherMinTempService() {
		super("with-min-temperature");
	}

	@Override
	public ServiceUnit createServiceUnit() {
		return new WeatherMinTempServiceUnit();
	}
}
