package com.app.studyabroad.adapter;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * ע��㲥��������״̬
 * @author Amao
 *
 */
@SuppressLint("ShowToast")
public class MyReceiver extends BroadcastReceiver {  
	
	private final static int NET_Y = 1;//������
	private final static int NET_N = 0;//������
	
	public MyReceiver(){};
	
    @Override  
    public void onReceive(Context context, Intent intent) {
    	switch (getNetworkState(context)) {
		case 0:
			Toast.makeText(context, "��������������ӣ�", 1000).show();  
			break;
		}
    }  

         
    /**
     * ��ȡ��ǰ����״̬
     * @param context
     * @return
     */
    public static int getNetworkState(Context context){
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connManager.getActiveNetworkInfo();
        if(null != activeInfo){
        	return NET_Y;
        }else{
        	 return NET_N;
        }
    }
}  
