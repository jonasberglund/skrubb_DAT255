package se.chalmers.h_sektionen.utils;

public class NewsItem {
	
	private String message;
	private String date;
	private String image;
	
	/**
	 * CONSTRUCTOR
	 */
	public NewsItem(String message, String date, String image){
		this.message = message;
		this.date = date;
		this.image = image;
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
		this.image = image;
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
	
	public String getImage(){
		return image;
	}

}
