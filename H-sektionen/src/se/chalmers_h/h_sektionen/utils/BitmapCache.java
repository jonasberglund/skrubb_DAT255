package se.chalmers_h.h_sektionen.utils;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

/**
 *  A cache for images in the application, uses the LruCache class.
 * @Author Anders Johansson
 * @Copyright (c) 2013 Anders Johansson, Olle Svensson, Robin Tornquist, Rikard Ekbom, Oskar Gustavsson, Jonas Berglund
 * @Licens Apache
 */
public class BitmapCache extends LruCache<String, Bitmap> {
	
		  /**
		   * Constructor 
		   * @param maxSizeInBytes - The number of bytes to reserve for cache.
		   */
		  public BitmapCache(int maxSizeInBytes) {
		
		   super(maxSizeInBytes);
		
		  }
		
		  @Override
		
		  /**
		   * Calculates the size of a image in the cache.
		   * Depending of version it calculates in two different ways.		  
		   */
		  protected int sizeOf(String key, Bitmap value) {
		        if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= Build.VERSION_CODES.HONEYCOMB_MR1)
		            return value.getByteCount();
		        else
		            return (value.getRowBytes() * value.getHeight());
		    }

	
	
}
