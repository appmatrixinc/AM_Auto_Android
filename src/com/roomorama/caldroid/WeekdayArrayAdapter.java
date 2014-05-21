package com.roomorama.caldroid;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Customize the weekday gridview
 */
public class WeekdayArrayAdapter extends ArrayAdapter<String> {
	public static int textColor = Color.WHITE;

	public WeekdayArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
	}

	// To prevent cell highlighted when clicked
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	// Set color to gray and text size to 12sp
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// To customize text size and color
		TextView textView = (TextView) super.getView(position, convertView, parent);
        //GridView weekdayGrid = (GridView) convertView.findViewById(R.id.weekday_gridview);

		// Set content
		String item = getItem(position);

		// Show smaller text if the size of the text is 4 or more in some
		// locale
		if (item.length() <= 3) {
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		} else {
			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
		}

		textView.setTextColor(textColor);
        if ((getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) != Configuration.SCREENLAYOUT_SIZE_SMALL) {
            //textView.setGravity(Gravity.CENTER);
            //textView.setPadding(0,5,0,0);
            //weekdayGrid.setPadding(0,5,0,0);
        }
        //else {
            textView.setGravity(Gravity.TOP);
        //}
		return textView;
	}

}
