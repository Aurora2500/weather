package es.ulpgc.es.weather.service.application;

import java.util.ArrayList;
import java.util.List;

public class Application {
	private final String version;
	private final String scope;
	private final List<Service> services;

	public Application(String version, String scope) {
		this.version = version;
		this.scope = scope;
		this.services = new ArrayList<>();
	}

	public void registerService(Service service) {
		services.add(service);
	}

	public String version() {
		return version;
	}

	public String scope() {
		return scope;
	}

	public List<Service> services() {
		return services;
	}
}
