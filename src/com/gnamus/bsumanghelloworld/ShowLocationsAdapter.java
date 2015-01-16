package com.gnamus.bsumanghelloworld;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gnamus.bsumanghelloworld.utilities.LocationUtils;
import com.gnamus.bsumanghelloworld.utilities.SessionManager;

public class ShowLocationsAdapter extends ArrayAdapter<Location> {

	private final Activity mActivity;
	private final ArrayList<Location> list;
	public SessionManager session;
	private double distance;

	public ShowLocationsAdapter(Activity mActivity, ArrayList<Location> list) {

		super(mActivity, R.layout.show_locations_row, list);
		this.list = list;
		this.mActivity = mActivity;
	}

	static class ViewHolder {
		public TextView name;
		public TextView address;
		public TextView address2;
		public TextView distance;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		session = new SessionManager(mActivity.getApplicationContext());
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflator = mActivity.getLayoutInflater();
			rowView = inflator.inflate(R.layout.show_locations_row, null);

			ViewHolder viewHolder = new ViewHolder();

			viewHolder.name = (TextView) rowView.findViewById(R.id.name);
			viewHolder.address = (TextView) rowView
					.findViewById(R.id.address_line1);
			viewHolder.address2 = (TextView) rowView
					.findViewById(R.id.address_line2);
			viewHolder.distance = (TextView) rowView
					.findViewById(R.id.distance);

			rowView.setTag(viewHolder);

		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.name.setText(list.get(position).getName());
		holder.address.setText(list.get(position).getAddress() + " "
				+ list.get(position).getAddress2());
		holder.address2.setText(list.get(position).getCity() + ", "
				+ list.get(position).getState() + " "
				+ list.get(position).getZipPostalCode());

		if (session.getCurrentLatitude() != 0) {
			distance = LocationUtils.calculateDistance(
					session.getCurrentLatitude(),
					session.getCurrentLongitude(),
					Double.parseDouble(list.get(position).getLatitude()),
					Double.parseDouble(list.get(position).getLongitude()));
			holder.distance.setText(distance + " miles");
		}

		return rowView;

	}

}
