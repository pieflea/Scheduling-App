package com.pieflea.classprojectfinal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.pieflea.classprojectfinal.Models.Event;
import com.pieflea.classprojectfinal.Models.Term;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventStorage extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EventsList.db";
    private static final int VERSION = 2;

    public EventStorage(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    private static final class EventTable {
        private static final String TABLE = "Events";
        private static final String COL_ID = "_id";
        private static final String COL_TERM = "Term";
        private static final String COL_TIME = "Time";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + EventTable.TABLE + "" + " (" +
                EventTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EventTable.COL_TERM + " TEXT, " +
                EventTable.COL_TIME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EventTable.TABLE);
        onCreate(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void populateEvents() {

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + EventTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Event> events = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                events.add(new Event(cursor.getString(1), cursor.getString(2)));
            }
            while (cursor.moveToNext());
            DataHolder.setEvents(events);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long addEvent(String term) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues eventValues = new ContentValues();
        eventValues.put(EventTable.COL_TIME, LocalDateTime.now().toString());
        eventValues.put(EventTable.COL_TERM, term);

        long EventID = db.insert(EventTable.TABLE, null, eventValues);
        return EventID;
    }
}
