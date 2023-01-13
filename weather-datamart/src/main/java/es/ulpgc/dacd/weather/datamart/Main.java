package es.ulpgc.dacd.weather.datamart;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String datamartPath = args[0];
        if (datamartPath == null) {
            System.err.println("Datamart file path not set");
            System.exit(1);
        }

        String datalakePathString = System.getenv("DATALAKE_PATH");
        if(datalakePathString == null) {
            System.err.println("DATALAKE_PATH environment variable not set");
            System.exit(1);
        }

        File datalakePath = new File(datalakePathString);
        if(!datalakePath.isDirectory()) {
            System.err.println("DATALAKE_PATH is not a directory: " + datalakePathString);
            System.exit(1);
        }

        Controller controller = new Controller(datamartPath, datalakePath);
        controller.run();
    }
}