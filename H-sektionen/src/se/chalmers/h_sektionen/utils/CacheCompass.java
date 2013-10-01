package se.chalmers.h_sektionen.utils;

import android.app.ActivityManager;
import android.content.Context;

public class CacheCompass {
	
	//Singelton that holds a BitmapCache
	
	private BitmapCache bitmapCache; 

	private static CacheCompass CC=null;
	
	private CacheCompass(Context context){
		
		//Get the memory limit of the application
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		int memoryClassBytes = am.getMemoryClass() * 1024 * 1024;
		// create a BitmapCahce with a 8th of available memory
		bitmapCache = new BitmapCache(memoryClassBytes / 8);
		
	}
	
	public static CacheCompass getInstance(Context con)
	{
		if(CC==null){
			CC=new CacheCompass(con);
		}
		return CC;
	}
	
		
	public BitmapCache getBitmapCache(){
		
		return bitmapCache;
	}
	
}
