package com.gnamus.bsumanghelloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// show splash screen for a few seconds, then jump to locations screen
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent i = new Intent(MainActivity.this,
						ShowLocationsActivity.class);
				startActivity(i);

				finish();
			}
		}, SPLASH_TIME_OUT);

	}

}
