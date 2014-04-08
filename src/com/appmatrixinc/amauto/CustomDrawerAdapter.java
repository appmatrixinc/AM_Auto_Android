package com.appmatrixinc.amauto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CustomDrawerAdapter extends BaseExpandableListAdapter {

    Context context;
    List<DrawerItem> drawerItemList;
    HashMap<DrawerItem, List<String>> childItems;
    int layoutResID;

    public CustomDrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems, HashMap<DrawerItem, List<String>> children) {
        //super(context, layoutResourceID, listItems);
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
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_drawer_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.drawer_childName);

        txtListChild.setText(childText);
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
        //String headerTitle = (String) getGroup(groupPosition);
        DrawerItemHolder drawerHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            drawerHolder = new DrawerItemHolder();

            //convertView = infalInflater.inflate(R.layout.custom_drawer_item, null);
            convertView = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) convertView.findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) convertView.findViewById(R.id.drawer_icon);
            convertView.setTag(drawerHolder);
        }
        else {
            drawerHolder = (DrawerItemHolder) convertView.getTag();

        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(groupPosition);
        drawerHolder.icon.setImageDrawable(convertView.getResources().getDrawable(dItem.getImgResID()));
        drawerHolder.ItemName.setText(dItem.getItemName());

        //TextView lblListHeader = (TextView) convertView.findViewById(R.id.drawer_itemName);
        //lblListHeader.setTypeface(null, Typeface.BOLD);
        //lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // OLD CODE from standard listview
    /*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         // TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.ItemName = (TextView) view.findViewById(R.id.drawer_itemName);
            drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

        drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(dItem.getImgResID()));
        drawerHolder.ItemName.setText(dItem.getItemName());

        return view;
    }
    */

    private static class DrawerItemHolder {
        TextView ItemName;
        ImageView icon;
    }


}
