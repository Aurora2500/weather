package es.ulpgc.es.weather.service.weatherapp;

import com.google.gson.JsonObject;
import es.ulpgc.es.weather.datalake.WeatherGson;
import es.ulpgc.es.weather.service.application.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class WeatherMaxTempServiceUnit implements ServiceUnit {

	private final Datamart datamart;
	private final DateRangeParameter dateRangeParameter;

	public WeatherMaxTempServiceUnit(Datamart datamart) {
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

		Optional<WeatherExtreme> maxTemp;
		try {
			maxTemp = datamart.getMaxTemperature(from, to);
		} catch (SQLException e) {
			return new Response(Status.InternalError, new TextResponseBody("Internal Server Error"));
		}

		if (maxTemp.isPresent()) {
			WeatherExtreme max = maxTemp.get();
			JsonObject response = new JsonObject();
			response.addProperty("status", "ok");
			response.add("data", WeatherGson.timeAwareGson().toJsonTree(max));
			return new Response(Status.Ok, new SerializedResponseBody(response));
		} else {
			JsonObject response = new JsonObject();
			response.addProperty("status", "No data found");
			return new Response(Status.Ok, new SerializedResponseBody(response));
		}
	}
}
