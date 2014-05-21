package com.appmatrixinc.amauto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by jennaharris on 4/18/14.
 */
public class Inventory extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inventory, container, false);

        View header = view.findViewById(R.id.header);
        ImageButton menu = (ImageButton) header.findViewById(R.id.menu_button);
        menu.setOnClickListener(MenuButton.menuToggle());

        return view;
    }
}
