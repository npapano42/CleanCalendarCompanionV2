package todolist.jimmy.com.cleancalendarcompanionv2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;


import todolist.jimmy.com.cleancalendarcompanionv2.Database.TaskDB;
import todolist.jimmy.com.cleancalendarcompanionv2.Helper.DateEx;
import todolist.jimmy.com.cleancalendarcompanionv2.Objects.Task;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

// Class AddTask, attached to activity_add_task.xml layout
// Takes inputted fields and stores them as a task object in the Database
public class AddTaskActivity extends AppCompatActivity {
    EditText txtTaskName, txtTaskLocation, txtTaskDate, txtStartTime, txtEndTime, txtTaskDescription, txtTaskParticipants;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener startTimeSetListener, endTimeSetListener;
    CheckBox allDayCheck;
    Button btnAddTask, btnSetDate, btnStartTime, btnEndTime;

    int oldTaskId = -1;
    private static final String TAG = "AddTaskActivity";

    @Override
    // Code executed when activity is created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // assign all fields to objects
        txtTaskName = (EditText) findViewById(R.id.txtTaskName);
        txtTaskLocation = (EditText) findViewById(R.id.txtLocation);
        txtTaskDate = (EditText) findViewById(R.id.txtTaskDate);
        txtStartTime = (EditText) findViewById(R.id.txtStartTime);
        txtEndTime = (EditText) findViewById(R.id.txtEndTime);
        txtTaskDescription = (EditText) findViewById(R.id.txtTaskDescription);
        txtTaskParticipants = (EditText) findViewById(R.id.txtTaskParticipants);
        allDayCheck = (CheckBox) findViewById(R.id.chkAllDay);
        btnAddTask = (Button) findViewById(R.id.btnAddTask);
        btnSetDate = (Button) findViewById(R.id.btnSetDate);
        btnStartTime = (Button) findViewById(R.id.btnStartTime);
        btnEndTime = (Button) findViewById(R.id.btnEndTime);

        //Variables Definition
        final long selectedDate = (long)getIntent().getExtras().get("selectedDate");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(selectedDate));
        txtTaskDate.setText(DateEx.getDateString(calendar.getTime()));

        // task date on-click listener lambda that when inputted a task date, stores it
        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTaskActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog,
                        dateSetListener,
                        DateEx.getYearOf(null),
                        DateEx.getMonthOf(null) -1,
                        DateEx.getDayOf(null)

                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        // task start time on-click listener lambda that assigns the task a start time, storing it
        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddTaskActivity.this,
                        startTimeSetListener,
                        12,0,
                        false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                timePickerDialog.show();
            }
        });

        // task end time on-click listener lambda that assigns the task an end time, storing it
        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddTaskActivity.this,
                        endTimeSetListener,
                        12, 0,
                        false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                timePickerDialog.show();
            }
        });

        // converts listener input into valid inputs for task
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = (month+1)+"/"+day+"/"+year;
                txtTaskDate.setText(date);
            }
        };

        startTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + ":" + i1;
                txtStartTime.setText(time);
            }
        };

        endTimeSetListener = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String time = i + ":" + i1;
                txtEndTime.setText(time);
            }
        };

        // updates time in case all day is checked
        allDayCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(allDayCheck.isChecked()) {
                    txtStartTime.setText(DateEx.getTimeString(DateEx.getTodayMorning()));
                    txtEndTime.setText(DateEx.getTimeString(DateEx.getTodayMidNight()));
                    txtStartTime.setEnabled(false);
                    txtEndTime.setEnabled(false);
                }
                else{
                    txtStartTime.setEnabled(true);
                    txtEndTime.setEnabled(true);
                }
            }
        });

        // Creates the task object with inputs, storing the required fields and adding it to the database
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oldTaskId != -1){
                    finish();
                    return;
                }
                Task task = new Task();
                task.setTask_id(0);
                task.setTask_name(txtTaskName.getText().toString().trim());
                task.setTask_location(txtTaskLocation.getText().toString().trim());
                try {
                    task.setTask_date(DateEx.getDateOfDate(txtTaskDate.getText().toString().trim()));
                } catch (ParseException e) {
                    Log.e(TAG, "Error while date parsing of task_date", e);
                }
                try {
                    task.setTask_start(DateEx.getDateOfTime(txtStartTime.getText().toString().trim()));
                } catch (ParseException e) {
                    Log.e(TAG, "Error while date parsing of task_startTime", e);
                }
                try {
                    task.setTask_end(DateEx.getDateOfTime(txtEndTime.getText().toString().trim()));
                } catch (ParseException e) {
                    Log.e(TAG, "Error while date parsing of task_endTime", e);
                }

                if (txtTaskDescription.getText().toString().trim().equals(""))
                {
                    task.setTask_description("No Description");
                }
                else {
                    task.setTask_description(txtTaskDescription.getText().toString().trim());
                }
                task.setTask_participants(txtTaskParticipants.getText().toString().trim());
                task.setIs_all_day_task(allDayCheck.isChecked());

                    TaskDB taskDB = new TaskDB(AddTaskActivity.this);
                    if(taskDB.insert(task)){
                        Toast.makeText(AddTaskActivity.this, "Reminder added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                        Toast.makeText(AddTaskActivity.this, "Error while saving reminder", Toast.LENGTH_LONG).show();
            }
        });
    }
}
