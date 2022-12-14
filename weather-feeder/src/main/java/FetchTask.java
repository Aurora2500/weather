import com.google.gson.JsonObject;

import java.util.TimerTask;
import java.util.stream.Stream;

public class FetchTask extends TimerTask {
	private final DataFetcher fetcher;
	private final DatalakeFeeder feeder;

	public FetchTask(DataFetcher fetcher, DatalakeFeeder feeder) {
		this.fetcher = fetcher;
		this.feeder = feeder;
	}

	@Override
	public void run() {
		Stream<JsonObject> dataPoints = fetcher.fetch();
		feeder.feed(dataPoints);
	}
}
