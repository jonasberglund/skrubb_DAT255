package se.chalmers.h_sektionen.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class BitmapCache extends LruCache<String, Bitmap> {
	
		
		//public class ThumbnailCache extends LruCache<Long, Bitmap>{
		
		  public BitmapCache(int maxSizeInBytes) {
		
		   super(maxSizeInBytes);
		
		  }
		
		  @Override
		
				  
		  protected int sizeOf(String key, Bitmap value) {
		        if (Integer.valueOf(android.os.Build.VERSION.SDK_INT) >= 12)
		            return value.getByteCount();
		        else
		            return (value.getRowBytes() * value.getHeight());
		    }

	
	
}
