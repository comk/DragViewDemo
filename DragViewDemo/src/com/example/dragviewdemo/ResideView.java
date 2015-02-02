package com.example.dragviewdemo;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.InflateException;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class ResideView extends ViewGroup {
	
	/**
	 * The Content View Scale Rate
	 * 
	 * Value 0.2f is recommend
	 */
	static final float RATE_SCALE = 0.15f;

	/**
	 * Value 3 is recommend
	 */
	static final float RATE_FACTOR = 2;

	/**
	 * The Value of RATE_MENU_MIN must between 0.0 and 1.0 , so the value of
	 * RATE_FACTOR * RATE_SCALE must between 0.0 and 1.0,
	 * 
	 * Value 0.4 is recommend
	 * 
	 */

	static final float RATE_MENU_MIN = (1 - RATE_FACTOR * RATE_SCALE);

	int mode;

	int modetype_left_id = -1;

	int contentid = -1;

	int modetype_right_id = -1;

	int width = 0;

	int lastContentLeft;

	int lastMenuLeft;

	ViewDragHelper mHelper;

	View mContentView;

	View mMenuView2;

	View mMenuView;

	private GestureDetectorCompat mGestureDetector;

	Context context;

	public ShowStatus status = ShowStatus.Close;

	public enum ShowStatus {
		Open, Close, Drag
	}

	@Override
	protected void onFinishInflate() {
		mContentView = findViewById(contentid);
		if (modetype_left_id != -1)
			mMenuView = findViewById(modetype_left_id);
		if (modetype_right_id != -1)
			mMenuView2 = findViewById(modetype_right_id);
	}

	public ResideView(Context context) {
		this(context, null);
	}

	public ResideView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	public ResideView(Context context, AttributeSet attrs, int defstyle) {
		super(context, attrs, defstyle);
		mGestureDetector = new GestureDetectorCompat(context,
				new YScrollDetector());
		mHelper = ViewDragHelper.create(this, 1.0f,
				callback);

		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.ResideView);

		mode = ta.getInt(R.styleable.ResideView_mode, 0);

		switch (mode) {
		case 0: // left
			modetype_left_id = ta.getResourceId(R.styleable.ResideView_left_id,
					-1);
			if (modetype_left_id == -1)
				throw new InflateException(
						"\n ResideView xml attrs must define for left_id if you set mode with left or by default.");
			break;
		case 1: // right
			modetype_right_id = ta.getResourceId(
					R.styleable.ResideView_right_id, -1);
			if (modetype_right_id == -1)
				throw new InflateException(
						"\n ResideView xml attrs must define for right_id if you set mode with right.");
			break;
		case 2: // both
			modetype_left_id = ta.getResourceId(R.styleable.ResideView_left_id,
					-1);
			modetype_right_id = ta.getResourceId(
					R.styleable.ResideView_right_id, -1);
			if (modetype_left_id == -1)
				throw new InflateException(
						"\n ResideView xml attrs must define for left_id if you set mode with both.");
			if (modetype_right_id == -1)
				throw new InflateException(
						"\n ResideView xml attrs must define for right_id if you set mode with both.");
			break;

		default:
			break;
		}

		contentid = ta.getResourceId(R.styleable.ResideView_content_id, -1);
		if (contentid == -1)
			throw new InflateException(
					"\n ResideView xml attrs must define for content_id .");

		ta.recycle();
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		try {
			mHelper.processTouchEvent(ev);
		} catch (Exception ex) {
		}
		return true;
	}

	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return Math.abs(distanceY) < Math.abs(distanceX);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		mHelper.shouldInterceptTouchEvent(ev);
		if (ev.getAction() == MotionEvent.ACTION_DOWN
				|| ev.getAction() == MotionEvent.ACTION_UP) {
			mHelper.processTouchEvent(ev);
		}
		if (status == ShowStatus.Drag)
			return true;

		return mGestureDetector.onTouchEvent(ev);
	}

	public void Close() {
		if (mHelper.smoothSlideViewTo(mContentView, 0, 0))
			ViewCompat.postInvalidateOnAnimation(this);
	}

	public void Open() {
		Open(true);
	}

	public void Open(boolean isLeft) {
		if (isLeft && modetype_left_id != -1) {
			if (mHelper.smoothSlideViewTo(mContentView, width * 2, 0))
				ViewCompat.postInvalidateOnAnimation(this);
		} else if (modetype_right_id != -1) {
			if (mHelper.smoothSlideViewTo(mContentView, -width * 2, 0))
				ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	private void smoothSlideTo() {
		if (lastContentLeft >= 0) {
			if (lastContentLeft > width) {
				if (mHelper.smoothSlideViewTo(mContentView, width * 2, 0))
					ViewCompat.postInvalidateOnAnimation(this);
			} else {
				if (mHelper.smoothSlideViewTo(mContentView, 0, 0))
					ViewCompat.postInvalidateOnAnimation(this);
			}
		} else {
			if (lastContentLeft < -width) {
				if (mHelper.smoothSlideViewTo(mContentView, -width * 2, 0))
					ViewCompat.postInvalidateOnAnimation(this);
			} else {
				if (mHelper.smoothSlideViewTo(mContentView, 0, 0))
					ViewCompat.postInvalidateOnAnimation(this);
			}
		}
	}

	private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
		@Override
		public void onViewCaptured(View capturedChild, int activePointerId) {
			super.onViewCaptured(capturedChild, activePointerId);
		}

		@Override
		public boolean tryCaptureView(View arg0, int arg1) {
			return arg0 == mContentView;
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			if (left == 0) {
				status = ShowStatus.Close;
			} else if (Math.abs(left) == width * 2) {
				status = ShowStatus.Open;
			} else {
				status = ShowStatus.Drag;
			}
			if (modetype_left_id != -1 && left >= 0) {
				float rate = (float) left / (width * 2);
				float scale = rate * RATE_SCALE;
				ViewHelper.setScaleX(mContentView, 1 - scale);
				ViewHelper.setScaleY(mContentView, 1 - scale);
				ViewHelper.setAlpha(mContentView, 1 - scale);
				
				ViewHelper.setAlpha(mMenuView, rate);
				ViewHelper.setScaleY(mMenuView, RATE_MENU_MIN + scale
						* RATE_FACTOR);
				ViewHelper.setScaleX(mMenuView, RATE_MENU_MIN + scale
						* RATE_FACTOR);
				ViewHelper.setTranslationX(mMenuView,
						-(1 - rate) * mMenuView.getWidth());

			} else if (modetype_right_id != -1) {
				float rate = (float) -left / (width * 2);
				float scale = rate * RATE_SCALE;
				ViewHelper.setAlpha(mContentView, 1 - scale);
				ViewHelper.setScaleX(mContentView, 1 - scale);
				ViewHelper.setScaleY(mContentView, 1 - scale);

				ViewHelper.setAlpha(mMenuView2, rate);
				ViewHelper.setScaleY(mMenuView2, RATE_MENU_MIN + scale
						* RATE_FACTOR);
				ViewHelper.setScaleX(mMenuView2, RATE_MENU_MIN + scale
						* RATE_FACTOR);
				ViewHelper.setTranslationX(mMenuView2,
						(1 - rate) * mMenuView2.getWidth());

			}

		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {

			if (modetype_left_id != -1 && left >= 0) {
				final int newLeft = Math.min(left, width * 2);
				lastContentLeft = newLeft;
				return newLeft;
			} else if (modetype_right_id != -1 && left < 0) {
				final int newLeft = Math.max(left, -width * 2);
				lastContentLeft = newLeft;
				return newLeft;
			}
			return 0;
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			if (status == ShowStatus.Drag)
				smoothSlideTo();
		}
	};

	@Override
	public void computeScroll() {
		if (mHelper.continueSettling(true))
			ViewCompat.postInvalidateOnAnimation(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
		int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

		setMeasuredDimension(Math.min(maxWidth, widthMeasureSpec),
				Math.min(maxHeight, heightMeasureSpec));
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		// TODO Auto-generated method stub
		Log.e("=====================", "============onSaveInstanceState============");
		return super.onSaveInstanceState();
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// TODO Auto-generated method stub
		Log.e("=====================", "============onRestoreInstanceState============");
		super.onRestoreInstanceState(state);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		lastContentLeft = mContentView.getLeft();
		Log.e("=====================", "========================");
		width = this.getWidth() / 4;
		mContentView.layout(lastContentLeft, 0,
				lastContentLeft + mContentView.getMeasuredWidth(),
				mContentView.getMeasuredHeight());
		if (modetype_left_id != -1) {
			mMenuView.layout(0, 0, mMenuView.getMeasuredWidth(),
					mMenuView.getMeasuredHeight());
			ViewHelper.setTranslationX(mMenuView, -mMenuView.getWidth());
		}
		if (modetype_right_id != -1) {
			int left = this.getRight() - mMenuView2.getMeasuredWidth();
			mMenuView2.layout(left, 0, left + mMenuView2.getMeasuredWidth(),
					mMenuView2.getMeasuredHeight());
			ViewHelper.setTranslationX(mMenuView2, mMenuView2.getWidth());
		}
		callback.onViewPositionChanged(mContentView, lastContentLeft, 0, 0, 0);
	}

}
