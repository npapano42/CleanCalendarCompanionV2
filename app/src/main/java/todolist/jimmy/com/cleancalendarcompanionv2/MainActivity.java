package todolist.jimmy.com.cleancalendarcompanionv2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Date;
import java.util.List;

import todolist.jimmy.com.cleancalendarcompanionv2.Objects.Task;

// class works with activity_main layout to display the main screen (summary view)
public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    Button btnToday, btnViewTask;
    DatePickerDialog.OnDateSetListener dateSetListener;
    FloatingActionButton fabAddTask;
    RecyclerView rvTaskList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    // method that executes when the view is layout is switched to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign xml to objects
        rvTaskList = (RecyclerView) findViewById(R.id.recyclerView_Tasks);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        btnToday = (Button) findViewById(R.id.btnSummary);
        btnViewTask = (Button) findViewById(R.id.btnViewTask);
        fabAddTask = (FloatingActionButton) findViewById(R.id.fabAddTask);
        fabAddTask.setColorFilter(Color.WHITE);

        // lists tasks
        List<Task> tasks = Task.getAllTasks(MainActivity.this);
        setRecyclerViewAdapter(tasks);

        // swaps to the viewTask activity and layout when task button is pressed, getting the date
        btnViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentViewTask = new Intent(MainActivity.this, ViewTaskActivity.class);
                intentViewTask.putExtra("selectedDate", calendarView.getDate());
                startActivity(intentViewTask);
            }
        });
        // same as method above, swaps to viewTask activity but doesn't pull the date
        btnViewTask.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intentViewTask = new Intent(MainActivity.this, ViewTaskActivity.class);
                startActivity(intentViewTask);
                return false;
            }
        });

        // gets today's date when the today button is pressed
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setDate(new Date().getTime(), true, true);
            }
        });

        // listener for the floating + button that jumps to the addTask activity
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddTask = new Intent(MainActivity.this, AddTaskActivity.class);
                intentAddTask.putExtra("selectedDate", calendarView.getDate());
                startActivity(intentAddTask);
            }
        });
    }

    // correctly list tasks in a scrollable view
    private void setRecyclerViewAdapter(List<Task> tasks) {
        adapter = new DataAdapter(MainActivity.this, tasks);
        rvTaskList.setAdapter(adapter);
        rvTaskList.setLayoutManager(layoutManager);
    }
}

