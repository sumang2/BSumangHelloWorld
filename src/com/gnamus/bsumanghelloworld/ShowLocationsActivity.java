package com.gnamus.bsumanghelloworld;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gnamus.bsumanghelloworld.utilities.Contract;
import com.gnamus.bsumanghelloworld.utilities.SessionManager;
import com.gnamus.bsumanghelloworld.utilities.WebService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class ShowLocationsActivity extends Activity {

	ListView listLocations;
	public static ArrayList<Location> officesArrayList = new ArrayList<Location>();
	public ShowLocationsAdapter adapter = null;
	TextView totalStars;
	Activity myActivity;
	SessionManager session;

	private WebService service;

	private Toast toast = null;

	// private SessionManager session;

	static final LatLng HQ = new LatLng(42.475162, -83.134733);
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_locations);

		toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);

		session = new SessionManager(getApplicationContext());
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		// Move the camera instantly to Headquarters with a zoom of 20.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(HQ, 10));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(3), 2000, null);

		initWebService();
		getOfficeLocations();

		// session = new SessionManager(getApplicationContext());

		listLocations = (ListView) findViewById(R.id.locations_list);
		adapter = new ShowLocationsAdapter(this, officesArrayList);
		listLocations.setAdapter(adapter);

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adapter.notifyDataSetChanged();
			}
		});

		// if (officesArrayList == null || officesArrayList.size() < 1) {
		// Log.i("OFFICE empty", "empty");
		// } else {
		// Log.i("OFFICE name", officesArrayList.get(0).getName().toString());
		// setMapMarkers();
		//
		// }

		// detail view on item click
		listLocations
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parentAdapter,
							View view, int position, long id) {
//						toast.setText("Item with id [" + id + "] - Position ["
//								+ position + "] ");
//						toast.show();

						Location selectedLocation = (Location) listLocations
								.getItemAtPosition(position);

						Intent i = new Intent(getApplicationContext(),
								ShowLocationsDetailActivity.class);
						i.putExtra("name", selectedLocation.getName()
								.toString());
						i.putExtra("address", selectedLocation.getAddress()
								.toString());
						i.putExtra("address2", selectedLocation.getAddress2()
								.toString());
						i.putExtra("city", selectedLocation.getCity()
								.toString());
						i.putExtra("state", selectedLocation.getState()
								.toString());
						i.putExtra("zip", selectedLocation.getZipPostalCode()
								.toString());
						i.putExtra("phone", selectedLocation.getPhone()
								.toString());
						i.putExtra("fax", selectedLocation.getFax()
								.toString());
						i.putExtra("latitude", selectedLocation.getLatitude()
								.toString());
						i.putExtra("longitude", selectedLocation.getLongitude()
								.toString());
						i.putExtra("office_image", selectedLocation.getOfficeImage()
								);

						startActivity(i);

					}
				});

	}

	private void getOfficeLocations() {

		toast.setText("Fetching locations...");
		toast.show();

		try {
			Callback<Offices> locationsCallback = new Callback<Offices>() {
				@Override
				public void success(Offices offices, Response response) {

					if (offices == null) {

						toast.setText("No locations found!");
						toast.show();
					} else {

						toast.setText("Locations loaded: "
								+ offices.getLocations().size());
						toast.show();

						officesArrayList.clear();
						for (int i = 0; i < offices.getLocations().size(); i++) {
							Location tempLocations = new Location();
							tempLocations.setName(offices.getLocations().get(i)
									.getName());
							tempLocations.setAddress(offices.getLocations()
									.get(i).getAddress());
							tempLocations.setAddress2(offices.getLocations()
									.get(i).getAddress2());
							tempLocations.setCity(offices.getLocations().get(i)
									.getCity());
							tempLocations.setState(offices.getLocations()
									.get(i).getState());
							tempLocations.setZipPostalCode(offices
									.getLocations().get(i).getZipPostalCode());
							tempLocations.setPhone(offices.getLocations()
									.get(i).getPhone());
							tempLocations.setFax(offices.getLocations().get(i)
									.getFax());
							tempLocations.setLatitude(offices.getLocations()
									.get(i).getLatitude());
							tempLocations.setLongitude(offices.getLocations()
									.get(i).getLongitude());
							tempLocations.setOfficeImage(offices.getLocations()
									.get(i).getOfficeImage());

							Log.i("OFFICE name", offices.getLocations().get(i)
									.getZipPostalCode()
									+ "");
							officesArrayList.add(tempLocations);
						}
					}

					adapter.notifyDataSetChanged();

					// add custom markers to map
					setMapMarkers();

					// save retrieved locations
					saveLocations();

				}

				@Override
				public void failure(RetrofitError retrofitError) {

					Toast.makeText(
							ShowLocationsActivity.this,
							"Error: retrofit failure "
									+ retrofitError.toString(),
							Toast.LENGTH_LONG).show();
					Log.d("retrofit error! ", retrofitError.toString());

					toast.setText("Error: Check your connection!");
					toast.show();

				}
			};
			service.getLocations(locationsCallback);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setMapMarkers() {

		for (int i = 0; i < officesArrayList.size(); i++) {
			double lati = Double.parseDouble(officesArrayList.get(i)
					.getLatitude());
			double longLat = Double.parseDouble(officesArrayList.get(i)
					.getLongitude());
			map.addMarker(new MarkerOptions()
					.position(new LatLng(lati, longLat))
					.title(officesArrayList.get(i).getName())
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.marker)));
		}

	}

	private void saveLocations() {

		Gson gson = new Gson();
		String jsonLocations = gson.toJson(officesArrayList);
		session.storeLocations(jsonLocations);

	}

	private void loadLocations() {

		// load if no internet

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// getMenuInflater().inflate(R.menu.view_targets, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return true;

	}

	private void initWebService() {

		RestAdapter.Log log = new RestAdapter.Log() {

			@Override
			public void log(String s) {
				Log.d("WEB SERVICE", s);
			}
		};

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(Contract.API_URL).setLog(log)
				.setLogLevel(RestAdapter.LogLevel.FULL).build();
		// .setLogLevel(RestAdapter.LogLevel.NONE).build();

		service = restAdapter.create(WebService.class);

	}

	@Override
	protected void onStop() {
		super.onStop();
		if (toast != null) {
			toast.cancel();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (toast != null) {
			toast.cancel();
		}
	}

}
