//package com.example.dragviewdemo;
//
//import android.content.Context;
//import android.support.v4.view.MotionEventCompat;
//import android.support.v4.view.ViewCompat;
//import android.support.v4.widget.ViewDragHelper;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//
//public class DragGroupView extends ViewGroup {
//
//	ViewDragHelper mHelper;
//
//	View mView;
//
//	View mView2;
//
//	int lastLeft = 0;
//
//	int lastTop = 0;
//
//	int lastLeft2 = 0;
//
//	int lastTop2 = 0;
//
//	public View getmView() {
//		return mView;
//	}
//
//	public void setmView(View mView) {
//		this.mView = mView;
//	}
//
//	public DragGroupView(Context context) {
//		this(context, null);
//	}
//
//	public DragGroupView(Context context, AttributeSet attrs) {
//		this(context, attrs, 0);
//	}
//
//	public DragGroupView(Context context, AttributeSet attrs, int defstyle) {
//		super(context, attrs, defstyle);
//		mHelper = ViewDragHelper.create(this, 1.0f, new DragViewCallBack());
//	}
//
//	@Override
//	protected void onFinishInflate() {
//		mView = findViewById(R.id.view);
//		mView2 = findViewById(R.id.view2);
//	}
//
//	private void smoothSlideTo(int id) {
//		int dir = 0;
//		int left = this.getRight();
//		if (id == R.id.view) {
//			if (lastLeft + mView.getMeasuredWidth()/2 > left / 2)
//				dir = 1;
//			if (dir == 0) {
//				if (mHelper.smoothSlideViewTo(mView, 0, 0)) {
//					ViewCompat.postInvalidateOnAnimation(this);
//				}
//			} else {
//				if (mHelper.smoothSlideViewTo(mView,
//						left - mView.getMeasuredWidth(), 0)) {
//					ViewCompat.postInvalidateOnAnimation(this);
//				}
//			}
//		} else if (id == R.id.view2) {
//			if (lastLeft2 + mView2.getMeasuredWidth()/2 > left / 2)
//				dir = 1;
//			if (dir == 0) {
//				if (mHelper.smoothSlideViewTo(mView2, 0, 0)) {
//					ViewCompat.postInvalidateOnAnimation(this);
//				}
//			} else {
//				if (mHelper.smoothSlideViewTo(mView2,
//						left - mView2.getMeasuredWidth(), 0)) {
//					ViewCompat.postInvalidateOnAnimation(this);
//				}
//			}
//		}
//	}
//
//	@Override
//	public void computeScroll() {
//		if (mHelper.continueSettling(true)) {
//			ViewCompat.postInvalidateOnAnimation(this);
//		}
//	}
//
//	class DragViewCallBack extends ViewDragHelper.Callback {
//
//		@Override
//		public boolean tryCaptureView(View arg0, int arg1) {
//			return true;
//		}
//
//		@Override
//		public int clampViewPositionHorizontal(View child, int left, int dx) {
//			if (child.getId() == R.id.view) {
//				final int leftBound = getPaddingLeft();
//				final int rightBound = getWidth() - mView.getWidth();
//				final int newLeft = Math.min(Math.max(left, leftBound),
//						rightBound);
//				lastLeft = newLeft;
//				Log.e("lastLeft = ", ""+lastLeft);
//				return newLeft;
//			} else if (child.getId() == R.id.view2) {
//				final int leftBound = getPaddingLeft();
//				final int rightBound = getWidth() - mView2.getWidth();
//				final int newLeft = Math.min(Math.max(left, leftBound),
//						rightBound);
//				lastLeft2 = newLeft;
//				return newLeft;
//			}
//			return 0;
//		}
//
//		@Override
//		public int getViewHorizontalDragRange(View child) {
//			return super.getViewHorizontalDragRange(child);
//		}
//
//		@Override
//		public int getViewVerticalDragRange(View child) {
//			return super.getViewVerticalDragRange(child);
//		}
//
//		@Override
//		public void onViewPositionChanged(View changedView, int left, int top,
//				int dx, int dy) {
//			super.onViewPositionChanged(changedView, left, top, dx, dy);
////			requestLayout();
//		}
//
//		@Override
//		public int clampViewPositionVertical(View child, int top, int dy) {
//			if (child.getId() == R.id.view) {
//				final int topBound = getPaddingTop();
//				final int bottomBound = getHeight() - mView.getHeight();
//				final int newTop = Math.min(Math.max(top, topBound),
//						bottomBound);
//				lastTop = newTop;
////				lastTop2 = (int) (lastTop * 0.8);
//				return newTop;
//			} else if (child.getId() == R.id.view2) {
//				final int topBound = getPaddingTop();
//				final int bottomBound = getHeight() - mView2.getHeight();
//				final int newTop = Math.min(Math.max(top, topBound),
//						bottomBound);
//				lastTop2 = newTop;
////				lastTop = (int) (lastTop2 * 0.2);
//				return newTop;
//			}
//			return 0;
//		}
//
//		@Override
//		public void onViewReleased(View releasedChild, float xvel, float yvel) {
//			 smoothSlideTo(releasedChild.getId());
//		}
//
//		@Override
//		public void onViewDragStateChanged(int state) {
//			switch (state) {
//			case ViewDragHelper.STATE_DRAGGING:
//				Log.e("onViewDragStateChanged", "-- STATE_DRAGGING");
//				break;
//			case ViewDragHelper.STATE_IDLE:
//				Log.e("onViewDragStateChanged", "-- STATE_IDLE");
//
//				break;
//			case ViewDragHelper.STATE_SETTLING:
//				Log.e("onViewDragStateChanged", "-- STATE_SETTLING");
//
//				break;
//
//			default:
//				break;
//			}
//		}
//
//		@Override
//		public void onViewCaptured(View capturedChild, int activePointerId) {
//			super.onViewCaptured(capturedChild, activePointerId);
//		}
//
//	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		mHelper.processTouchEvent(event);
//		return true;
//	}
//
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		final int action = MotionEventCompat.getActionMasked(ev);
//		if (action == MotionEvent.ACTION_CANCEL
//				|| action == MotionEvent.ACTION_UP) {
//			mHelper.cancel();
//			return false;
//		}
//		return mHelper.shouldInterceptTouchEvent(ev);
//	}
//
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		measureChildren(widthMeasureSpec, heightMeasureSpec);
//
//		int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
//		int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
//
//		setMeasuredDimension(Math.min(maxWidth, widthMeasureSpec),
//				Math.min(maxHeight, heightMeasureSpec));
//	}
//
//	@Override
//	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		mView.layout(lastLeft, lastTop, lastLeft + mView.getMeasuredWidth(),
//				lastTop + mView.getMeasuredHeight());
//		int left2 = this.getRight() - mView2.getMeasuredWidth() - lastLeft;
//		mView2.layout(left2, lastTop2, left2 + mView2.getMeasuredWidth(),
//				lastTop2 + mView2.getMeasuredHeight());
//	}
//
//}
