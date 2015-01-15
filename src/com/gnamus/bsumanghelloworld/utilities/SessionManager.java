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

	// private static final String IS_LOGIN = "IsLoggedIn";
	//
	// private static final String IS_ENABLED = "IsEnabled";
	//
	// public static final String KEY_NAME = "username";

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

	// public void createLoginSession(String nujWord, String message) {
	// //editor.putBoolean(IS_LOGIN, true);
	//
	// editor.putString(KEY_NAME, nujWord);
	//
	// editor.putString(KEY_TOKEN, message);
	//
	// editor.commit();
	// }

	// public void checkLogin() {
	// if (!this.isLoggedIn()) {
	// Intent i = new Intent(mContext, LoginActivity.class);
	// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	//
	// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	//
	// mContext.startActivity(i);
	// }
	//
	// }

	// public HashMap<String, String> getUserDetails() {
	// HashMap<String, String> user = new HashMap<String, String>();
	// user.put(KEY_NAME, pref.getString(KEY_NAME, null));
	//
	// //user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
	//
	// return user;
	// }

	// public void logoutUser() {
	// editor.clear();
	// editor.commit();
	//
	// // Intent i = new Intent(mContext, LoginActivity.class);
	// // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	// //
	// // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// //
	// // mContext.startActivity(i);
	// }

	// public boolean isLoggedIn() {
	// return pref.getBoolean(IS_LOGIN, false);
	// }
	//
	// public boolean isEnabled() {
	// return pref.getBoolean(IS_ENABLED, true);
	// }
	//
	// public void setEnabled(Boolean state) {
	//
	// editor.putBoolean(IS_ENABLED, state);
	//
	// editor.commit();
	// }

}
