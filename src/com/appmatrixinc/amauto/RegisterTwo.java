package com.appmatrixinc.amauto;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Calendar;
import java.util.List;

public class RegisterTwo extends Fragment {

    private int year;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register2, container, false);
        ImageButton addVehicleView = (ImageButton) view.findViewById(R.id.button_addvehicle);
        Button addVehicle = (Button) view.findViewById(R.id.add_vehicle);
        Button cancelView = (Button) view.findViewById(R.id.cancel_addVehicle);

        final EditText vehicleYear = (EditText) view.findViewById(R.id.vehicle_year);
        final EditText vehicleMake = (EditText) view.findViewById(R.id.vehicle_make);
        final EditText vehicleModel = (EditText) view.findViewById(R.id.vehicle_model);
        final LinearLayout newVehicleView = (LinearLayout) view.findViewById(R.id.new_vehicle_view);
        final TableLayout vehicleTable = (TableLayout) view.findViewById(R.id.vehicle_table);

        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newVehicleView.setVisibility(View.INVISIBLE);
                vehicleYear.setText("Year");
                vehicleMake.setText("Make");
                vehicleModel.setText("Model");
            }
        });

        addVehicleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newVehicleView.setVisibility(View.VISIBLE);
            }
        });

        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View tableRow = inflater.inflate(R.layout.vehicle_tablerow, null, false);
                final TableRow row = (TableRow) tableRow.findViewById(R.id.vehicle_tablerow);
                final ImageButton deleteVehicle = (ImageButton) tableRow.findViewById(R.id.delete_vehicle);
                final TextView vehicleDescription = (TextView) tableRow.findViewById(R.id.vehicle_description);
                final DataBaseHandler db = new DataBaseHandler(getActivity());

                deleteVehicle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TESTING", "row: " + row.getId() + ", button: " + v.getId());
                        vehicleTable.removeView(row);
                        db.deleteVehicle(vehicleDescription.getText().toString());
                    }
                });

                String newVehicle = vehicleYear.getText() + " " + vehicleMake.getText() + " " + vehicleModel.getText();

                db.addVehicle(newVehicle);
                newVehicleView.setVisibility(View.INVISIBLE);

                vehicleDescription.setText(newVehicle);
                vehicleTable.addView(tableRow);

                List<String> vehicleList = db.getAllVehicles();
                for (String vh : vehicleList) {
                    Log.d("VEHICLE", vh);
                }

            }
        });

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {

                vehicleYear.setText("" + mYear);
            }
        };

        vehicleYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new DatePickerDialog(getActivity(), datePickerListener, year, month, day).show();
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), datePickerListener, year, 1, 24);
                try{
                    java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                        if (datePickerDialogField.getName().equals("mDatePicker")) {
                            datePickerDialogField.setAccessible(true);
                            DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                            java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                            for (java.lang.reflect.Field datePickerField : datePickerFields) {
                                Log.i("test", datePickerField.getName());
                                if ("mDaySpinner".equals(datePickerField.getName())) {
                                    datePickerField.setAccessible(true);
                                    Object dayPicker = new Object();
                                    dayPicker = datePickerField.get(datePicker);
                                    ((View) dayPicker).setVisibility(View.GONE);
                                }
                                if ("mMonthSpinner".equals(datePickerField.getName())) {
                                    datePickerField.setAccessible(true);
                                    Object dayPicker = new Object();
                                    dayPicker = datePickerField.get(datePicker);
                                    ((View) dayPicker).setVisibility(View.GONE);
                                }
                            }
                        }

                    }
                }catch(Exception ex){
                }

                dpd.show();
            }
        });

        setCurrentDate();
        return view;
    }

    public void setCurrentDate() {

        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

    }

}
