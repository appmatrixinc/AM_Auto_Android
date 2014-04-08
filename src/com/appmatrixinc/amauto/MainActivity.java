package com.appmatrixinc.amauto;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import android.app.Fragment;
//import android.app.FragmentManager;

public class MainActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    //private ListView mDrawerList;
    ExpandableListView mDrawerList;
    HashMap<DrawerItem, List<String>> childItems;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;

    CustomDrawerAdapter adapter;
    List<DrawerItem> dataList;

    private String[] mGroupTitles;
    private TypedArray mGroupIcons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dataList = new ArrayList<DrawerItem>();
        childItems = new HashMap<DrawerItem, List<String>>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // From strings.xml to create group list
        mGroupTitles = getResources().getStringArray(R.array.group_array);
        mGroupIcons = getResources().obtainTypedArray(R.array.group_icon_array);

        // Remove group item open/close caret
        mDrawerList.setGroupIndicator(null);

        for ( int i = 0; i < mGroupTitles.length; ++i ) {
            String title = mGroupTitles[i];
            int icon = mGroupIcons.getResourceId(i, -1);
            dataList.add(new DrawerItem(title, icon));
        }

        //OLD CODE, workable but not as efficient
        // Add Drawer Item to dataList
        //dataList.add(new DrawerItem("Message", R.drawable.ic_action_email));
        //dataList.add(new DrawerItem("Likes", R.drawable.ic_action_good));
        //dataList.add(new DrawerItem("Games", R.drawable.ic_action_gamepad));
        /*
        dataList.add(new DrawerItem("Labels", R.drawable.ic_action_labels));
        dataList.add(new DrawerItem("Search", R.drawable.ic_action_search));
        dataList.add(new DrawerItem("Cloud", R.drawable.ic_action_cloud));
        dataList.add(new DrawerItem("Camera", R.drawable.ic_action_camera));
        dataList.add(new DrawerItem("Video", R.drawable.ic_action_video));
        dataList.add(new DrawerItem("Groups", R.drawable.ic_action_group));
        dataList.add(new DrawerItem("Import & Export", R.drawable.ic_action_import_export));
        dataList.add(new DrawerItem("About", R.drawable.ic_action_about));
        dataList.add(new DrawerItem("Settings", R.drawable.ic_action_settings));
        dataList.add(new DrawerItem("Help", R.drawable.ic_action_help));
        */


        //  Children Arrays
        // Empty array for group items that have no children
        List<String> empty = new ArrayList<String>();

        List<String> message = new ArrayList<String>();
        message.add("message 1");
        message.add("message 2");

        List<String> cloud = new ArrayList<String>();
        cloud.add("cloud 1");
        cloud.add("cloud 2");
        cloud.add("cloud 3");

        childItems.put(dataList.get(0), message);
        childItems.put(dataList.get(1), cloud);
        childItems.put(dataList.get(2), empty);

        // Initialize and set adapter
        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList, childItems);
        mDrawerList.setAdapter(adapter);

        // Initialize onclicklistener for parent group items
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Initialize onclicklistener for child items
        mDrawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                String title = childItems.get(dataList.get(groupPosition)).get(childPosition);
                Toast.makeText(
                        getApplicationContext(),
                        //dataList.get(groupPosition) + " : " + childItems.get(dataList.get(groupPosition)).get(childPosition),
                        //childItems.get(dataList.get(groupPosition)).get(childPosition) + ": group: " + groupPosition + ", child: " + childPosition,
                        title + ": " + childPosition,
                        Toast.LENGTH_SHORT)
                        .show();

                // Call appropriate child click function depending on parent clicked
                switch(groupPosition){
                    case 0:
                        SelectChildItem0(childPosition, title);
                        break;
                    case 1:
                        SelectChildItem1(childPosition, title);
                }

                return true;
            }
        });

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
            SelectItem(0);
        }
    }

    // Method to select list item
    public void SelectItem(int position) {

        Fragment fragment = null;
        Bundle args = new Bundle();

        switch(position) {
            case 0:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 1:
                fragment = new FragmentTwo();
                args.putString(FragmentTwo.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 2:
                fragment = new FragmentThree();
                args.putString(FragmentThree.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 3:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 4:
                fragment = new FragmentTwo();
                args.putString(FragmentTwo.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 5:
                fragment = new FragmentThree();
                args.putString(FragmentThree.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 6:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 7:
                fragment = new FragmentTwo();
                args.putString(FragmentTwo.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 8:
                fragment = new FragmentThree();
                args.putString(FragmentThree.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 9:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 10:
                fragment = new FragmentTwo();
                args.putString(FragmentTwo.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 11:
                fragment = new FragmentThree();
                args.putString(FragmentThree.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            case 12:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(position).getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position).getImgResID());
                break;
            default:
                break;

        }

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        setTitle(dataList.get(position).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    // Method to select child item
    public void SelectChildItem0(int position, String title) {

        Fragment fragment = null;
        Bundle args = new Bundle();

        switch(position) {
            case 0:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, title);
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
                break;
            case 1:
                fragment = new FragmentOne();
                args.putString(FragmentTwo.ITEM_NAME, title);
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_camera);
                break;
        }

            fragment.setArguments(args);
            FragmentManager frgManager = getSupportFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            setTitle(dataList.get(position).getItemName());
            mDrawerLayout.closeDrawer(mDrawerList);

    }
    public void SelectChildItem1(int position, String title) {

        Fragment fragment = null;
        Bundle args = new Bundle();

        switch(position) {
            case 0:
                fragment = new FragmentTwo();
                args.putString(FragmentOne.ITEM_NAME, title);
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, R.drawable.ic_action_about);
                break;
            case 1:
                fragment = new FragmentOne();
                args.putString(FragmentTwo.ITEM_NAME, title);
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_camera);
                break;
            case 2:
                fragment = new FragmentTwo();
                args.putString(FragmentTwo.ITEM_NAME, title);
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, R.drawable.ic_action_cloud);
                break;
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getSupportFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        setTitle(dataList.get(position).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    // Sets the action bar title
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
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

    // Calls SelectItem function on group item click if no children
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SelectItem(position);

        }
    }
}
