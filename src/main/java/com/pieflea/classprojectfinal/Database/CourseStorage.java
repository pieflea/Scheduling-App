package com.pieflea.classprojectfinal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.Models.Term;

import java.util.ArrayList;

public class CourseStorage extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CourseList.db";
    private static final int VERSION = 1;


    public CourseStorage(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class CourseTable {
        private static final String TABLE = "Courses";
        private static final String COL_ID = "_id";
        private static final String COL_TITLE = "Title";
        private static final String COL_START = "Start_Date";
        private static final String COL_END = "End_Date";
        private static final String COL_STATUS = "Status";
        private static final String COL_PROFNAME = "Instructor_Name";
        private static final String COL_PROFPHONE = "Instructor_Phone_Number";
        private static final String COL_PROFEMAIL = "Instructor_email";
        private static final String COL_NOTES = "Notes";
        private static final String COL_TERM = "Associated_Term_ID";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + CourseTable.TABLE + "" + " (" +
                CourseTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CourseTable.COL_TITLE + " TEXT, " +
                CourseTable.COL_START + " TEXT, " +
                CourseTable.COL_END + " TEXT, " +
                CourseTable.COL_STATUS + " BOOLEAN, " +
                CourseTable.COL_PROFNAME + " TEXT, " +
                CourseTable.COL_PROFPHONE + " TEXT, " +
                CourseTable.COL_PROFEMAIL + " TEXT, " +
                CourseTable.COL_NOTES + " TEXT, " +
                CourseTable.COL_TERM + " LONG)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CourseTable.TABLE);
        onCreate(db);
    }

    public long addCourse(String title, String startDate, String endDate, String status, String name, String phone, String email, String notes, Long Term) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues courseValues = new ContentValues();
        courseValues.put(CourseTable.COL_TITLE, title);
        courseValues.put(CourseTable.COL_START, startDate);
        courseValues.put(CourseTable.COL_END, endDate);
        courseValues.put(CourseTable.COL_STATUS, status);
        courseValues.put(CourseTable.COL_PROFNAME, name);
        courseValues.put(CourseTable.COL_PROFPHONE, phone);
        courseValues.put(CourseTable.COL_PROFEMAIL, email);
        courseValues.put(CourseTable.COL_NOTES, notes);
        courseValues.put(CourseTable.COL_TERM, Term);

        long CourseID = db.insert(CourseTable.TABLE, null, courseValues);
        return CourseID;
    }

    public void populateCourses() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + CourseTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        for (Term term : DataHolder.terms) {
            term.courses.clear();
            if (cursor.moveToFirst()) {
                do {
                    if (term.termID == cursor.getLong(9)) {
                        term.courses.add(new Course(
                                cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getLong(9)));
                    }
                }
                while (cursor.moveToNext());
            }
        }
    }
    public long saveCourse(Long courseID, String title, String startDate, String endDate, String status, String name, String phone, String email, String notes){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues courseValues = new ContentValues();
        courseValues.put(CourseStorage.CourseTable.COL_TITLE, title);
        courseValues.put(CourseStorage.CourseTable.COL_START, startDate);
        courseValues.put(CourseStorage.CourseTable.COL_END, endDate);
        courseValues.put(CourseTable.COL_STATUS, status);
        courseValues.put(CourseTable.COL_PROFNAME, name);
        courseValues.put(CourseTable.COL_PROFPHONE, phone);
        courseValues.put(CourseTable.COL_PROFEMAIL, email);
        courseValues.put(CourseStorage.CourseTable.COL_NOTES, notes);

        long TermID = db.update(CourseStorage.CourseTable.TABLE, courseValues, "_id = ?", new String[]{courseID.toString()});
        return TermID;
    }

    public boolean removeCourse(Long termID, Long courseID){
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(CourseStorage.CourseTable.TABLE, "_id = ?", new String[]{courseID.toString()});
        DataHolder.getTermByID(termID).courses.remove(Course.getCourseByID(termID, courseID));
        return rowsDeleted > 0;
    }
}