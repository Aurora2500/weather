package es.ulpgc.es.weather.service.application;

public class TextResponseBody implements ResponseBody {
	private final String text;

	public TextResponseBody(String text) {
		this.text = text;
	}

	@Override
	public String contentType() {
		return "text/plain";
	}

	@Override
	public String toString() {
		return text;
	}
}
