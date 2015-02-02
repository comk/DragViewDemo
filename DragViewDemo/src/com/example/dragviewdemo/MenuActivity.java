package com.example.dragviewdemo;

import com.example.dragviewdemo.R;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.*;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;

public class MenuActivity extends FragmentActivity implements OnClickListener{
	public static final String HOME_CENTER = "HOME-CENTER";
	public static final String HOME_RIGHT = "HOME-RIGHT";
	
	View v_shift;
	View v_menu;
	View v_center;
	View v_bottom_right;
	ListView v_contentListView;
	Animation category_in;
	Animation content_out;
	Animation content_out_category_dont;
	Animation content_in;
	boolean animing = false;
	private volatile int currentType = -1;
	Animation category_out;
	boolean content_animing = false;
	View anim;
	
	@Override
	public void onBackPressed() {
		String name ;
		int count = getSupportFragmentManager().getBackStackEntryCount();
		if(!HOME_CENTER.equalsIgnoreCase(name = getSupportFragmentManager().getBackStackEntryAt(count > 0 ? count - 1 : 0).getName())){
			Log.e("name = ", name);
			v_center.performClick();
		}else
			super.onBackPressed();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_tmp);
		getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).commit();
		anim = findViewById(R.id.animview);
		v_center = findViewById(R.id.center);
		v_bottom_right = findViewById(R.id.bottom_right);
		category_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.overshoot_slide_header_down);
		category_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anticipate_slide_header_up);
		content_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anticipate_slide_content_in);
		content_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.overshoot_slide_content_out);
		content_out_category_dont = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.overshoot_slide_content_out);
		v_menu = findViewById(R.id.menu_category);
		
		
		v_center.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fragment frag = getSupportFragmentManager().findFragmentByTag(HOME_CENTER);
				if(frag == null){
					getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.content_layout, OtherFragment.newInstance().setContent(HOME_CENTER),HOME_CENTER).addToBackStack(HOME_CENTER).commit();
				}else{
					getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.content_layout, frag,HOME_CENTER).addToBackStack(HOME_CENTER).commit();
					
				}
			}
		});
		
		v_bottom_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fragment frag = getSupportFragmentManager().findFragmentByTag(HOME_RIGHT);
				if(frag == null){
					getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.content_layout, OtherFragment.newInstance().setContent(HOME_RIGHT),HOME_RIGHT).addToBackStack(HOME_RIGHT).commit();
				}else{
					getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.content_layout, frag,HOME_RIGHT).addToBackStack(HOME_RIGHT).commit();
				}
			}
		});
		
		
		
		
		v_contentListView = (ListView) findViewById(R.id.menu_sub_category);
		
		v_contentListView.setOnItemClickListener(new OnItemClickListener() {

			/* (non-Javadoc)
			 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
			 */
			
			
			
			
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(content_animing)
					return;
				startAnimation(view, v_center,MenuActivity.this);
				if(v_contentListView.getVisibility() == View.VISIBLE){
					v_contentListView.startAnimation(content_out);
				}
					FragmentDetail.getInstance().setContent(array[currentType][position]);
				if(!FragmentDetail.getInstance().isAdded())
					getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.content_layout, FragmentDetail.getInstance()).addToBackStack("list").commit();
			}
		});
		
		v_shift = findViewById(R.id.menu_shift);
		
		v_shift.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(animing)
					return;
				
				
				
