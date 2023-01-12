package es.ulpgc.es.weather.service.weatherapp;

import es.ulpgc.es.weather.service.application.RequestParameter;
import es.ulpgc.es.weather.service.application.Response;
import es.ulpgc.es.weather.service.application.ServiceUnit;

public class WeatherMaxTempServiceUnit implements ServiceUnit {

	private final DateRangeParameter dateRangeParameter;

	public WeatherMaxTempServiceUnit() {
		this.dateRangeParameter = new DateRangeParameter();
	}

	@Override
	public RequestParameter requestParameter() {
		return dateRangeParameter;
	}

	@Override
	public Response execute() {
		return null;
	}
}
