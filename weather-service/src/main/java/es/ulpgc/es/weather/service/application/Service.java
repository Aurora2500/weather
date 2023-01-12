package es.ulpgc.es.weather.service.application;

public abstract class Service {
	private final String endpoint;

	protected Service(String endpoint) {
		this.endpoint = endpoint;
	}

	public String endpoint() {
		return endpoint;
	}

	public abstract ServiceUnit createServiceUnit();
}
