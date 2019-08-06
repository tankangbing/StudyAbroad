package com.app.studyabroad.learnactivity.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.app.studyabroad.learnactivity.entity.ChildEntity;

import java.util.ArrayList;

import app.com.studyabroad.R;

public class ChildAdapter extends BaseExpandableListAdapter {

    private Context mContext;// 上下文

    private ArrayList<ChildEntity> mChilds;// 数据源

    public ChildAdapter(Context context, ArrayList<ChildEntity> childs) {
        this.mContext = context;
        this.mChilds = childs;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChilds.get(groupPosition).getChildNames() != null ? mChilds
                .get(groupPosition).getChildNames().size() : 0;
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        if (mChilds.get(groupPosition).getChildNames() != null
                && mChilds.get(groupPosition).getChildNames().size() > 0)
            return mChilds.get(groupPosition).getChildNames()
                    .get(childPosition).toString();
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.child_child_item, null);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        String[] split = getChild(groupPosition, childPosition).split("#");
        holder.update(split[0],split[1],childPosition);
        return convertView;
    }

    /**
     * @author Apathy、恒
     *
     *         Holder优化
     * */
    class ChildHolder {

        private TextView childChildTV;
        private TextView childChildTyle;

        public ChildHolder(View v) {
            childChildTV = (TextView) v.findViewById(R.id.childChildTV);
            childChildTyle = (TextView) v.findViewById(R.id.childChildTyle);
        }

        public void update(String str,String tyle,int groupPosition) {
            childChildTV.setText(str);
            childChildTyle.setText(tyle);
            childChildTyle.setTextColor(Color.parseColor("#000000"));
            if(!str.contains("成绩")){
                float i = Float.parseFloat(str);
                String strColor;
                if(i>=0&&i<60){
                    strColor = "#f40707";
                }else {
                    strColor = "#676767";
                }
                childChildTV.setTextColor(Color.parseColor(strColor));
            }
            if(groupPosition == 0 ){
                childChildTV.setBackgroundResource(R.color.colorCheck_item);
                childChildTyle.setBackgroundResource(R.color.colorCheck_item);
            }else {
                childChildTV.setBackgroundResource(R.color.colorWhite);
                childChildTyle.setBackgroundResource(R.color.colorWhite);
            }
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (mChilds != null && mChilds.size() > 0)
            return mChilds.get(groupPosition);
        return null;
    }

    @Override
    public int getGroupCount() {
        return mChilds != null ? mChilds.size() : 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.child_group_item, null);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.update(mChilds.get(groupPosition));
        return convertView;
    }

    /**
     * @author Apathy、恒
     *
     *         Holder优化
     * */
    class GroupHolder {

        private TextView childGroupTV;

        public GroupHolder(View v) {
            childGroupTV = (TextView) v.findViewById(R.id.childGroupTV);
        }

        public void update(ChildEntity model) {
            childGroupTV.setText(model.getGroupName());
            childGroupTV.setTextColor(model.getGroupColor());
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        /**
         * ==============================================
         * 此处必须返回true，否则无法响应子项的点击事件===============
         * ==============================================
         **/
        return true;
    }

}
