package es.ulpgc.es.weather.datalake;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDatalake implements Datalake {
	private final File datalakePath;

	public FileDatalake(File datalakePath) {
		this.datalakePath = datalakePath;
		// assure that the datalake directory exists
		datalakePath.mkdirs();
	}

	@Override
	public Stream<WeatherData> read() {
		return null;
	}

	@Override
	public Stream<WeatherData> readDate(LocalDate date) {
		File dayPath = new File(datalakePath, associatedFileName(date));
		if (!dayPath.exists()) {
			return Stream.empty();
		}
		try {
			return new BufferedReader(new FileReader(dayPath))
				.lines()
				.map(FileDatalake::parseLine);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static WeatherData parseLine(String s) {
		return WeatherGson.timeAwareGson().fromJson(s, WeatherData.class);
	}

	public Stream<WeatherData> readRange(LocalDate from, LocalDate to) {
		if(from.isAfter(to)) {
			return Stream.empty();
		}
		return Stream.concat(
			readDate(from),
			readRange(from.plusDays(1), to)
		);
	}

	@Override
	public void save(List<WeatherData> data) {
		data.stream()
			.collect(Collectors.groupingBy(events -> associatedFileName(events.timestamp().toLocalDate())))
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

	private String associatedFileName(LocalDate date) {
		return date.format(DateTimeFormatter.BASIC_ISO_DATE) + ".events";
	}
}
