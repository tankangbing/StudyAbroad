package com.app.studyabroad.util;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class DialogUtil {
	
	public static AlertDialog getDilog(Context c,Drawable d,String t,String m){
		AlertDialog a = new AlertDialog.Builder(c)
		.setIcon(d)
		.setTitle(t)
		.setMessage(m)
		.create();
		return a;
	}
	

}
