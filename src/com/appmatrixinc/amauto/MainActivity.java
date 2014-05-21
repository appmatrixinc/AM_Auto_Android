package com.appmatrixinc.amauto;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// These are for newer android apk (14 and above) only - otherwise use v4 support library
//import android.app.Fragment;
//import android.app.FragmentManager;

public class MainActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    ExpandableListView mDrawerList;
    HashMap<DrawerItem, ArrayList<ChildDrawerItem>> childItems;
    private ActionBarDrawerToggle mDrawerToggle;
    int numChildren;

    CustomDrawerAdapter adapter;
    List<DrawerItem> dataList;

    private String[] mGroupTitles;
    private TypedArray mGroupBackgrounds;
    private TypedArray mDepartmentsBackgrounds;
    private TypedArray mShowroomBackgrounds;
    private TypedArray mMyToolsBackgrounds;
    private TypedArray mConnectBackgrounds;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dataList = new ArrayList<DrawerItem>();
        childItems = new HashMap<DrawerItem, ArrayList<ChildDrawerItem>>();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Remove group item open/close caret
        mDrawerList.setGroupIndicator(null);

        // From strings.xml to create group list
        mGroupTitles = getResources().getStringArray(R.array.group_array);
        mGroupBackgrounds = getResources().obtainTypedArray(R.array.group_background_notRegistered_array);
        mDepartmentsBackgrounds = getResources().obtainTypedArray(R.array.departments);
        mShowroomBackgrounds = getResources().obtainTypedArray(R.array.showroom);
        mMyToolsBackgrounds = getResources().obtainTypedArray(R.array.mytools);
        mConnectBackgrounds = getResources().obtainTypedArray(R.array.connect);

        // Create parent group rows
        for ( int i = 0; i < mGroupTitles.length; ++i ) {
            int background = mGroupBackgrounds.getResourceId(i, -1);
            dataList.add(new DrawerItem(background));
        }

        //// Create child rows
        //Departments
        ArrayList<ChildDrawerItem> departments = new ArrayList<ChildDrawerItem>();
        for ( int i = 0; i < 5; ++i ) {
            int background = mDepartmentsBackgrounds.getResourceId(i, -1);
            departments.add(new ChildDrawerItem(background));
        }
        //Showroom
        ArrayList<ChildDrawerItem> showroom = new ArrayList<ChildDrawerItem>();
        for ( int i = 0; i < 3; ++i ) {
            int background = mShowroomBackgrounds.getResourceId(i, -1);
            showroom.add(new ChildDrawerItem(background));
        }
        //MyTools
        ArrayList<ChildDrawerItem> mytools = new ArrayList<ChildDrawerItem>();
        for ( int i = 0; i < 6; ++i ) {
            int background = mMyToolsBackgrounds.getResourceId(i, -1);
            mytools.add(new ChildDrawerItem(background));
        }
        //ConnectShare
        ArrayList<ChildDrawerItem> connect = new ArrayList<ChildDrawerItem>();
        for ( int i = 0; i < 2; ++i ) {
            int background = mConnectBackgrounds.getResourceId(i, -1);
            connect.add(new ChildDrawerItem(background));
        }
        // Empty array for group items that have no children
        ArrayList<ChildDrawerItem> empty = new ArrayList<ChildDrawerItem>();

        //Add child arrays to parent items
        childItems.put(dataList.get(0), empty);
        childItems.put(dataList.get(1), empty);
        childItems.put(dataList.get(2), departments);
        childItems.put(dataList.get(3), showroom);
        childItems.put(dataList.get(4), empty);
        childItems.put(dataList.get(5), mytools);
        childItems.put(dataList.get(6), empty);
        childItems.put(dataList.get(7), connect);
        childItems.put(dataList.get(8), empty);

        // Initialize and set adapter
        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList, childItems);
        mDrawerList.setAdapter(adapter);

        // Initialize onclicklistener for parent group items
        mDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                /// Check if row has children
                numChildren = adapter.getChildrenCount(groupPosition);
                Log.d("numChildren:", "num: " + numChildren);

                //If parent has no children, change fragment
                if(numChildren == 0){
                    SelectItem(groupPosition);
                    return true;
                }
                //Else expand listview to reveal children
                else{
                    return false;
                }
            }
        });

        // Initialize onclicklistener for child items
        mDrawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {

                // Call appropriate child click function depending on parent clicked
                switch(groupPosition){

                    case 1:
                        SelectChildItem_MyInfo(childPosition);
                        break;
                    case 2:
                        SelectChildItem_Departments(childPosition);
                        break;
                    case 3:
                        SelectChildItem_Showroom(childPosition);
                        break;
                    case 5:
                        SelectChildItem_MyTools(childPosition);
                        break;
                    case 7:
                        SelectChildItem_ConnectShare(childPosition);
                        break;
                }

                return true;
            }
        });

        ////These can get called only for higher sdks
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        // creates call to onPrepareOptionsMenu()
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu();

            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu();

            }
        };

        // Optionally sets the menu icon to the arrow and dis-allows toggle of the drawer
        //mDrawerToggle.setDrawerIndicatorEnabled(false);

        // Set drawerlistener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Sets default menu item selection
        if (savedInstanceState == null) {
            SelectItem(8);
        }
    }

    // Method to select list item
    public void SelectItem(int position) {

        Fragment fragment = null;
        Bundle args = new Bundle();

        switch(position) {
            case 0:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, "Contact Us");
                //args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 1:
                fragment = new RegisterOne();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                fragment = new FragmentTwo();
                args.putString(FragmentTwo.ITEM_NAME, "Special Offers");
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 5:
                break;
            case 6:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, "News");
                //args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 7:
                break;
            case 8:
                fragment = new Dashboard();
                break;
            default:
                break;

        }

        //fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        //setTitle(dataList.get(position).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    // Methods to select child items
    //////////////////////////////
    public void SelectChildItem_MyInfo(int position) {

        Fragment fragment = null;
        Bundle args = new Bundle();

        switch(position) {
            case 0:
                fragment = new FragmentOne();
                //args.putString(FragmentOne.ITEM_NAME, title);
                //args.putInt(FragmentOne.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
                break;
            case 1:
                fragment = new FragmentOne();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_camera);
                break;
        }

            fragment.setArguments(args);
            FragmentManager frgManager = getSupportFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            //setTitle(dataList.get(position).getItemName());
            mDrawerLayout.closeDrawer(mDrawerList);

    }

    public void SelectChildItem_Departments(int position) {

        Fragment fragment = null;
        Bundle args = new Bundle();

        switch(position) {
            case 0:
                fragment = new Departments();
                //args.putString(FragmentOne.ITEM_NAME, title);
                //args.putInt(FragmentOne.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
                break;
            case 1:
                fragment = new FragmentOne();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_camera);
                break;
            case 2:
                fragment = new FragmentTwo();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_cloud);
                break;
            case 3:
                fragment = new FragmentOne();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_camera);
                break;
            case 4:
                fragment = new FragmentTwo();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_cloud);
                break;
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        //setTitle(dataList.get(position).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    public void SelectChildItem_Showroom(int position) {

        Fragment fragment = null;
        Bundle args = new Bundle();

        switch(position) {
            case 0:
                fragment = new FragmentTwo();
                //args.putString(FragmentOne.ITEM_NAME, title);
                //args.putInt(FragmentOne.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
                break;
            case 1:
                fragment = new FragmentOne();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_camera);
                break;
            case 2:
                fragment = new FragmentTwo();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_cloud);
                break;
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        //setTitle(dataList.get(position).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    public void SelectChildItem_MyTools(int position) {

        Fragment fragment = null;
        Bundle args = new Bundle();

        switch(position) {
            case 0:
                fragment = new FragmentTwo();
                //args.putString(FragmentOne.ITEM_NAME, title);
                //args.putInt(FragmentOne.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
                break;
            case 1:
                fragment = new FragmentOne();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_camera);
                break;
            case 2:
                fragment = new FragmentTwo();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_cloud);
                break;
            case 3:
                fragment = new FragmentOne();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_camera);
                break;
            case 4:
                fragment = new FragmentTwo();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_cloud);
                break;
            case 5:
                fragment = new FragmentTwo();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_cloud);
                break;
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        //setTitle(dataList.get(position).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    public void SelectChildItem_ConnectShare(int position) {

        Fragment fragment = null;
        Bundle args = new Bundle();

        switch(position) {
            case 0:
                fragment = new FragmentTwo();
                //args.putString(FragmentOne.ITEM_NAME, title);
                //args.putInt(FragmentOne.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
                break;
            case 1:
                fragment = new FragmentOne();
                //args.putString(FragmentTwo.ITEM_NAME, title);
                //args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_camera);
                break;
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        //setTitle(dataList.get(position).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }


    // Sets the action bar title - only for higher sdks
    @Override
    public void setTitle(CharSequence title) {
        //mTitle = title;
        //getActionBar().setTitle(mTitle);
    }

    // Sync the toggle state after onRestoreInstanceState has occurred.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    // The action bar home/up action should open or close the drawer.
    // ActionBarDrawerToggle will take care of this.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    // Pass any configuration change to the drawer toggles
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
