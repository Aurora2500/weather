package es.ulpgc.es.weather.service.restserver;

import es.ulpgc.es.weather.service.application.*;
import spark.Spark;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RestServer {
	Application application;

	public RestServer(Application application) {
		this.application = application;
	}

	public void start() {
		String pathBase = application.version() + "/" + application.scope();
		List<Service> services = application.services();
		for (Service service : services) {
			String path = pathBase + "/" + service.endpoint();
			Spark.get(path, (request, response) -> {
				ServiceUnit serviceUnit = service.createServiceUnit();
				RequestParameter requestParameter = serviceUnit.requestParameter();
				Map<String, String> parameters = request.queryMap().toMap().entrySet().stream()
						.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));
				Optional<Response> validationResult = requestParameter.validate(parameters);
				if (validationResult.isPresent()) {
					fillResponse(validationResult.get(), response);
					return response.body();
				}
				fillResponse(serviceUnit.execute(), response);
				return response.body();
			});
		}
	}

	private void fillResponse(Response modelResponse, spark.Response response) {
		switch (modelResponse.status()) {
			case Ok -> response.status(200);
			case InvalidRequest -> response.status(400);
			case InternalError -> response.status(500);
		}
		response.type(modelResponse.response().contentType());
		response.body(modelResponse.response().toString());
	}
}
