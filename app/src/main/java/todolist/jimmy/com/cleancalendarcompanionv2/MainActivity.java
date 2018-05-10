package todolist.jimmy.com.cleancalendarcompanionv2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import todolist.jimmy.com.cleancalendarcompanionv2.Helper.ReminderActivator;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    Button btnToday, btnViewTask;
    DatePickerDialog.OnDateSetListener dateSetListener;
    FloatingActionButton fabAddTask;

    //TODO Remove button created for testing
//    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        btnToday = (Button) findViewById(R.id.btnToday);
        btnViewTask = (Button) findViewById(R.id.btnViewTask);
        fabAddTask = (FloatingActionButton) findViewById(R.id.fabAddTask);

//        btnTest = (Button) findViewById(R.id.btnTest);


        /*
        calendarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout()
            {
                Toast.makeText(getApplicationContext(), "Long press", Toast.LENGTH_LONG).show();
            }
        });*/

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setDate(new Date().getTime(), true, true);
            }
        });


        btnViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentViewTask = new Intent(MainActivity.this, ViewTaskActivity.class);
                intentViewTask.putExtra("selectedDate", calendarView.getDate());
                startActivity(intentViewTask);
            }
        });

        btnViewTask.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intentViewTask = new Intent(MainActivity.this, ViewTaskActivity.class);
                startActivity(intentViewTask);
                return false;
            }
        });

        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddTask = new Intent(MainActivity.this, AddTaskActivity.class);
                intentAddTask.putExtra("selectedDate", calendarView.getDate());
                startActivity(intentAddTask);
            }
        });


//        //TODO Testbtn listner
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ReminderActivator.runActivator(MainActivity.this);
//            }
//        });
    }
}

