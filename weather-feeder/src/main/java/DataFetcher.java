import com.google.gson.JsonObject;

import java.util.stream.Stream;

public class DataFetcher {
	private final String apiKey;

	public DataFetcher(String apiKey) {
		this.apiKey = apiKey;
	}

	public Stream<JsonObject> fetch() {
		return null;
	}
}
