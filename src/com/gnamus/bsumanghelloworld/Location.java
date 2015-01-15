package com.gnamus.bsumanghelloworld;

import com.google.gson.Gson;

public class Location {
	private String name;
	private String address;
	private String address2;
	private String city;
	private String state;
	private String zip_postal_code;
	private String phone;
	private String fax;
	private String latitude;
	private String longitude;
	private String office_image;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipPostalCode() {
		return zip_postal_code;
	}

	public void setZipPostalCode(String zipPostalCode) {
		this.zip_postal_code = zipPostalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getOfficeImage() {
		return office_image;
	}

	public void setOfficeImage(String officeImage) {
		this.office_image = officeImage;
	}

	public String serialize() {
		// Serialize this class into a JSON string using GSON
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	static public Location create(String serializedData) {
		// Use GSON to instantiate this class using the JSON representation of
		// the state
		Gson gson = new Gson();
		return gson.fromJson(serializedData, Location.class);
	}

}
