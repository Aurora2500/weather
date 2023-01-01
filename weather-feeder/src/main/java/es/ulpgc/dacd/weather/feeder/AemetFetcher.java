package es.ulpgc.dacd.weather.feeder;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import es.ulpgc.es.weather.datalake.WeatherData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class AemetFetcher implements WeatherFetcher{
	private final String apiKey;
	private static final String URL = "https://opendata.aemet.es/opendata/api/observacion/convencional/todas";

	public AemetFetcher(String apiKey) {
		this.apiKey = apiKey;
	}

	private static WeatherData toWeatherData(JsonObject o) {
		String time = o.get("fint").getAsString();
		LocalDateTime timestamp = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(time));
		String id = o.get("idema").getAsString();
		String name = o.get("ubi").getAsString();
		double maxTemp = o.get("tamax").getAsDouble();
		double minTemp = o.get("tamin").getAsDouble();

		return new WeatherData(timestamp, id, name, maxTemp, minTemp);
	}

	private Stream<JsonObject> fetchData() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest req1 = HttpRequest.newBuilder()
			.uri(URI.create(URL))
			.header("api_key", apiKey)
			.GET()
			.build();

		HttpResponse<String> response1 = client.send(req1, HttpResponse.BodyHandlers.ofString(Charset.forName("ISO-8859-15")));

		String link = new Gson().fromJson(response1.body(), JsonObject.class).get("datos").getAsString();

		HttpRequest req2 = HttpRequest.newBuilder()
			.uri(URI.create(link))
			.GET()
			.build();

		HttpResponse<String> response2 = client.send(req2, HttpResponse.BodyHandlers.ofString(Charset.forName("ISO-8859-15")));

		JsonArray data = new Gson().fromJson(response2.body(), JsonArray.class);
		return data
			.asList()
			.stream()
			.map(JsonObject.class::cast)
			.filter(o -> {
				// make sure all the fields are present
				return o.has("fint")
					&& o.has("tamax")
					&& o.has("tamin");
			});
	}

	public Stream<WeatherData> fetch() throws IOException, InterruptedException {
			return fetchData()
			.map(AemetFetcher::toWeatherData);
	}

	@Override
	public Stream<WeatherData> fetch(Area area) throws IOException, InterruptedException {
		return fetchData()
			.filter(o -> {
				double lat = o.get("lat").getAsDouble();
				double lon = o.get("lon").getAsDouble();
				return area.contains(lat, lon);
			})
			.map(AemetFetcher::toWeatherData);
	}
}
