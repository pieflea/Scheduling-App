package com.pieflea.classprojectfinal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pieflea.classprojectfinal.Models.Assessment;
import com.pieflea.classprojectfinal.Models.Course;
import com.pieflea.classprojectfinal.Models.Term;

import java.util.ArrayList;

//this class handles the sql methods for assesments, allowing for uploading and downloading sql models from the database.
public class AssessmentStorage extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AssessmentList.db";
    private static final int VERSION = 1;


    public AssessmentStorage(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final class AssessmentTable {
        private static final String TABLE = "Courses";
        private static final String COL_ID = "_id";
        private static final String COL_TITLE = "Title";
        private static final String COL_START = "Start_Date";
        private static final String COL_END = "End_Date";
        private static final String COL_START_TIME = "Start_Time";
        private static final String COL_END_TIME = "End_Time";
        private static final String COL_NOTES = "Notes";
        private static final String COL_COURSE = "Associated_Course_ID";
    }

    //this creates the table of assesments.
    //TODO: Decide if this should destroy the original table each time, or simply add new objects.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + AssessmentTable.TABLE + "" + " (" +
                AssessmentTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AssessmentTable.COL_TITLE + " TEXT, " +
                AssessmentTable.COL_START + " TEXT, " +
                AssessmentTable.COL_END + " TEXT, " +
                AssessmentTable.COL_START_TIME + " TEXT, " +
                AssessmentTable.COL_END_TIME + " TEXT, " +
                AssessmentTable.COL_NOTES + " TEXT, " +
                AssessmentTable.COL_COURSE + " LONG)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AssessmentTable.TABLE);
        onCreate(db);
    }

    //this method adds a new assessment to the SQL database
    public long addAssessment(String title, String startDate, String endDate, String startTime, String endTime, String Notes, Long Course) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues assessmentValues = new ContentValues();
        assessmentValues.put(AssessmentTable.COL_TITLE, title);
        assessmentValues.put(AssessmentTable.COL_START, startDate);
        assessmentValues.put(AssessmentTable.COL_END, endDate);
        assessmentValues.put(AssessmentTable.COL_START_TIME, startTime);
        assessmentValues.put(AssessmentTable.COL_END_TIME, endTime);
        assessmentValues.put(AssessmentTable.COL_NOTES, Notes);
        assessmentValues.put(AssessmentTable.COL_COURSE, Course);

        long AssesmentID = db.insert(AssessmentTable.TABLE, null, assessmentValues);
        return AssesmentID;
    }

    //this method adds each assesment to it's appropriate array under a course object.
    public void populateAssesments() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + AssessmentTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        for (Term term : DataHolder.terms) {
            for (Course course : term.courses) {
                course.assessments.clear();
                if (cursor.moveToFirst()) {
                    do {
                        if (course.courseID == cursor.getLong(7)) {
                            course.assessments.add(new Assessment(
                                    cursor.getLong(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(3),
                                    cursor.getString(4),
                                    cursor.getString(5),
                                    cursor.getString(6),
                                    course.termID,
                                    course.courseID));
                        }
                    }
                    while (cursor.moveToNext());
                }
            }
        }
    }

    public long saveAssessment(Long assessmentID, String title, String startDate, String endDate, String startTime, String endTime, String notes, Long courseID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues assessmentValues = new ContentValues();
        assessmentValues.put(AssessmentStorage.AssessmentTable.COL_TITLE, title);
        assessmentValues.put(AssessmentStorage.AssessmentTable.COL_START, startDate);
        assessmentValues.put(AssessmentStorage.AssessmentTable.COL_END, endDate);
        assessmentValues.put(AssessmentTable.COL_START_TIME, startTime);
        assessmentValues.put(AssessmentTable.COL_END_TIME, endTime);
        assessmentValues.put(AssessmentStorage.AssessmentTable.COL_NOTES, notes);
        assessmentValues.put(AssessmentTable.COL_COURSE, courseID.toString());


        long TermID = db.update(AssessmentStorage.AssessmentTable.TABLE, assessmentValues, "_id = ?", new String[]{assessmentID.toString()});
        return TermID;
    }

    public boolean removeAssessment(Long termID, Long courseID, Long assessmentID){
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(AssessmentStorage.AssessmentTable.TABLE, "_id = ?", new String[]{assessmentID.toString()});
        Course.getCourseByID(termID, courseID).assessments.remove(Assessment.getAssessmentByID(termID, courseID, assessmentID));
        Log.i("ID to remove", assessmentID.toString());
        Log.i("rows deleted", Integer.toString(rowsDeleted));
        return rowsDeleted > 0;
    }
}