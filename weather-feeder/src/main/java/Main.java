public class Main {
    public static void main(String[] args) {
        String datalakePath = System.getenv("DATALAKE_PATH");
        String apiKey = System.getenv("API_KEY");

        DatalakeFeeder feeder = new DatalakeFeeder(datalakePath);
        DataFetcher fetcher
        System.out.println("Hello, feeder!");
    }
}
