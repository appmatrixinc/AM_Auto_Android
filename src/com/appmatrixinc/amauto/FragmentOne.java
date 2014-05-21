package com.appmatrixinc.amauto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentOne extends Fragment {

    ImageView ivIcon;
    TextView tvItemName;
    SharedPreferences sp;
    Button preference;
    public static String filename = "preferences";

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    public FragmentOne() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout_one, container, false);

        ivIcon = (ImageView) view.findViewById(R.id.frag1_icon);
        tvItemName = (TextView) view.findViewById(R.id.frag1_text);
        preference = (Button) view.findViewById(R.id.button);
        sp = this.getActivity().getSharedPreferences(filename, 0);

        preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("registered", "false");
                editor.commit();
            }
        });


        //String string = sp.getString("registered", "no");
        //tvItemName.setText("Registered");
        //tvItemName.setText("Registered: " + string);

        //tvItemName.setText(getResources().getString(R.string.registered));

        tvItemName.setText(getArguments().getString(ITEM_NAME));
        //ivIcon.setImageDrawable(view.getResources().getDrawable(
                //getArguments().getInt(IMAGE_RESOURCE_ID)));
        return view;
    }

}
