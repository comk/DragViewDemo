package com.example.dragviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ResideScrollView extends ScrollView {

	private ResideView mResideView;
	
	public ResideScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if(this.getParent() instanceof ResideView)
			this.mResideView = (ResideView)this.getParent();
		else
			throw new NullPointerException("ResideScrollView must directly contained by ResideView");
		
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
				performClick();
				this.mResideView.Close();
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
}
