package com.appmatrixinc.amauto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by jennaharris on 4/18/14.
 */
public class MyTools extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_tools, container, false);
        ImageButton emergencyRoadside = (ImageButton) view.findViewById(R.id.emergency_roadside);
        ImageButton flashlight = (ImageButton) view.findViewById(R.id.flashlight);
        ImageButton parkingMeter = (ImageButton) view.findViewById(R.id.parking_meter);
        ImageButton wherePark = (ImageButton) view.findViewById(R.id.where_park);
        ImageButton findParking = (ImageButton) view.findViewById(R.id.find_parking);
        ImageButton findGas = (ImageButton) view.findViewById(R.id.find_gas);

        View header = view.findViewById(R.id.header);
        ImageButton menu = (ImageButton) header.findViewById(R.id.menu_button);
        menu.setOnClickListener(MenuButton.menuToggle());

        emergencyRoadside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new EmergencyRoadside();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment).commit();
                getFragmentManager().executePendingTransactions();
            }
        });

        flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Flashlight();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment).commit();
                getFragmentManager().executePendingTransactions();
            }
        });

        return view;
    }
}
