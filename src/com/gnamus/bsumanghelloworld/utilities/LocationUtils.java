package com.gnamus.bsumanghelloworld.utilities;

import java.text.DecimalFormat;

import android.location.Location;

public class LocationUtils {

	public static double calculateDistance(double fromLatitude,
			double fromLongitude, double toLatitude, double toLongitude) {

		float results[] = new float[1];

		try {
			Location.distanceBetween(fromLatitude, fromLongitude, toLatitude,
					toLongitude, results);
		} catch (Exception e) {
			if (e != null)
				e.printStackTrace();
		}

		int dist = (int) results[0];
		if (dist <= 0)
			return 0D;

		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		results[0] = (float) (results[0] * 0.000621371192);
		String distance = decimalFormat.format(results[0]);
		double d = Double.parseDouble(distance);
		return d;
	}

}