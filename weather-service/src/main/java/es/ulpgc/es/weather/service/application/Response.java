package es.ulpgc.es.weather.service.application;

public class Response {
	private final Status status;
	private final ResponseBody response;

	public Response(Status status, ResponseBody response) {
		this.status = status;
		this.response = response;
	}

	public Status status() {
		return status;
	}

	public ResponseBody response() {
		return response;
	}
}
