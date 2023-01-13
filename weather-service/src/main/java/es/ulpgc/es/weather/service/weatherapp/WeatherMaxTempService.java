package es.ulpgc.es.weather.service.weatherapp;

import es.ulpgc.es.weather.service.application.Service;
import es.ulpgc.es.weather.service.application.ServiceUnit;

public class WeatherMaxTempService extends Service {
	private final Datamart datamart;
	public WeatherMaxTempService(Datamart datamart) {
		super("with-max-temperature");
		this.datamart = datamart;
	}

	@Override
	public ServiceUnit createServiceUnit() {
		return new WeatherMaxTempServiceUnit(datamart);
	}
}
