package es.ulpgc.es.weather.service;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		String datamartPath = args[0];
		File datamartFile = new File(datamartPath);
		if (!datamartFile.exists()) {
			System.err.println("Datamart file not found: " + datamartPath);
			System.exit(1);
		}

		Controller controller = new Controller(datamartFile);
		controller.run();
	}
}