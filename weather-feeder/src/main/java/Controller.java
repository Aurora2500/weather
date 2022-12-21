import org.example.Datalake;
import org.example.FileDatalake;

import java.io.File;

public class Controller {
	public Controller(String apiKey, File datalakePath) {
		Datalake datalake = new FileDatalake(datalakePath);
		WeatherFetcher fetcher = new AemetFetcher(apiKey);
	}
}
