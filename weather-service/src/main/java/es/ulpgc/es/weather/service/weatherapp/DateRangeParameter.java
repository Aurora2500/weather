package es.ulpgc.es.weather.service.weatherapp;

import es.ulpgc.es.weather.service.application.RequestParameter;
import es.ulpgc.es.weather.service.application.Response;
import es.ulpgc.es.weather.service.application.Status;
import es.ulpgc.es.weather.service.application.TextResponseBody;

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
			return Optional.of(new Response(Status.InvalidRequest, new TextResponseBody("Missing parameters")));
		}
		try {
			from = LocalDate.parse(fromParameter);
			to = LocalDate.parse(toParameter);
		} catch (DateTimeParseException e) {
			return Optional.of(new Response(Status.InvalidRequest, new TextResponseBody("Invalid date format")));
		}
		if(from.isAfter(to)) {
			return Optional.of(new Response(Status.InvalidRequest, new TextResponseBody("Invalid date range, \"from\" must be before \"to\"")));
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
