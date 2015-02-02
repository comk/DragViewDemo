package com.example.dragviewdemo;

import android.util.Log;
import android.view.animation.Interpolator;


public class A implements Interpolator {

	float mTension = 3.0f;
	@Override
	public float getInterpolation(float input) {
		// TODO Auto-generated method stub
		float f = input * input * ((mTension + 1) * input - mTension);
		Log.e("f = ", f + "");
		return f;
	}

}
