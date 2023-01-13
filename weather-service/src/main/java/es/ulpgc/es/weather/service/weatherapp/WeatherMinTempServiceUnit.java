package es.ulpgc.es.weather.service.weatherapp;

import com.google.gson.JsonObject;
import es.ulpgc.es.weather.datalake.WeatherGson;
import es.ulpgc.es.weather.service.application.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class WeatherMinTempServiceUnit implements ServiceUnit {

	private final Datamart datamart;
	private final DateRangeParameter dateRangeParameter;

	public WeatherMinTempServiceUnit(Datamart datamart) {
		this.dateRangeParameter = new DateRangeParameter();
		this.datamart = datamart;
	}

	@Override
	public RequestParameter requestParameter() {
		return dateRangeParameter;
	}

	@Override
	public Response execute() {
		LocalDate from = dateRangeParameter.from();
		LocalDate to = dateRangeParameter.to();

		Optional<WeatherExtreme> minTemp;
		try {
			minTemp = datamart.getMinTemperature(from, to);
		} catch (SQLException e) {
			return new Response(Status.InternalError, new TextResponseBody("Internal Server Error"));
		}

		if(minTemp.isPresent()) {
			WeatherExtreme min = minTemp.get();
			JsonObject response = new JsonObject();
			response.addProperty("status", "ok");
			response.add("data", WeatherGson.timeAwareGson().toJsonTree(min));
			return new Response(Status.Ok, new SerializedResponseBody(response));
		} else {
			JsonObject response = new JsonObject();
			response.addProperty("status", "No data found");
			return new Response(Status.Ok, new SerializedResponseBody(response));
		}
	}
}
