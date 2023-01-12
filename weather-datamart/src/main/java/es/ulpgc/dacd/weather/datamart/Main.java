package es.ulpgc.dacd.weather.datamart;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String datamartPath = args[0];


        String datalakePathString = System.getenv("DATALAKE_PATH");
        File datalakePath = new File(datalakePathString);

        Controller controller = new Controller(datamartPath, datalakePath);
        controller.run();
    }
}