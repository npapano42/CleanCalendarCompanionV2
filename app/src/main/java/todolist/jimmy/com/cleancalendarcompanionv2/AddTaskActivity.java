package todolist.jimmy.com.cleancalendarcompanionv2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import todolist.jimmy.com.cleancalendarcompanionv2.Database.TaskDB;
import todolist.jimmy.com.cleancalendarcompanionv2.Helper.DateEx;
import todolist.jimmy.com.cleancalendarcompanionv2.Helper.ReminderActivator;
import todolist.jimmy.com.cleancalendarcompanionv2.Models.Task;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

// Class AddTask, attached to activity_add_task.xml layout
// Takes inputted fields and stores them as a task object in the Database

public class AddTaskActivity extends AppCompatActivity {
    EditText txtTaskName, txtTaskLocation, txtTaskDate, txtStartTime, txtEndTime;
    EditText txtTaskDescription, txtTaskParticipants;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener startTimeSetListener, endTimeSetListener;
    Spinner spinnerSounds, spinnerTaskNotificationTime;
    ArrayAdapter<CharSequence> soundList;
    CheckBox chkAllDay;
    Button btnAddTask;

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
        spinnerTaskNotificationTime = (Spinner) findViewById(R.id.spinnerTaskNotificationTime);
        spinnerSounds = (Spinner) findViewById(R.id.spinnerSounds);
        chkAllDay = (CheckBox) findViewById(R.id.chkAllDay);
        btnAddTask = (Button) findViewById(R.id.btnAddTask);


        //Variables Definition
        final long selectedDate = (long)getIntent().getExtras().get("selectedDate");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(selectedDate));
        txtTaskDate.setText(DateEx.getDateString(calendar.getTime()));
        soundList = ArrayAdapter.createFromResource(AddTaskActivity.this, R.array.sound_list, R.layout.support_simple_spinner_dropdown_item);
        soundList.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSounds.setAdapter(soundList);

        // task date on-click listener lambda that when inputted a task date, stores it
        txtTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTaskActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog,
                        dateSetListener,
                        DateEx.getYearOf(null),
                        DateEx.getMonthOf(null),
                        DateEx.getDayOf(null)
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        // task start time on-click listener lambda that assigns the task a start time, storing it
        txtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddTaskActivity.this,
                        startTimeSetListener,
                        12,0,
                        false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        // task end time on-click listener lambda that assigns the task an end time, storing it
        txtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddTaskActivity.this,
                        endTimeSetListener,
                        12, 0,
                        false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });


        // converts listener input into valid inputs for task
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = year+"-"+(month+1)+"-"+day;
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

        // spinner that allows user to see the list of sounds
        spinnerSounds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MediaPlayer mediaPlayer = null;
                String soundName = adapterView.getItemAtPosition(i).toString().toLowerCase();
                switch (soundName){
                    case "buzz" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.buzz);
                        break;
                    case "gets in the way" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.gets_in_the_way);
                        break;
                    case "jingle bells" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.jingle_bells);
                        break;
                    case "rooster" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.rooster);
                        break;
                    case "siren" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.siren);
                        break;
                    case "system fault" :
                        mediaPlayer = MediaPlayer.create(AddTaskActivity.this, R.raw.system_fault);
                        break;
                }
                if(mediaPlayer != null)
                    mediaPlayer.start();
            }

            // required override to avoid unwanted superclass behavior
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // updates time in case all day is checked
        chkAllDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(chkAllDay.isChecked()) {
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
                else
                    {
                    task.setTask_description(txtTaskDescription.getText().toString().trim());
                }
                task.setTask_participants(txtTaskParticipants.getText().toString().trim());
                task.setIs_all_day_task(chkAllDay.isChecked());
                task.setTask_notification_sound(spinnerSounds.getSelectedItem().toString().trim());
                String notify = (String) spinnerTaskNotificationTime.getSelectedItem();
                switch(notify){
                    case "On time" :
                        task.setTask_notification_time(task.getTask_start());
                        break;
                    case "Before 2 minutes" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 2));
                        break;
                    case "Before 5 minutes" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 5));
                        break;
                    case "Before 10 minutes" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 10));
                        break;
                    case "Before 30 minutes" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 30));
                        break;
                    case "Before 1 hour" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 60));
                        break;
                    case "Before 3 hours" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 180));
                        break;
                    case "Before 1 day" :
                        task.setTask_notification_time(DateEx.addMinutesTo(task.getTask_start(), 1440));
                        break;
                    default:
                        task.setTask_notification_time(task.getTask_start());
                        break;
                }

                if(task.getTask_notification_time().before(new Date())){
                    Snackbar.make(view, "Cannot set reminders for past events", Snackbar.LENGTH_LONG)
                            .setAction("OK", null).show();
                }else{
                    TaskDB taskDB = new TaskDB(AddTaskActivity.this);
                    if(taskDB.insert(task)){
                        ReminderActivator.runActivator(AddTaskActivity.this, task);
                        Toast.makeText(AddTaskActivity.this, "Reminder added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                        Toast.makeText(AddTaskActivity.this, "Error while saving reminder", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
