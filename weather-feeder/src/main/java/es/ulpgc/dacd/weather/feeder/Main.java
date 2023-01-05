package es.ulpgc.dacd.weather.feeder;

import java.io.File;

public class Main {
	public static void main(String[] args) {
		String datalakePathString = System.getenv("DATALAKE_PATH");
		String apiKey = System.getenv("API_KEY");

		File datalakePath = new File(datalakePathString);

		Controller controller = new Controller(apiKey, datalakePath);
		controller.startLoop();
	}
}
