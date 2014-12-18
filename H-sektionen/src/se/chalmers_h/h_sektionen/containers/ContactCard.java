package se.chalmers_h.h_sektionen.containers;

import android.graphics.Bitmap;

/**
 * ContactCard is a container class.
 */
public class ContactCard {
	private String name;
	private String position;
	private String email;
	private String phoneNumber;
	private Bitmap pic = null;
	
	/**
	 * Initiating the variables.
	 * @param name The name of a person
	 * @param position The position of a person 
	 * @param email The email address to a person
	 * @param phoneNumber The phone number to a person
	 * @param pic A Bitmap picture of the person
	 */
	public ContactCard(String name, String position, String email, String phoneNumber, Bitmap pic) {
		this.name = name;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.pic = pic;
	}
	
	/**
	 * @return The name stored in the ContactCard
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The position stored in the ContactCard
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @return The email address stored in the ContactCard
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @return The phone number stored in the ContactCard
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * @return The Bitmap picture stored in the ContactCard
	 */
	public Bitmap getPic() {
		return pic;
	}
}
