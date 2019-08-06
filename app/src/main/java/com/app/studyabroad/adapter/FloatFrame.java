package com.app.studyabroad.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import com.app.studyabroad.util.ApplicationUtil;

import app.com.studyabroad.R;

@SuppressLint("ViewConstructor")
public class FloatFrame extends LinearLayout {
	private LinearLayout playOrStop_but;
	private VideoView video;
	private View view;
	private MediaController mc;

	public FloatFrame(Context context,VideoView myVideo,MediaController m,ApplicationUtil ap) {
		super(context);
		view = View.inflate(context, R.layout.float_frame, null);
		playOrStop_but = (LinearLayout)view.findViewById(R.id.play_stop_video_but);
		playOrStop_but.setOnClickListener(lis);
		
		
//		this.setLayoutParams(new LinearLayout.LayoutParams(1, 1));
		this.video = myVideo;
		this.mc = m;
		this.addView(view);
		ap.setView(view);
	}
	
	private OnClickListener lis = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(null != video){
	    		if(video.isPlaying()){
	    			if(null != mc){
	    				mc.show();
	    			}
	    			video.pause();
	    		}else{
	    			//view.setVisibility(View.INVISIBLE);
	    			video.start();
	    		}
	    	}			
		}
	};

}
