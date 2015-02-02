package com.example.dragviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import com.example.dragviewdemo.R;
public class TransparentActivity extends Activity {
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		sendBroadcast(new Intent(NotificationService.Action_Alarm));
		startService(new Intent(this, NotificationService.class));
		Log.e("Transparent", " onResume");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		this.setVisible(false);
		this.getWindow().getDecorView().setVisibility(View.INVISIBLE);
		this.getWindow().getDecorView().setBackgroundColor(getResources().getColor(android.R.color.transparent));
		setContentView(R.layout.transparent);
		Log.e("Transparent", " onCreate");
	}
}
