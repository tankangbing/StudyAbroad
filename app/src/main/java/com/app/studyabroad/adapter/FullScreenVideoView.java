package com.app.studyabroad.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.VideoView;

import com.app.studyabroad.util.ApplicationUtil;

public class FullScreenVideoView extends VideoView implements MediaPlayerControl{  
	
	private View view;
	private WindowManager wm;
	private WindowManager.LayoutParams wmParams;
	private FloatFrame myFV; //��Ƶ��ͣ/��ʼ����ͼƬ
	private FloatVideoLoad myLoad;//��Ƶ���ظ���ͼƬ
	private Context c;
	
	private PlayPauseListener mListener; //�Զ�����Ƶ������
	  
	public FullScreenVideoView(Context context) {  
		super(context);
		this.c = context;
	}  
	public FullScreenVideoView (Context context, AttributeSet attrs)  
	{  
		super(context,attrs); 
		this.c = context;
	}  
	public FullScreenVideoView(Context context, AttributeSet attrs,int defStyle)  
	{  
		super(context,attrs,defStyle);  
		this.c = context;
	}  
	@Override  
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
	{
	  view = ((ApplicationUtil) getContext() .getApplicationContext()).getView();
	  //��ȫ�ֲ������ȡ
	  myFV = ((ApplicationUtil) getContext() .getApplicationContext()).getFloatFv();
	  myLoad = ((ApplicationUtil) getContext() .getApplicationContext()).getFloatLoad();
	  wm = ((ApplicationUtil) getContext() .getApplicationContext()).getWm();
	  wmParams = ((ApplicationUtil) getContext() .getApplicationContext()).getWmParams();
	  c = ((ApplicationUtil) getContext() .getApplicationContext()).getC();
	  
	  WindowManager win = (WindowManager) getContext()
              .getSystemService(Context.WINDOW_SERVICE);

	  int width = win.getDefaultDisplay().getWidth();
	  int height = win.getDefaultDisplay().getHeight();
	  if(width > height){
		  setMeasuredDimension(width, height);
	  }else{
		  int measuredHeight = measureHeight(heightMeasureSpec);
		  int measuredWidth = measureWidth(widthMeasureSpec);
		  setMeasuredDimension(measuredWidth, measuredHeight);  
	  }
	}
	
	private int measureHeight(int measureSpec) {

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		// Default size if no limits are specified.

		int result = 500;
		if (specMode == MeasureSpec.AT_MOST) {

			// Calculate the ideal size of your
			// control within this maximum size.
			// If your control fills the available
			// space return the outer bound.

			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY) {

			// If your control can fit within these bounds return that value.
			result = specSize;
		}

		return result;
	}

	private int measureWidth(int measureSpec) {
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		// Default size if no limits are specified.
		int result = 500;
		if (specMode == MeasureSpec.AT_MOST) {
			// Calculate the ideal size of your control
			// within this maximum size.
			// If your control fills the available space
			// return the outer bound.
			result = specSize;
		}

		else if (specMode == MeasureSpec.EXACTLY) {
			// If your control can fit within these bounds return that value.

			result = specSize;
		}

		return result;
	}
	
	
	@Override
	public void pause() {
		super.pause();
		if(null != this && null != wm && null != myFV && null != c){
			FloatingFunc.show(c, wm, myFV,getResources().getDisplayMetrics());
    	}	
		 if (mListener != null) {
	            mListener.onPause();
	     }
	}
	
	@Override
	public void start() {		
		if(null != this && null != c){
			FloatingFunc.close(c);
    	}
		 if (mListener != null) {
	            mListener.onPlay();
	     }
		super.start();
	}
	
	@SuppressLint("NewApi")
	@Override
	public void setOnInfoListener(OnInfoListener l) {
		super.setOnInfoListener(l);
	}
	
	@Override
	public void setOnErrorListener(OnErrorListener l) {
		super.setOnErrorListener(l);
	}
	
	 public interface PlayPauseListener {
	        void onPlay();
	        void onPause();
	  }
	 
	 public void setPlayPauseListener(PlayPauseListener listener) {
	        mListener = listener;
	 }
	 
	 @Override
	public void seekTo(int msec) {
		super.seekTo(msec);
	}
}