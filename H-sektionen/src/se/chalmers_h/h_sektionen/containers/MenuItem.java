package se.chalmers_h.h_sektionen.containers;

/**
 *  Container for a item in the Menu list
 *
 */
public class MenuItem {

	 public int Id;
	    public String IconFile;
	    public String Name;
	    
	    /**
	     * Constructor 
	     * @param id - unique id for MenuItem
	     * @param iconFile - filename of image
	     * @param name - Text to display in menu
	     */
	    public MenuItem(int id, String iconFile, String name) {

	        Id = id;
	        IconFile = iconFile;
	        Name = name;

	    }
}
