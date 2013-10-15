package se.chalmers.h_sektionen.containers;

/**
 * Class to create events.
 */
public class Event {
	private String title;
	private String description;
	private String place;
	private String date;
	private String fullDate;

	public Event(){

	}

	/**
	 * Creating event
	 * @param title
	 * @param description
	 * @param place
	 * @param date
	 */
	public Event(String title, String description, String place, String date, String fullDate){
		this.title = title;
		this.description = description;
		this.place = place;
		this.date = date;
		this.fullDate = fullDate;
	}
	
	public Event(String title, String description, String place, String date){
		this.title = title;
		this.description = description;
		this.place = place;
		this.date = date;
	}

	/**
	 * Get title
	 * @return String title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Get Description
	 * @return String description
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Get place
	 * @return String Place
	 */
	public String getPlace(){
		return place;
	}
	
	/**
	 * Get date
	 * @return String date
	 */
	public String getDate(){
		return date;
	}
	
	/**
	 * Get place
	 * @return String Place
	 */
	public String getFullDate(){
		return fullDate;
	}
}
