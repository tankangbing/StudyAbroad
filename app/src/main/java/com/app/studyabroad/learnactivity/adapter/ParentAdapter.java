package com.app.studyabroad.learnactivity.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.studyabroad.learnactivity.entity.ChildEntity;
import com.app.studyabroad.learnactivity.entity.ParentEntity;

import java.util.ArrayList;

import app.com.studyabroad.R;

public class ParentAdapter extends BaseExpandableListAdapter {

    private Context mContext;// 上下文

    private ArrayList<ParentEntity> mParents;// 数据源

    private OnChildTreeViewClickListener mTreeViewClickListener;// 点击子ExpandableListView子项的监听
    private LinearLayout inflate;

    public ParentAdapter(Context context, ArrayList<ParentEntity> parents) {
        this.mContext = context;
        this.mParents = parents;

    }

    @Override
    public ChildEntity getChild(int groupPosition, int childPosition) {
        return mParents.get(groupPosition).getChilds().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(mParents.size()>groupPosition){
            return mParents.get(groupPosition).getChilds() != null ? mParents
                    .get(groupPosition).getChilds().size() : 0;
        }else {
            return 0;
        }

    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {

        final ExpandableListView eListView = getExpandableListView();

        ArrayList<ChildEntity> childs = new ArrayList<ChildEntity>();

        final ChildEntity child = getChild(groupPosition, childPosition);

        childs.add(child);

        final ChildAdapter childAdapter = new ChildAdapter(this.mContext,
                childs);

//        inflate = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout
//                .child_child_item_head, null);

        eListView.setAdapter(childAdapter);
        int count = eListView.getCount();
        for (int i = 0;i<count;i++){//默认第一学期的item
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, (child
                    .getChildNames().size() + 1)
                    * (int) mContext.getResources().getDimension(
                    R.dimen.parent_expandable_list_child_height));
            eListView.setLayoutParams(lp);
            if (mTreeViewClickListener != null) {

                mTreeViewClickListener.onClickPosition(groupPosition,
                        childPosition, i);
            }
            eListView.expandGroup(i);
        }

        /**
         * @author Apathy、恒
         *
         *         点击子ExpandableListView子项时，调用回调接口
         * */
        eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1,
                                        int groupIndex, int childIndex, long arg4) {

                if (mTreeViewClickListener != null) {

                    mTreeViewClickListener.onClickPosition(groupPosition,
                            childPosition, childIndex);
                }
                return false;
            }
        });


        /**
         * @author Apathy、恒
         *
         *         子ExpandableListView展开时，因为group只有一项，所以子ExpandableListView的总高度=
         *         （子ExpandableListView的child数量 + 1 ）* 每一项的高度
         * */
        eListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (child
                        .getChildNames().size() + 1)
                        * (int) mContext.getResources().getDimension(
                        R.dimen.parent_expandable_list_child_height));
                eListView.setLayoutParams(lp);
            }
        });

        /**
         * @author Apathy、恒
         *
         *         子ExpandableListView关闭时，此时只剩下group这一项，
         *         所以子ExpandableListView的总高度即为一项的高度
         * */
        eListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
                        .getResources().getDimension(
                                R.dimen.parent_expandable_list_height));
                eListView.setLayoutParams(lp);
            }
        });
        return eListView;

    }

    /**
     * @author Apathy、恒
     *
     *         动态创建子ExpandableListView
     * */
    public ExpandableListView getExpandableListView() {
        ExpandableListView mExpandableListView = new ExpandableListView(
                mContext);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, (int) mContext
                .getResources().getDimension(
                        R.dimen.parent_expandable_list_height));
        mExpandableListView.setLayoutParams(lp);
        mExpandableListView.setDividerHeight(0);// 取消group项的分割线
        mExpandableListView.setChildDivider(null);// 取消child项的分割线
        mExpandableListView.setGroupIndicator(null);// 取消展开折叠的指示图标
        return mExpandableListView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParents.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mParents != null ? mParents.size() : 0;
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
                    R.layout.parent_group_item, null);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.update(mParents.get(groupPosition));
        return convertView;
    }

    /**
     * @author Apathy、恒
     *
     *         Holder优化
     * */
    class GroupHolder {

        private TextView parentGroupTV;

        public GroupHolder(View v) {
            parentGroupTV = (TextView) v.findViewById(R.id.parentGroupTV);
        }

        public void update(ParentEntity model) {
            parentGroupTV.setText(model.getGroupName());
            parentGroupTV.setTextColor(model.getGroupColor());
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * @author Apathy、恒
     *
     *         设置点击子ExpandableListView子项的监听
     * */
    public void setOnChildTreeViewClickListener(
            OnChildTreeViewClickListener treeViewClickListener) {
        this.mTreeViewClickListener = treeViewClickListener;
    }

    /**
     * @author Apathy、恒
     *
     *         点击子ExpandableListView子项的回调接口
     * */
    public interface OnChildTreeViewClickListener {

        void onClickPosition(int parentPosition, int groupPosition,
                             int childPosition);
    }

}
