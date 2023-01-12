package es.ulpgc.es.weather.service.restserver;

import es.ulpgc.es.weather.service.application.*;
import spark.Spark;

import java.util.List;
import java.util.Optional;

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
				Optional<Response> validationResult = requestParameter.validate(request.params());
				if (validationResult.isPresent()) {
					fillResponse(validationResult.get(), response);
					return null;
				}
				fillResponse(serviceUnit.execute(), response);
				return null;
			});
		}
	}

	private void fillResponse(Response modelResponse, spark.Response response) {
		switch (modelResponse.status()) {
			case Ok -> response.status(200);
			case InvalidRequest -> response.status(400);
		}
		response.type(modelResponse.response().contentType());
		response.body(modelResponse.response().toString());
	}
}
