package es.ulpgc.es.weather.service.application;

import es.ulpgc.es.weather.datalake.WeatherGson;

public class SerializedResponseBody implements ResponseBody {
	private final Object response;

	public SerializedResponseBody(Object response) {
		this.response = response;
	}

	@Override
	public String contentType() {
		return "application/json";
	}

	@Override
	public String toString() {
		return WeatherGson.timeAwareGson().toJson(response);
	}
}
