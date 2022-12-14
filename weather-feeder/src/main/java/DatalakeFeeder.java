import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatalakeFeeder {
    private final String datalakePath;
    private static Pattern TIME_REGEX = Pattern.compile("^(?year:\\d{4})-(?month:\\d{2})-(?day:\\d{2})T(?hour:\\d{2}):(?min:\\d{2}):(?sec:\\d{2})$");

    public DatalakeFeeder(String datalakePath) {
        this.datalakePath = datalakePath;
    }

    public void Feed(Stream<JsonObject> stream) {
        stream
            .filter(o -> {
                // make sure the reading is froma station in Gran Canaria
                double lat = o.get("lat").getAsDouble();
                double lon = o.get("lon").getAsDouble();
                return (-16.0 < lon) && (lon < -15) && (27.5 < lat) && (lat < 28.4);
            })
            .filter(o -> o.has("fint"))
            .collect(Collectors.groupingBy(o -> {
              String time = o.get("fint").getAsString();
              Matcher m = TIME_REGEX.matcher(time);
              String date = m.group(1) + m.group(2) + m.group(3);
            }));
    }
}
