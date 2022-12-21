package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FileDatalake implements Datalake {
	private final File datalakePath;
	private LocalDateTime lastRecord;

	public FileDatalake(File datalakePath) {
		this.datalakePath = datalakePath;
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

	}
}
