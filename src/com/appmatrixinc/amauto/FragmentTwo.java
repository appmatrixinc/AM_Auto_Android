package com.appmatrixinc.amauto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentTwo   extends Fragment {

    ImageView ivIcon;
    TextView tvItemName;
    SharedPreferences sp;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    public FragmentTwo()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_layout_two,container, false);

        ivIcon=(ImageView)view.findViewById(R.id.frag2_icon);
        tvItemName=(TextView)view.findViewById(R.id.frag2_text);
        sp = getActivity().getSharedPreferences("preferences", 0);

        String string = sp.getString("registered", null);
        //tvItemName.setText("Registered");
        if(string != null){
            tvItemName.setText("Registered: " + string);
        }
        else{
            tvItemName.setText("Registered: not working");
        }


        //tvItemName.setText(getArguments().getString(ITEM_NAME));
        //ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));
        return view;
    }

}