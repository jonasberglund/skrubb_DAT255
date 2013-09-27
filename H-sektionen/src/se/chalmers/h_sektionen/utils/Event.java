package se.chalmers.h_sektionen.utils;

public class Event {
	private String title;
	private String description;
	private String place;
	private String date;

	public Event(){

	}

	public Event(String title, String description, String place, String date){
		this.title = title;
		this.description = description;
		this.place = place;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getPlace(){
		return place;
	}
	public void setPlace(String place){
		this.place = place;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	
}
