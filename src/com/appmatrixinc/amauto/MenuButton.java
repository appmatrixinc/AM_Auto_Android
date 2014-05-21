package com.appmatrixinc.amauto;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * Created by jennaharris on 4/28/14.
 */
public class MenuButton {

    public static View.OnClickListener menuToggle() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View parent = (View) view.getRootView();
                DrawerLayout mDrawerLayout = (DrawerLayout) parent.findViewById(R.id.drawer_layout);
                ExpandableListView mDrawerList = (ExpandableListView) parent.findViewById(R.id.left_drawer);

                if(parent != null) {
                    if(mDrawerLayout.isDrawerOpen(mDrawerList)){
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                    else{
                        mDrawerLayout.openDrawer(mDrawerList);
                    }
                }
            };
        };
    }
}
