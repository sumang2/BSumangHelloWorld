package com.gnamus.bsumanghelloworld.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	SharedPreferences pref;
	Editor editor;
	Context mContext;

	int PRIVATE_MODE = 0;

	private static final String PREFS_NAME = "LocationPref";
	public static final String PREFS_KEY = "LocationKey";

	public static final String CURR_LAT = "CurrentLatitude";
	public static final String CURR_LNG = "CurrentLongitude";

	public SessionManager(Context context) {
		this.mContext = context;
		pref = mContext.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public void storeLocations(String locations) {

		// clear old locations, then add updated locations
		editor.clear();
		editor.putString(PREFS_KEY, locations);
		editor.commit();

	}

	public String getLocations() {

		String locations = pref.getString(PREFS_KEY, null);
		return locations;

	}

	public void setCurrentLocation(String lat, String lng) {

		editor.putString(CURR_LAT, lat);
		editor.putString(CURR_LNG, lng);

		editor.commit();
	}

	public void setCurrentLatitude(double lat) {
		editor.putLong(CURR_LAT, Double.doubleToLongBits(lat));
		editor.commit();

	}

	public double getCurrentLatitude() {

		double latitude = Double.longBitsToDouble(pref.getLong(CURR_LAT, 0));
		return latitude;

	}

	public void setCurrentLongitude(double lng) {
		editor.putLong(CURR_LNG, Double.doubleToLongBits(lng));
		editor.commit();

	}

	public double getCurrentLongitude() {

		double longitude = Double.longBitsToDouble(pref.getLong(CURR_LNG, 0));
		return longitude;

	}

}
