package es.ulpgc.dacd.weather.feeder;

import es.ulpgc.es.weather.datalake.WeatherData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public record PlaceTimeKey(String place, LocalDateTime time) {
		public PlaceTimeKey {
				Objects.requireNonNull(place);
				Objects.requireNonNull(time);
				time = time.truncatedTo(ChronoUnit.SECONDS);

		}

	public PlaceTimeKey(WeatherData data) {
		this(data.stationId(), data.timestamp());
	}
}
