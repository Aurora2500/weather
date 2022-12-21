import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.example.WeatherData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.List;

public class AemetFetcher implements WeatherFetcher{
	private final String apiKey;

	public AemetFetcher(String apiKey) {
		this.apiKey = apiKey;
	}

	public List<WeatherData> fetch() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest req1 = HttpRequest.newBuilder()
				.uri(URI.create(""))
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

		// TODO
		return null;
	}

	@Override
	public List<WeatherData> fetch(Area area) {
		return null;
	}
}
