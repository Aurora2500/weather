package es.ulpgc.es.weather.datalake;

public class WeatherStation {
	private final String id;
	private final String name;
	private final double latitude;
	private final double longitude;

	public WeatherStation(String id, String name, double latitude, double longitude) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String id() {
		return id;
	}

	public String name() {
		return name;
	}

	public double latitude() {
		return latitude;
	}

	public double longitude() {
		return longitude;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		WeatherStation other = (WeatherStation) obj;
		return id.equals(other.id);
	}
}
