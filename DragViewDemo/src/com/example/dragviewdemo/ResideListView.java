package com.example.dragviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.ListView;

public class ResideListView extends ListView {

	private ResideView mResideView;
	
	public ResideListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		ViewParent v = this.getParent();
		while(v != null){
			if(v instanceof ResideView){
				this.mResideView = (ResideView)v;
				break;
			}
			v = v.getParent();
		}
		if(this.mResideView == null)
			throw new NullPointerException("ResideScrollView must contained by ResideView");
		
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(this.mResideView != null && this.mResideView.status == ResideView.ShowStatus.Open){
			return true;
		}
		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(this.mResideView != null && this.mResideView.status == ResideView.ShowStatus.Open){
			if(ev.getAction() == MotionEvent.ACTION_UP){
				this.mResideView.Close();
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
}
