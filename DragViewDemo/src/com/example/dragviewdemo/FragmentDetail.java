package com.example.dragviewdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentDetail extends Fragment {
	
	private FragmentDetail(){
		
	}
	
	private static FragmentDetail instance = null;
	
	public static FragmentDetail getInstance(){
		if(instance == null)
			instance = new FragmentDetail();
		return instance;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e("FragmentDetail onCreate", "============");
		super.onCreate(savedInstanceState);
	}
	
	public FragmentDetail newInstance(){
		return new FragmentDetail();
	}
	
	public void setContent(String categoryID){
		this.categoryID = categoryID;
		if(tv_content != null)
			tv_content.setText(categoryID);
	}
	
	private String categoryID = "default";
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e("FragmentDetail onDestroy", "============");
	}

	private TextView tv_content;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_layout, null);
		tv_content = (TextView) v.findViewById(R.id.content_text);
		tv_content.setText(categoryID);
		Log.e("FragmentDetail onCreateView", "============");
		tv_content.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), categoryID, Toast.LENGTH_SHORT).show();
			}
		});
		return v;
	}
	
}
