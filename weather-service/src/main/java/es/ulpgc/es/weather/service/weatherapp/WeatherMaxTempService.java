package es.ulpgc.es.weather.service.weatherapp;

import es.ulpgc.es.weather.service.application.Service;
import es.ulpgc.es.weather.service.application.ServiceUnit;

public class WeatherMaxTempService extends Service {
	public WeatherMaxTempService() {
		super("with-max-temperature");
	}

	@Override
	public ServiceUnit createServiceUnit() {
		return new WeatherMaxTempServiceUnit();
	}
}
