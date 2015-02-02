package com.example.dragviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecycleActivity extends Activity {
	RecyclerView rv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recycle_layout);
		rv = (RecyclerView) findViewById(R.id.recyclerView);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		rv.setLayoutManager(layoutManager);
		rv.setAdapter(new RecyclerAdapter());
		
	}
}
