package com.appmatrixinc.amauto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jennaharris on 5/5/14.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vehicleDB";
    private static final String TABLE_NAME = "vehicleList";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "vehicle";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VEHICLES_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
        db.execSQL(CREATE_VEHICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE ID EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addVehicle(String vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, vehicle);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String getVehicle(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NAME }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

        String vehicle = cursor.getString(1);

        return vehicle;
    }

    public List<String> getAllVehicles() {
        List<String> vehicleList = new ArrayList<String>();
        String selectQuery = "SELECT " + KEY_NAME + " FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                vehicleList.add(cursor.getString(0));
            }
            while(cursor.moveToNext());
        }
        return vehicleList;
    }

    public void deleteVehicle(String vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + " =?", new String[] { String.valueOf(vehicle) });
        db.close();
    }

    public int getVehicleCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
