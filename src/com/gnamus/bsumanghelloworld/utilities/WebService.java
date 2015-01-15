package com.gnamus.bsumanghelloworld.utilities;

import retrofit.Callback;
import retrofit.http.GET;

import com.gnamus.bsumanghelloworld.Offices;

public interface WebService {

	@GET("/helloworld_locations.json")
	public void getLocations(Callback<Offices> response);

};
