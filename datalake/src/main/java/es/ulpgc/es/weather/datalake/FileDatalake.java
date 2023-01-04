package es.ulpgc.es.weather.datalake;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FileDatalake implements Datalake {
	private final File datalakePath;

	public FileDatalake(File datalakePath) {
		this.datalakePath = datalakePath;
		// assure that the datalake directory exists
		datalakePath.mkdirs();
	}

	@Override
	public List<WeatherData> read() {
		return null;
	}

	@Override
	public List<WeatherData> read(LocalDate date) {

		return null;
	}

	@Override
	public void save(List<WeatherData> data) {
		data.stream()
			.collect(Collectors.groupingBy(events -> associatedFileName(events.timestamp())))
			.forEach((fileName, events) -> {
				File dayPath = new File(datalakePath, fileName);
				try {
					Writer writer = new BufferedWriter(new FileWriter(dayPath, true));
					events.forEach(event -> {
						String serialized = WeatherGson.timeAwareGson().toJson(event);
						try {
							writer.write(serialized);
							writer.write("\n");
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					});
					writer.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
	}

	private String associatedFileName(LocalDateTime timestamp) {
		return timestamp.format(DateTimeFormatter.BASIC_ISO_DATE) + ".events";
	}
}
