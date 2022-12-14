import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Timer;

public class Main {
	public static void main(String[] args) {
		String datalakePathString = System.getenv("DATALAKE_PATH");
		String apiKey = System.getenv("API_KEY");

		Path datalakePath = Paths.get(datalakePathString);

		DatalakeFeeder feeder = new DatalakeFeeder(datalakePath);
		DataFetcher fetcher = new DataFetcher(apiKey);
		FetchTask task = new FetchTask(fetcher, feeder);

		Timer timer = new Timer();
		timer.schedule(task,0 , 1000 * 60 * 60);
	}
}
