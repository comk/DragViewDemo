package com.example.dragviewdemo;



import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;


public class RecyclerAdapter extends Adapter<RecyclerAdapter.ViewHolder> {
	
	private DisplayImageOptions imgConfig;
	public RecyclerAdapter() {
		imgConfig = new DisplayImageOptions
			 	  .Builder()
			      .cacheInMemory(false)
			      .cacheOnDisk(true)
			      .showImageForEmptyUri(R.drawable.ic_launcher)     
			      .showImageOnLoading(R.drawable.ic_launcher)
//			      .displayer(new RoundedBitmapDisplayer(UtiUI.getInstance().dip2px(28)))
			      .bitmapConfig(Bitmap.Config.ARGB_8888)
			      .build();
		
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public ImageView i;

		/**
		 * @Description: TODO
		 * @param itemView
		 */
		public ViewHolder(View itemView) {
			super(itemView);
			i = (ImageView) itemView;
		}
	}

	/**
	 * @Method: getItemCount
	 * @Description: TODO
	 * @return
	 * @see android.support.v7.widget.RecyclerView.Adapter#getItemCount()
	 */
	@Override
	public int getItemCount() {
		return Constants.IMAGES.length;
	}

	
	@Override
	public void onViewRecycled(ViewHolder holder) {
		// TODO Auto-generated method stub
		super.onViewRecycled(holder);
	}
	
	/**
	 * @Method: onBindViewHolder
	 * @Description: TODO
	 * @param holder
	 * @param position
	 * @see android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder,
	 *      int)
	 */
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		ImageLoader.getInstance().displayImage(Constants.IMAGES[position],holder.i,imgConfig);
	}

	/**
	 * @Method: onCreateViewHolder
	 * @Description: TODO
	 * @param parent
	 * @param viewType
	 * @return
	 * @see android.support.v7.widget.RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup,
	 *      int)
	 */
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		ImageView i = new ImageView(parent.getContext());
//		 i.setBackgroundResource(R.drawable.circular_onlyborder);
		 i.setPadding(10,10,10,10);
		 i.setScaleType(ImageView.ScaleType.CENTER_CROP);
//		 int side_width = 450;
//		 i.setLayoutParams(new LayoutParams(side_width, side_width));
		 ViewHolder holder = new ViewHolder(i);
		return holder;
	}
}
