package se.chalmers.h_sektionen.utils;

import android.graphics.Bitmap;

public class ContactCard {
	private String name;
	private String position;
	private String email;
	private String phoneNumber;
	private Bitmap pic = null;
	
	public ContactCard(String name, String position, String email, String phoneNumber, String picAddr) {
		// To make sure that the BitmapFactory can render the picture, the picture should be ".png"
		PicLoaderThread t = new PicLoaderThread(picAddr);
		t.start();
		
		this.name = name;
		this.position = position;
		this.email = email;
		this.phoneNumber = phoneNumber;
		
		// Try to get the picture from the given url.
		try {
			t.join();
			pic = t.getPicture();
			if (pic != null) {
				pic.prepareToDraw();
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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
