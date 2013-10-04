package se.chalmers.h_sektionen.utils;



import android.graphics.Bitmap;

public class NewsItem {
	
	private String message;
	private String date;
	private String imageAdr;
	private Bitmap bitImage;
	
	
	/**
	 * CONSTRUCTOR
	 */
	public NewsItem(String message, String date, String imageAdr){
		
		//PicLoaderThread t = new PicLoaderThread(imageAdr);
		//t.start();
		
		
		this.message = message;
		this.date = date;
		this.imageAdr = imageAdr;
		
		/*
		try {
			t.join();
			bitImage = t.getPicture();
			if (bitImage != null) {
				bitImage.prepareToDraw();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		
	}
	
	/**
	 * SET METHODS
	 */
	
	public void setMessage(String message){
		this.message = message;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public void setImage(String image){
		this.imageAdr = image;
	}
	public void setbitImage(Bitmap bitImage){
		this.bitImage=bitImage;
	}
	
	/**
	 * GET METHODS
	 */
	
	public String getMessage(){
		return message;
	}
	
	public String getDate(){
		return date;
	}
	
	public String getImageAdr(){
		return imageAdr;
	}
	public Bitmap getBitImage(){
		return bitImage;
	}

}
