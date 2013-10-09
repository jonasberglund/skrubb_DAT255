package se.chalmers.h_sektionen.utils;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

/**
 * ScrollListener that checks if ScrollList has reached bottom.
 */
public abstract class OnBottomScrollListener implements OnScrollListener {
		private int currentFirstVisibleItem;
		private int currentVisibleItemCount;
		private int totalItemCount;
		private int currentScrollState;

		/**
		 * Superclass method onScroll
		 */
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		    this.currentFirstVisibleItem = firstVisibleItem;
		    this.currentVisibleItemCount = visibleItemCount;
		    this.totalItemCount = totalItemCount;
		}
		
		/**
		 * Superclass method onScroll
		 */
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
		    this.currentScrollState = scrollState;
		    if (this.isScrollCompleted()){
		    	doOnScrollCompleted();
		    }
		 }

		/**
		 * Checks if scrollist has reached bottom.
		 * 
		 * @return True if scrollist reached bottom, otherwise false
		 */
		private boolean isScrollCompleted() {
		    return ((this.currentVisibleItemCount + this.currentFirstVisibleItem) >= this.totalItemCount && 
		    	this.currentScrollState == SCROLL_STATE_IDLE);
		}
		
		/**
		 * Executed when scrollist has reached bottom.
		 */
		protected abstract void doOnScrollCompleted();
}
