package com.app.studyabroad.adapter;

import app.com.studyabroad.R;
import com.app.studyabroad.util.ApplicationUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class FloatVideoLoad extends LinearLayout {
	
	private View view;
	private TextView txt;

	public FloatVideoLoad(Context context) {
		super(context);
	}
	
	public FloatVideoLoad(Context context,VideoView myVideo,MediaController m,ApplicationUtil ap,Integer type) {
		super(context);
		view = View.inflate(context, R.layout.float_loading_video, null);
		txt = (TextView)view.findViewById(R.id.floatfuc_txt);
		if(1==type){ //��Ƶ������ʾ
			txt.setText("��Ƶ���ڻ�����,���Ե�...");
			txt.setTextColor(0xFF339900);
		}else if(2==type){//��Ƶ�޷�������ʾ
			txt.setText("����Ƶ�ݲ�֧�����ֻ�����...");
			txt.setTextColor(Color.RED);
		}
		this.addView(view);
		ap.setView(view);
	}


}
