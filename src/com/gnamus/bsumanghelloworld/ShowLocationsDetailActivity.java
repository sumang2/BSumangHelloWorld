package com.gnamus.bsumanghelloworld;

import java.io.InputStream;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gnamus.bsumanghelloworld.utilities.SessionManager;
import com.google.android.gms.maps.GoogleMap;

public class ShowLocationsDetailActivity extends Activity {

	private Button btnCall;
	private Button btnDirections;

	private TextView name;
	private TextView addressLine1;
	private TextView addressLine2;
	private ImageView officeImage;
	private Bitmap bitmap;

	private GoogleMap map;
	private String STATIC_MAP_API_ENDPOINT;
	private String phoneExtra;
	private String latitudeExtra;
	private String longitudeExtra;
	public SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_locations_detail);

		session = new SessionManager(getApplicationContext());

		name = (TextView) findViewById(R.id.name_detail);
		addressLine1 = (TextView) findViewById(R.id.address_detail_line1);
		addressLine2 = (TextView) findViewById(R.id.address_detail_line2);
		officeImage = (ImageView) findViewById(R.id.office_image);

		// grab data from session manager based on list position

		// grab selected item data
		Intent intentObject = getIntent();
		if (intentObject != null) {
			String nameExtra = intentObject.getStringExtra("name");
			String addressExtra = intentObject.getStringExtra("address");
			String address2Extra = intentObject.getStringExtra("address2");
			String cityExtra = intentObject.getStringExtra("city");
			String stateExtra = intentObject.getStringExtra("state");
			String zipExtra = intentObject.getStringExtra("zip");
			phoneExtra = intentObject.getStringExtra("phone");
			String faxExtra = intentObject.getStringExtra("fax");
			latitudeExtra = intentObject.getStringExtra("latitude");
			longitudeExtra = intentObject.getStringExtra("longitude");
			String imageExtra = intentObject.getStringExtra("office_image");

			STATIC_MAP_API_ENDPOINT = ("http://maps.google.com/maps/api/staticmap?center="
					+ latitudeExtra
					+ ","
					+ longitudeExtra
					+ "&zoom=15&size=600x600&scale=2"
					+ "&markers=color:orange%7Clabel:HW%7C"
					+ latitudeExtra
					+ "," + longitudeExtra);
			AsyncTask<Void, Void, Bitmap> setImageFromUrl = new AsyncTask<Void, Void, Bitmap>() {
				@Override
				protected Bitmap doInBackground(Void... params) {
					Bitmap bmp = null;
					HttpClient httpclient = new DefaultHttpClient();
					HttpGet request = new HttpGet(STATIC_MAP_API_ENDPOINT);

					InputStream in = null;
					try {
						in = httpclient.execute(request).getEntity()
								.getContent();
						bmp = BitmapFactory.decodeStream(in);
						in.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return bmp;
				}

				protected void onPostExecute(Bitmap bmp) {
					if (bmp != null) {
						final ImageView iv = (ImageView) findViewById(R.id.map_static);
						iv.setImageBitmap(bmp);
					}
				}
			};
			setImageFromUrl.execute();

			name.setText(nameExtra);
			addressLine1.setText(addressExtra + " " + address2Extra);
			addressLine2
					.setText(cityExtra + ", " + stateExtra + " " + zipExtra);

			new LoadImage().execute(imageExtra);

		}

		btnCall = (Button) findViewById(R.id.btn_call);
		btnCall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					Intent callIntent = new Intent(Intent.ACTION_DIAL);
					callIntent.setData(Uri.parse("tel:" + phoneExtra));
					startActivity(callIntent);
				} catch (ActivityNotFoundException activityException) {
					Log.e("Calling a Phone Number", "Call failed",
							activityException);
				}

			}
		});

		btnDirections = (Button) findViewById(R.id.btn_directions);
		btnDirections.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Log.i("LAT", latitudeExtra);
				Log.i("LNG", longitudeExtra);

//				Toast.makeText(getApplicationContext(),
//						session.getCurrentLatitude() + "", Toast.LENGTH_SHORT)
//						.show();
				if (session.getCurrentLatitude() != 0) {
					Intent intent = new Intent(
							android.content.Intent.ACTION_VIEW, Uri
									.parse("http://maps.google.com/maps?saddr="
											+ session.getCurrentLatitude()
											+ ","
											+ session.getCurrentLongitude()
											+ "&daddr=" + latitudeExtra + ","
											+ longitudeExtra));
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(),
							"Acquiring your location...", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

	}

	private class LoadImage extends AsyncTask<String, String, Bitmap> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected Bitmap doInBackground(String... args) {
			try {
				bitmap = BitmapFactory.decodeStream((InputStream) new URL(
						args[0]).getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap image) {
			if (image != null) {
				officeImage.setImageBitmap(image);
			} else {
				// Toast.makeText(MainActivity.this,
				// "Image Does Not exist or Network Error",
				// Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.show_locations_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
