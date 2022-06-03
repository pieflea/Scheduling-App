package com.pieflea.classprojectfinal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.pieflea.classprojectfinal.Models.Term;
import com.pieflea.classprojectfinal.UI.MainActivity;

import java.util.ArrayList;

public class TermStorage extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TermsList.db";
    private static final int VERSION = 1;


    public TermStorage(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class TermTable {
        private static final String TABLE = "Terms";
        private static final String COL_ID = "_id";
        private static final String COL_TITLE = "Title";
        private static final String COL_START = "Start_Date";
        private static final String COL_END = "End_Date";
        private static final String COL_NOTES = " Notes";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TermTable.TABLE + "" + " (" +
                TermTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TermTable.COL_TITLE + " TEXT, " +
                TermTable.COL_START + " TEXT, " +
                TermTable.COL_END + " TEXT, " +
                TermTable.COL_NOTES + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TermTable.TABLE);
        onCreate(db);
    }

    public long addTerm(String title, String startDate, String endDate, String notes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues termValues = new ContentValues();
        termValues.put(TermTable.COL_TITLE, title);
        termValues.put(TermTable.COL_START, startDate);
        termValues.put(TermTable.COL_END, endDate);
        termValues.put(TermTable.COL_NOTES, notes);

        MainActivity.eventStorage.addEvent(title);

        long TermID = db.insert(TermTable.TABLE, null, termValues);
        return TermID;
    }
    public void populateTerms(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + TermTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<Term> terms = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                terms.add(new Term(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)));
            }
            while (cursor.moveToNext());
            DataHolder.setTerms(terms);
        }
    }
    public long saveTerms(Long termID, String title, String startDate, String endDate, String notes){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues termValues = new ContentValues();
        termValues.put(TermTable.COL_TITLE, title);
        termValues.put(TermTable.COL_START, startDate);
        termValues.put(TermTable.COL_END, endDate);
        termValues.put(TermTable.COL_NOTES, notes);

        long TermID = db.update(TermTable.TABLE, termValues, "_id = ?", new String[]{termID.toString()});
        return TermID;
    }

    public boolean removeTerm(Long termID){
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(TermTable.TABLE, "_id = ?", new String[]{termID.toString()});
        DataHolder.terms.remove(DataHolder.getTermByID(termID));
        return rowsDeleted > 0;
    }
}
