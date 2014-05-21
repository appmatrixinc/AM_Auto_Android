package com.appmatrixinc.amauto;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by jennaharris on 5/6/14.
 */
public class EmergencyRoadside extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.emergency_roadside, container, false);
        ImageButton emergencyCallNow = (ImageButton) view.findViewById(R.id.emergency_call_now);

        View header = view.findViewById(R.id.header);
        ImageButton menu = (ImageButton) header.findViewById(R.id.menu_button);
        menu.setOnClickListener(MenuButton.menuToggle());

        emergencyCallNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String phone_cs = getResources().getString(R.string.emergency_call_now);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone_cs));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });

        return view;
    }
}
