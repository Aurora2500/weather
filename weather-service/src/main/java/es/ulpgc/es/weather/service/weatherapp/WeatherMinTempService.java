package es.ulpgc.es.weather.service.weatherapp;

import es.ulpgc.es.weather.service.application.Service;
import es.ulpgc.es.weather.service.application.ServiceUnit;

public class WeatherMinTempService extends Service {
	private final Datamart datamart;
	public WeatherMinTempService(Datamart datamart) {
		super("with-min-temperature");
		this.datamart = datamart;
	}

	@Override
	public ServiceUnit createServiceUnit() {
		return new WeatherMinTempServiceUnit(datamart);
	}
}
