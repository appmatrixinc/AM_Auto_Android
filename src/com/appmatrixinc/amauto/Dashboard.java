package com.appmatrixinc.amauto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class Dashboard extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, container, false);

        ImageButton contact = (ImageButton) view.findViewById(R.id.contact_button);
        ImageButton inventory = (ImageButton) view.findViewById(R.id.inventory_button);
        ImageButton service = (ImageButton) view.findViewById(R.id.service_button);
        ImageButton mytools = (ImageButton) view.findViewById(R.id.mytools_button);

        View header = view.findViewById(R.id.header);
        ImageButton menu = (ImageButton) header.findViewById(R.id.menu_button);
        menu.setOnClickListener(MenuButton.menuToggle());

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ContactUs();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment).commit();
                getFragmentManager().executePendingTransactions();
            }
        });

        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Inventory();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment).commit();
                getFragmentManager().executePendingTransactions();
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new RequestService();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft.addToBackStack(null);
                ft.replace(R.id.content_frame, fragment).commit();
                getFragmentManager().executePendingTransactions();

            }
        });

        mytools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyTools();
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
