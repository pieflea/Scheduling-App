package com.pieflea.classprojectfinal.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pieflea.classprojectfinal.Database.TimerIntentService;
import com.pieflea.classprojectfinal.R;

import java.util.Calendar;
import java.util.Date;

public class addNotification extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    int day, month, year, hour, minute;
    int myDay, myMonth, myYear, myHour, myMinute;
    EditText notificationName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        notificationName = findViewById(R.id.notificationTitle);
    }




        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.appbarlayout, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.helpButton:
                    Intent helpIntent = new Intent(this, HelpPage.class);
                    startActivity(helpIntent);
                    return true;
                case R.id.aboutButton:
                    Intent aboutIntent = new Intent(this, aboutPage.class);
                    startActivity(aboutIntent);
                    return true;

                case R.id.viewTerms:
                    Intent viewTermIntent = new Intent(this, viewTerms.class);
                    startActivity(viewTermIntent);
                    return true;

                case R.id.viewReport:
                    Intent viewReportIntent = new Intent(this, termReport.class);
                    startActivity(viewReportIntent);
                    return true;

            /* Deprecated features, more trouble than they were worth.
            case R.id.viewCourses:
                Intent viewCourseIntent = new Intent(this, viewCourses.class);
                startActivity(viewCourseIntent);
                return true;

            case R.id.viewAssesments:
                Intent viewAssessmentIntent = new Intent(this, viewAssessments.class);
                startActivity(viewAssessmentIntent);
                return true;*/

                default:
                    return super.onOptionsItemSelected(item);

            }
        }

    public void pickTime(View view) {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myMonth = month;
        myDay = dayOfMonth;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(myYear, myMonth, myDay, myHour, myMinute, 0);
        myHour = hourOfDay;
        myMinute = minute;
        CalendarView dateDisplay = findViewById(R.id.datePicker);
        dateDisplay.setDate(calendar.getTimeInMillis());
        TextView timeView = findViewById(R.id.notifyTime);
        timeView.setText(myHour + ":" + myMinute);
    }

    public void saveNotificationChanges(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(myYear, myMonth, myDay, myHour, myMinute, 0);
        Long currentTime = System.currentTimeMillis();
        Long notificationDate = calendar.getTimeInMillis();
        String notificationTitle = notificationName.getText().toString();

        Intent intent = new Intent(this, TimerIntentService.class);
        intent.putExtra("timeLeft", notificationDate - currentTime);
        intent.putExtra("notificationTitle", notificationTitle);
        intent.putExtra("notificationText", "Reminder about upcoming " + notificationTitle);
        startService(intent);
        String toastText = "Notification for " + notificationTitle + " set.";
        Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();
    }
}