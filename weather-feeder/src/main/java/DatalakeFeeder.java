import com.google.gson.JsonObject;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatalakeFeeder {
	private final Path datalakePath;

	public DatalakeFeeder(Path datalakePath) {
		this.datalakePath = datalakePath;
	}

	public void feed(Stream<JsonObject> stream) {
		LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
		String newDataPoints = stream
			.filter(o -> {
				// make sure the reading is from a station in Gran Canaria
				double lat = o.get("lat").getAsDouble();
				double lon = o.get("lon").getAsDouble();
				return (-16.0 < lon) && (lon < -15) && (27.5 < lat) && (lat < 28.4);
			})
			.filter(o -> o.has("fint"))
			.filter(o -> {
				String timestamp = o.get("fint").getAsString();
				LocalDateTime time = LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME);
				// TODO: potentially incorrect?
				return time.isAfter(now);
			}).map(JsonObject::toString)
			.collect(Collectors.joining("\n","", "\n"));
		String today = now.format(DateTimeFormatter.BASIC_ISO_DATE);
		Path filePath = datalakePath.resolve(today + ".events.ndjson");
		System.out.println("Writing to " + filePath);
	}
}
