package es.ulpgc.dacd.weather.feeder;

public class Area {
	private final double top;
	private final double bottom;
	private final double left;
	private final double right;

	public Area(double top, double bottom, double left, double right) {
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}

	public boolean contains(double lat, double lon) {
		if (lat > top || lat < bottom) {
			return false;
		}
		if (left < right) {
			return left < lon && lon < right;
		} else {
			return right < lon || lon < left;
		}
	}
}
