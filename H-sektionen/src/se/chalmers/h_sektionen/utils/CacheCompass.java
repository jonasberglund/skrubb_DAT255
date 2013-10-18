package se.chalmers.h_sektionen.utils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Singleton Container for the BitmapCache.
 * @Author Anders Johansson
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */
public class CacheCompass {
	
	private BitmapCache bitmapCache; 

	private static CacheCompass CC=null;
	
	/**
	 * Constructor - Creates a BitmapCache and reserves a 6th of the available memory for the BitmapCache
	 * @param context - Contect of the calling 
	 */
	private CacheCompass(Context context){
		
		//Get the memory limit of the application
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		int memoryClassBytes = am.getMemoryClass() * 1024 * 1024;
		// create a BitmapCahce with a 6th of available memory
		bitmapCache = new BitmapCache(memoryClassBytes / 6);
		
	}
	/**
	 * If CacheCompass exits, return reference to CacheCompass. Otherwise creates a CacheCompass
	 * @param con - Context of calling Activity
	 * @return CacheCompass
	 */
	public static CacheCompass getInstance(Context con)
	{
		if(CC==null){
			CC=new CacheCompass(con);
		}
		return CC;
	}
	
	/**
	 * Returns the BitmapCache	
	 * @return BitmapCache
	 */
	public BitmapCache getBitmapCache(){
		
		return bitmapCache;
	}
	
	
}
