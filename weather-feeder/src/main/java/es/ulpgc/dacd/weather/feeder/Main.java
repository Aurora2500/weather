package es.ulpgc.dacd.weather.feeder;

import java.io.File;

public class Main {
	public static final double GC_top = 28.4;
	public static final double GC_BOTTOM = 27.5;
	public static final double GC_LEFT = -16;
	public static final int GC_RIGHT = -15;

	public static void main(String[] args) {
		String datalakePathString = System.getenv("DATALAKE_PATH");
		if(datalakePathString == null) {
			System.err.println("DATALAKE_PATH environment variable not set");
			System.exit(1);
		}
		String apiKey = System.getenv("API_KEY");
		if(apiKey == null) {
			System.err.println("API_KEY environment variable not set");
			System.exit(1);
		}

		File datalakePath = new File(datalakePathString);

		Area granCanariaArea = new Area(GC_top, GC_BOTTOM, GC_LEFT, GC_RIGHT);

		Controller controller = new Controller(apiKey, datalakePath, granCanariaArea);
		controller.startLoop();
	}
}