//				if(!FragmentDetail.getInstance().isAdded())
//					getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.content_layout, FragmentDetail.getInstance()).addToBackStack(null).commit();
//				else
//					getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit).replace(R.id.content_layout, OtherFragment.newInstance().setContent("abc")).addToBackStack(null).commit();
					
				if(v_menu.getVisibility() != View.VISIBLE){
					v_menu.setVisibility(View.VISIBLE);
					v_menu.startAnimation(category_in);
				}else{
					if(v_contentListView.getVisibility() == View.VISIBLE){
						v_contentListView.startAnimation(content_out);
					}else{
						v_menu.startAnimation(category_out);
					}
				}
			}
		});
		
		findViewById(R.id.category_1).setOnClickListener(this);
		findViewById(R.id.category_2).setOnClickListener(this);
		findViewById(R.id.category_3).setOnClickListener(this);
		findViewById(R.id.category_4).setOnClickListener(this);
		findViewById(R.id.category_5).setOnClickListener(this);
		findViewById(R.id.category_6).setOnClickListener(this);
		findViewById(R.id.category_7).setOnClickListener(this);
		findViewById(R.id.category_8).setOnClickListener(this);
		
		content_in.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				content_animing = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				content_animing = false;
				v_contentListView.setVisibility(View.VISIBLE);
			}
		});
		content_out.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				content_animing = true;
				if(v_menu.getVisibility() == View.VISIBLE)
					animing = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				content_animing = false;
				v_contentListView.setVisibility(View.GONE);
				if(v_menu.getVisibility() == View.VISIBLE){
					v_menu.startAnimation(category_out);
				}
			}
		});
		content_out_category_dont.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				content_animing = true;
				if(v_menu.getVisibility() == View.VISIBLE)
					animing = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				content_animing = false;
				animing = false;
				v_contentListView.setVisibility(View.GONE);
			}
		});
		content_out.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				content_animing = true;
				if(v_menu.getVisibility() == View.VISIBLE)
					animing = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				content_animing = false;
				v_contentListView.setVisibility(View.GONE);
				if(v_menu.getVisibility() == View.VISIBLE){
					v_menu.startAnimation(category_out);
				}
			}
		});
		
		
		category_in.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				animing = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				animing = false;
				v_menu.setVisibility(View.VISIBLE);
			}
		});
		
		category_out.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				animing = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				animing = false;
				v_menu.setVisibility(View.GONE);
			}
		});
	}
	
	void startAnimation(View startView,View desView,Activity activity){
		if(startView == null || desView == null || activity == null)
			return;
		View tmpv = new View(getApplicationContext());
		((ViewGroup) activity.getWindow().getDecorView()).addView(tmpv);
		tmpv.setBackgroundColor(Color.RED);
		
		int[] location = new int[2];
		startView.getLocationInWindow(location);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(100, 100);
		lp.leftMargin = location[0];
		lp.topMargin = location[1];
		tmpv.setLayoutParams(lp);
		
		int[] locationdes = new int[2];
		desView.getLocationInWindow(locationdes);
		
		AnimationSet as = new AnimationSet(false);
		as.setDuration(1800);
		AddToCartAnimation ta = new AddToCartAnimation(0, locationdes[0] - location[0],0,  locationdes[1] - location[1]);
		ta.setFillAfter(true);
		as.addAnimation(ta);
		as.addAnimation(new AlphaAnimation(1f,0.3f));
		as.setFillAfter(false);
		tmpv.startAnimation(as);
	}
	
	void openContent(){
		if(v_contentListView.getVisibility() != View.VISIBLE){
			v_contentListView.setVisibility(View.VISIBLE);
			v_contentListView.startAnimation(content_in);
		}
	}
	
	
	String[][] array = new String[][]{
			{"category_1_item_1","category_1_item_2","category_1_item_3","category_1_item_4","category_1_item_5",
				"category_1_item_6","category_1_item_7","category_1_item_8","category_1_item_9","category_1_item_10"},
			{"category_2_item_1","category_2_item_2","category_2_item_3","category_2_item_4","category_2_item_5"},
			{"category_3_item_1","category_3_item_2","category_3_item_3","category_3_item_4","category_3_item_5"},
			{"category_4_item_1","category_4_item_2","category_4_item_3","category_4_item_4","category_4_item_5"},
			{"category_5_item_1","category_5_item_2","category_5_item_3","category_5_item_4","category_5_item_5"},
			{"category_6_item_1","category_6_item_2","category_6_item_3","category_6_item_4","category_6_item_5"},
			{"category_7_item_1","category_7_item_2","category_7_item_3","category_7_item_4","category_7_item_5"},
			{"category_8_item_1","category_8_item_2","category_8_item_3","category_8_item_4","category_8_item_5"}
	};
	
	
	void LoadSubCategoryData(int type){
		currentType = type;
		v_contentListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, array[type]));
	}
	
	void LoadContentData(){
		
	}
	
	@Override
	public void onClick(View v) {
		if(content_animing)
			return;
		switch (v.getId()) {
		case R.id.category_1:
			LoadSubCategoryData(0);
			openContent();
			break;
		case R.id.category_2:
			LoadSubCategoryData(1);
			openContent();
			break;
		case R.id.category_3:
			LoadSubCategoryData(2);
			openContent();
			break;
		case R.id.category_4:
			LoadSubCategoryData(3);
			openContent();
			break;
		case R.id.category_5:
			LoadSubCategoryData(4);
			openContent();
			break;
		case R.id.category_6:
			LoadSubCategoryData(5);
			openContent();
			break;
		case R.id.category_7:
			LoadSubCategoryData(6);
			openContent();
			break;
		case R.id.category_8:
			LoadSubCategoryData(7);
			openContent();
			break;

		default:
			break;
		}
	}
}
