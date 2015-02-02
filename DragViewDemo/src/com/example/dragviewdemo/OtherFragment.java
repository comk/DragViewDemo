package com.example.dragviewdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class OtherFragment extends Fragment {
	private OtherFragment() {
		// TODO Auto-generated constructor stub
	}
	
	private static OtherFragment instance = null;
	
	public static OtherFragment getInstance(){
		if(instance == null)
			instance = new OtherFragment();
		return instance;
	}
	
	public static OtherFragment newInstance(){
		return new OtherFragment();
	}
	
	public OtherFragment setContent(String str){
		this.name = str;
		return this;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		Log.e("Fragment onAttach", name + "============");
		super.onAttach(activity);
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		Log.e("Fragment onHiddenChanged", name + "============"+hidden);
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		Log.e("Fragment onDestroyView", name + "============");
		super.onDestroyView();
	}
	
	@Override
	public void onResume() {
		Log.e("Fragment onResume", name + "============");
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		Log.e("Fragment onPause", name + "============");
		super.onPause();
	}
	
	@Override
	public void onStart() {
		Log.e("Fragment onStart", name + "============");
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		Log.e("Fragment onStop", name + "============");
		super.onStop();
	}
	
	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		Log.e("Fragment onDetach", name + "============");
		super.onDetach();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.e("Fragment onCreate", name + "============");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.e("Fragment onDestroy", name + "============");
		super.onDestroy();
	}
	
	private TextView tv_content;
	private String name;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_layout, null);
		v.setBackgroundColor(Color.GREEN);
		tv_content = (TextView) v.findViewById(R.id.content_text);
		tv_content.setText(name);
		tv_content.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
			}
		});
		return v;
	}
	
}
