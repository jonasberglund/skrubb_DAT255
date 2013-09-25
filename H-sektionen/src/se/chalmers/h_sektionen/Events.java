package se.chalmers.h_sektionen;

public class Events {
	private String title;
	private String description;
	private int date;

	public Events(){

	}

	public Events(String i, String d, int p){
		this.title = d;
		this.description = i;
		this.date = p;
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
	
	public int getDate(){
		return date;
	}
	
	public void setDate(int date){
		this.date = date;
	}
}
