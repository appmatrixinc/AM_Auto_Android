package com.appmatrixinc.amauto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomDrawerAdapter extends BaseExpandableListAdapter {

    Context context;
    List<DrawerItem> drawerItemList;
    HashMap<DrawerItem, ArrayList<ChildDrawerItem>> childItems;
    int layoutResID;

    public CustomDrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems, HashMap<DrawerItem, ArrayList<ChildDrawerItem>> children) {
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
        this.childItems = children;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childItems.get(drawerItemList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildDrawerItemHolder childItemHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            childItemHolder = new ChildDrawerItemHolder();

            convertView = inflater.inflate(R.layout.child_drawer_item, parent, false);
            childItemHolder.background = (ImageView) convertView.findViewById(R.id.child_drawer_background);
            convertView.setTag(childItemHolder);
        }
        else {
            childItemHolder = (ChildDrawerItemHolder) convertView.getTag();
        }

        ChildDrawerItem childItem = (ChildDrawerItem) getChild(groupPosition, childPosition);
        childItemHolder.background.setBackgroundDrawable(convertView.getResources().getDrawable(childItem.getImgBackID()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childItems.get(this.drawerItemList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.drawerItemList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.drawerItemList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        DrawerItemHolder drawerHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            drawerHolder = new DrawerItemHolder();

            convertView = inflater.inflate(layoutResID, parent, false);
            drawerHolder.background = (LinearLayout) convertView.findViewById(R.id.drawer_background);
            convertView.setTag(drawerHolder);
        }
        else {
            drawerHolder = (DrawerItemHolder) convertView.getTag();
        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(groupPosition);
        drawerHolder.background.setBackgroundDrawable(convertView.getResources().getDrawable(dItem.getImgBackID()));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class DrawerItemHolder {
        LinearLayout background;
    }

    private static class ChildDrawerItemHolder {
        ImageView background;
    }


}
