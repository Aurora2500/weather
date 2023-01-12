package es.ulpgc.es.weather.service.weatherapp;

import es.ulpgc.es.weather.service.application.RequestParameter;
import es.ulpgc.es.weather.service.application.Response;
import es.ulpgc.es.weather.service.application.ServiceUnit;

import java.time.LocalDate;

public class WeatherMinTempServiceUnit implements ServiceUnit {

	private final DateRangeParameter dateRangeParameter;

	public WeatherMinTempServiceUnit() {
		this.dateRangeParameter = new DateRangeParameter();
	}

	@Override
	public RequestParameter requestParameter() {
		return dateRangeParameter;
	}

	@Override
	public Response execute() {
		LocalDate from = dateRangeParameter.from();
		LocalDate to = dateRangeParameter.to();

		return null;
	}
}
