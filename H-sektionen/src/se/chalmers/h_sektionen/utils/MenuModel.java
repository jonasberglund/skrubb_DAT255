package se.chalmers.h_sektionen.utils;

import java.util.ArrayList;

public class MenuModel {

	 public static ArrayList<MenuItem> Items;

	    public static void LoadModel() {

	        Items = new ArrayList<MenuItem>();
	        Items.add(new MenuItem(1, "ic_news.png", "Nyheter"));
	        Items.add(new MenuItem(2, "ic_events.png", "Events"));
	        Items.add(new MenuItem(3, "ic_lunch.png", "Dagens Lunch"));
	        Items.add(new MenuItem(4, "ic_11an.png", "11:an"));
	        Items.add(new MenuItem(5, "ic_info.png", "Information"));
	        Items.add(new MenuItem(6, "ic_suggest.png", "Förslagslådan"));
	        Items.add(new MenuItem(7, "ic_faultreport.png", "Felanmälan"));

	    }

	    public static MenuItem GetbyId(int id){

	        for(MenuItem item : Items) {
	            if (item.Id == id) {
	                return item;
	            }
	        }
	        return null;
	    }
}
