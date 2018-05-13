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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import todolist.jimmy.com.cleancalendarcompanionv2.Database.TaskDB;
import todolist.jimmy.com.cleancalendarcompanionv2.Helper.DateEx;
import todolist.jimmy.com.cleancalendarcompanionv2.Objects.Task;

import java.text.ParseException;

// Used with the update_task xml file
public class UpdateTaskActivity extends AppCompatActivity {
    int oldTaskId;

    private static final String TAG = "UpdateTaskActivity";

    Button btnDone;
    EditText txtTaskName, txtTaskLocation, txtTaskDate, txtStartTime, txtEndTime, txtTaskDescription, txtTaskParticipants;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener startTimeSetListener, endTimeSetListener;
    CheckBox chkAllDay;

    // method that runs when screen is swapped to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // assign all fields to objects
        btnDone = (Button) findViewById(R.id.btnAddTask);
        txtTaskName = (EditText) findViewById(R.id.txtTaskName);
        txtTaskLocation = (EditText) findViewById(R.id.txtLocation);
        txtTaskDate = (EditText) findViewById(R.id.txtTaskDate);
        txtStartTime = (EditText) findViewById(R.id.txtStartTime);
        txtEndTime = (EditText) findViewById(R.id.txtEndTime);
        txtTaskDescription = (EditText) findViewById(R.id.txtTaskDescription);
        txtTaskParticipants = (EditText) findViewById(R.id.txtTaskParticipants);
        chkAllDay = (CheckBox) findViewById(R.id.chkAllDay);

        oldTaskId = getIntent().getExtras().getInt("oldTaskId");

        //Load old task onto screen
        TaskDB taskDB = new TaskDB(UpdateTaskActivity.this);
        Task tempTask = Task.getTaskById(oldTaskId, UpdateTaskActivity.this);
        txtTaskName.setText(tempTask.getTask_name());
        txtTaskDescription.setText(tempTask.getTask_description());
        txtTaskParticipants.setText(tempTask.getTask_participants());
        txtTaskLocation.setText(tempTask.getTask_location());
        txtTaskDate.setText(DateEx.getDateString(tempTask.getTask_date()));
        txtStartTime.setText(DateEx.getTimeString(tempTask.getTask_start()));
        txtEndTime.setText(DateEx.getTimeString(tempTask.getTask_end()));
        txtTaskDate.setText(DateEx.getDateString(tempTask.getTask_date()));



        // text to set the task date listener
        txtTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateTaskActivity.this,
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

        // listener to get the start time of the task
        txtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        UpdateTaskActivity.this,
                        startTimeSetListener,
                        12,0,
                        false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        // listener to get the end time of the task
        txtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        UpdateTaskActivity.this,
                        endTimeSetListener,
                        12, 0,
                        false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        // next 3 chunks convert date and times to be in correct format for use
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i+"-"+(i1+1)+"-"+i2;
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


        // adjust time for all day if button is checked
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

        // sets all the data, updates task, and puts it back into the database
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task newTask = new Task();
                newTask.setTask_id(0);
                newTask.setTask_name(txtTaskName.getText().toString().trim());
                newTask.setTask_location(txtTaskLocation.getText().toString().trim());
                newTask.setTask_description(txtTaskDescription.getText().toString().trim());
                try {
                    newTask.setTask_date(DateEx.getDateOfDate(txtTaskDate.getText().toString().trim()));
                    newTask.setTask_start(DateEx.getDateOfTime(txtStartTime.getText().toString().trim()));
                    newTask.setTask_end(DateEx.getDateOfTime(txtEndTime.getText().toString().trim()));
                } catch (ParseException e) {
                    Log.e(TAG, "Error while parsing data into task class", e);
                }
                newTask.setTask_participants(txtTaskParticipants.getText().toString().trim());
                newTask.setIs_all_day_task(chkAllDay.isChecked());


                TaskDB taskDB = new TaskDB(UpdateTaskActivity.this);
                if(taskDB.update(newTask, oldTaskId)){
                    Toast.makeText(UpdateTaskActivity.this, "Reminder updated successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(UpdateTaskActivity.this, "Error while updating..", Toast.LENGTH_LONG).show();
                    Log.w(TAG, "Error got while updating taskId: "+oldTaskId);
                }
            }
        });
    }

}