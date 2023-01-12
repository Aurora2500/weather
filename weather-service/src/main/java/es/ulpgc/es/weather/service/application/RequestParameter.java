package es.ulpgc.es.weather.service.application;

import java.util.Map;
import java.util.Optional;

public interface RequestParameter {
	Optional<Response> validate(Map<String, String> parameters);
}
