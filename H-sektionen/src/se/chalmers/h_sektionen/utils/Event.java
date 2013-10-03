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
	
	public String getDescription(){
		return description;
	}
	
	public String getPlace(){
		return place;
	}
	
	public String getDate(){
		return date;
	}
	
	public String getTime(){
		String time = date.split("T")[1].substring(0,5);
		
		return time;
	}
}
