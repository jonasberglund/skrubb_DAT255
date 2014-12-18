package se.chalmers_h.h_sektionen.utils;

import java.util.ArrayList;

import se.chalmers_h.h_sektionen.R;
import se.chalmers_h.h_sektionen.containers.MenuItem;
import android.content.Context;
import android.content.res.Resources;

/**
 * Populate a static list of items. The list is used to build the menu.
 * @Author Anders Johansson
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */
public class MenuModel {

	 public static ArrayList<MenuItem> Items;
	 	/**
	 	 * Constructor - Populates a list containing the items for the menu
	 	 * @param con - Context of the calling activity
	 	 */
	    public static void LoadModel(Context con) {
	    	
	    	Resources res = con.getResources();
	    	String[] menuTitles = res.getStringArray(R.array.menu_titles);
	    	
	        Items = new ArrayList<MenuItem>();
	        Items.add(new MenuItem(1, "ic_news.png", menuTitles[0]));
	        Items.add(new MenuItem(2, "ic_events.png", menuTitles[1]));
	        Items.add(new MenuItem(3, "ic_lunch.png", menuTitles[2]));
	        Items.add(new MenuItem(4, "ic_11an.png", menuTitles[3]));
	        Items.add(new MenuItem(5, "ic_info.png", menuTitles[4]));
	        Items.add(new MenuItem(6, "ic_suggest.png", menuTitles[5]));
	        Items.add(new MenuItem(7, "ic_faultreport.png", menuTitles[6]));
	        Items.add(new MenuItem(8, "ic_about.png", menuTitles[7]));


	    }

	    /**
	     * Return MenuItem corresponding to a Id
	     * 
	     * @param id - identifier of the MenuItem
	     * @return MenuItem - returns a MenuItem
	     */
	    public static MenuItem GetbyId(int id){

	        for(MenuItem item : Items) {
	            if (item.Id == id) {
	                return item;
	            }
	        }
	        return null;
	    }
}
