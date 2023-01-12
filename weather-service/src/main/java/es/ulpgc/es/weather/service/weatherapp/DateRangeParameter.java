package es.ulpgc.es.weather.service.weatherapp;

import es.ulpgc.es.weather.service.application.RequestParameter;
import es.ulpgc.es.weather.service.application.Response;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;

public class DateRangeParameter implements RequestParameter {
	private LocalDate from;
	private LocalDate to;

	public Optional<Response> validate(Map<String, String> parameters) {
		String fromParameter = parameters.get("from");
		String toParameter = parameters.get("to");
		if(fromParameter == null || toParameter == null) {
			return null;
		}
		try {
			from = LocalDate.parse(fromParameter);
			to = LocalDate.parse(toParameter);
		} catch (DateTimeParseException e) {
			return null;
		}
		if(from.isAfter(to)) {
			return null;
		}
		return Optional.empty();
	}

	public LocalDate from() {
		return from;
	}

	public LocalDate to() {
		return to;
	}
}
