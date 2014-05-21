package com.appmatrixinc.amauto;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jennaharris on 4/18/14.
 */
public class RequestService extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.request_service, container, false);
        Button registerNow = (Button) view.findViewById(R.id.register_now);
        ImageButton contactAdvisor = (ImageButton) view.findViewById(R.id.contact_service_advisor);
        Spinner chooseVehicle = (Spinner) view.findViewById(R.id.choose_vehicle);
        DataBaseHandler db = new DataBaseHandler(getActivity());

        //Set menu control
        View header = view.findViewById(R.id.header);
        ImageButton menu = (ImageButton) header.findViewById(R.id.menu_button);
        menu.setOnClickListener(MenuButton.menuToggle());

        //Set vehicle spinner data from database
        List<String> vehicleList = db.getAllVehicles();
        vehicleList.add(0, "Choose your Vehicle");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, vehicleList);
        chooseVehicle.setAdapter(adapter);
        chooseVehicle.setSelection(vehicleList.indexOf("Choose your Vehicle"));

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RegisterOne();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        contactAdvisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String phone_cs = getResources().getString(R.string.contact_service_advisor);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phone_cs));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });

        //Begin Calendar functions
        CaldroidFragment caldroidFragment = new CaldroidFragment();
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                SimpleDateFormat formatter = new SimpleDateFormat();
                Toast.makeText(getActivity(),  "Date: " + formatter.format(date), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                //Toast.makeText(getApplicationContext(), text,
                        //Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                //Toast.makeText(getApplicationContext(),
                        //"Long click " + formatter.format(date),
                        //Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                //Toast.makeText(getApplicationContext(),
                        //"Caldroid view is created",
                        //Toast.LENGTH_SHORT).show();
            }

        };

        caldroidFragment.setCaldroidListener(listener);
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);
        caldroidFragment.setCaldroidListener(listener);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.addToBackStack(null);
        ft.replace(R.id.calendar_view, caldroidFragment);
        ft.commit();

        return view;
    }
}
