package se.chalmers.h_sektionen.utils;

import android.graphics.Bitmap;

public class ContactCard {
	private String name;
	private String position;
	private String email;
	private String phoneNumber;
	private Bitmap pic = null;
	
	public ContactCard(String name, String position, String email, String phoneNumber, Bitmap pic) {
		this.name = name;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.pic = pic;
	}
	
	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public Bitmap getPic() {
		return pic;
	}
}
