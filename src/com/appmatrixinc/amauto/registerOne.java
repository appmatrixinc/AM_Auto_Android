package com.appmatrixinc.amauto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by jennaharris on 4/11/14.
 */
public class RegisterOne extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register1, container, false);
        Button register = (Button) view.findViewById(R.id.register_firstPage);
        final EditText firstName = (EditText) view.findViewById(R.id.first_name);
        final EditText lastName = (EditText) view.findViewById(R.id.last_name);
        final EditText email = (EditText) view.findViewById(R.id.email);
        String filename = "preferences";
        final SharedPreferences sp = this.getActivity().getSharedPreferences(filename, 0);


        View header = view.findViewById(R.id.header);
        ImageButton menu = (ImageButton) header.findViewById(R.id.menu_button);
        menu.setOnClickListener(MenuButton.menuToggle());

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mFirstName = firstName.getText().toString();
                String mLastName = lastName.getText().toString();
                String mEmail = email.getText().toString();
                Log.d("REGISTER", mFirstName + " " + mLastName + ", " + mEmail);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username", mFirstName + " " + mLastName);
                editor.putString("email", mEmail);
                editor.commit();

                Fragment fragment = new RegisterTwo();
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
