import org.example.WeatherData;

import java.io.IOException;
import java.util.List;

public interface WeatherFetcher {
	List<WeatherData> fetch() throws IOException, InterruptedException;

	List<WeatherData> fetch(Area area) throws IOException, InterruptedException;

}
