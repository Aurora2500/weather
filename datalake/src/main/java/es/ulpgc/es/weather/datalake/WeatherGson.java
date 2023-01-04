package es.ulpgc.es.weather.datalake;

import com.google.gson.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherGson {
	public static Gson timeAwareGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
		builder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (jsonElement, type, jsonDeserializationContext) -> LocalDateTime.parse(jsonElement.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		return builder.create();
	}
}
