package com.app.studyabroad.learnactivity;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.app.studyabroad.ui.ViewHolder;
import com.app.studyabroad.util.BadgeView;
import com.app.studyabroad.util.ImageUtil;

import app.com.studyabroad.R;

public class GridViewAdapter extends SimpleAdapter {

	private Context context;
	
	public GridViewAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.context = context;
	}

	 @SuppressWarnings("unchecked")
	@Override  
	 public View getView(int position, View convertView, ViewGroup parent) {  
	       
	     ViewHolder viewHolder = null;  
	       
	     if (convertView == null) {
	    	 LayoutInflater inflater = LayoutInflater  
	                    .from(this.context);
	    	 convertView = inflater  
	                    .inflate(R.layout.activity_main_item, null);
	         viewHolder = new ViewHolder();  
	         viewHolder.itemTextView = (TextView) convertView.findViewById(R.id.itemName);  
	         viewHolder.itemImageView = (ImageView) convertView.findViewById(R.id.itemImage);  
	         convertView.setTag(viewHolder);  
	     } else {  
	         viewHolder = (ViewHolder) convertView.getTag();  
	     }  
	     
	     //����
	     Map<String,Object> map = (Map<String,Object>)getItem(position);
	     int imagepath  =  Integer.parseInt(map.get("itemImage").toString());
	     int txt    	=  Integer.parseInt(map.get("itemName").toString());
	    /*����δ������*/
	     Map<String,Object> m = (Map<String,Object>)getItem(position);
	     if(txt == R.string.main_msg){
	    	 if(null != m && m.containsKey("count")){
	    		 if(Integer.parseInt(m.get("count").toString()) > 0){
	    			 View target = convertView.findViewById(R.id.itemImage);
					 BadgeView badge = new BadgeView(convertView.getContext(), target);				 
					 badge.setText(m.get("count").toString());
					 badge.show(); 
	    		 }
	    		
	    	 }
	     }
	     if(txt == R.string.main_order){
	    	 if(null != m && m.containsKey("ordercount")){
	    		 if(Integer.parseInt(m.get("ordercount").toString()) > 0){
	    			 View target = convertView.findViewById(R.id.itemImage);
	    			 BadgeView badge = new BadgeView(convertView.getContext(), target);				 
	    			 badge.setText("●");
	    			 badge.show(); 
	    		 }
	    	 }
	     }
	     
	     setViewValue(viewHolder,txt,imagepath);
	     return convertView;  
	 }
	 
	 private void setViewValue(ViewHolder viewHolder,int txt,int image){
		 viewHolder.itemTextView.setText(txt);
	     ImageUtil.setImageSrc(viewHolder.itemImageView, context.getResources(), image);
	 }
	
}
