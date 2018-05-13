package todolist.jimmy.com.cleancalendarcompanionv2;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import todolist.jimmy.com.cleancalendarcompanionv2.Helper.DateEx;
import todolist.jimmy.com.cleancalendarcompanionv2.Objects.Task;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

// class that goes with view_task.xml to display the to-do list
public class ViewTaskActivity extends AppCompatActivity {

    public static final String TAG = "ViewTaskActivity.java";
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RelativeLayout relativeLayout_endDate;
    RadioButton rbOn, rbBetween, rbAll;
    Button btnFilter;
    TextView tvDate, tvEndDate;

    DatePickerDialog.OnDateSetListener startDateSetListener;
    DatePickerDialog.OnDateSetListener endDateSetListener;
    List<Task> taskList;

    // method that runs when activity is swapped to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Assign all fields to objects
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Tasks);
        layoutManager = new LinearLayoutManager(ViewTaskActivity.this);
        relativeLayout_endDate = (RelativeLayout) findViewById(R.id.relativeLayout_endDate);
        rbOn = (RadioButton) findViewById(R.id.rbOn);
        rbBetween = (RadioButton) findViewById(R.id.rbBetween);
        rbAll = (RadioButton) findViewById(R.id.rbAll);
        btnFilter = (Button) findViewById(R.id.btnFilter);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);

        // pull correct date
        if(!getIntent().hasExtra("selectedDate")){
            taskList = Task.getAllTasks(ViewTaskActivity.this);
            rbAll.setChecked(true);
            tvDate.setText(DateEx.getDateString(new Date()));
            tvDate.setVisibility(View.INVISIBLE);
        }
        else{
            String date = DateEx.getDateString(new Date(getIntent().getExtras().getLong("selectedDate")));
            taskList = Task.getTasksByDate(ViewTaskActivity.this, date);
            tvDate.setText(date);
        }
        relativeLayout_endDate.setVisibility(View.INVISIBLE);
        setRecyclerViewAdapter(taskList);


        // get between button to correctly update date
        rbBetween.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    tvDate.setVisibility(View.VISIBLE);
                    relativeLayout_endDate.setVisibility(View.VISIBLE);
                    tvEndDate.setText(DateEx.getDateString(new Date()));
                }
            }
        });
        // get all button to show all tasks
        rbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    relativeLayout_endDate.setVisibility(View.INVISIBLE);
                    tvDate.setVisibility(View.INVISIBLE);
                }
            }
        });
        // get on button to show tasks on a specific date
        rbOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    relativeLayout_endDate.setVisibility(View.INVISIBLE);
                    tvDate.setVisibility(View.VISIBLE);
                }
            }
        });
        // start date displayed, can be tapped to adjust date
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewTaskActivity.this,
                        startDateSetListener,
                        DateEx.getYearOf(null),
                        DateEx.getMonthOf(null)-1,
                        DateEx.getDayOf(null));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });
        // end date displayed, can be tapped to adjust date
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewTaskActivity.this,
                        endDateSetListener,
                        DateEx.getYearOf(null),
                        DateEx.getMonthOf(null)-1,
                        DateEx.getDayOf(null));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        // adjusted the form of the start date to work
        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tvDate.setText((month+1) + "/" + day + "/" + year);
            }
        };

        // adjust the form of the end date to work
        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                tvEndDate.setText((month+1) + "/" + day + "/" + year);
            }
        };

        // get filter to get dates correctly
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbOn.isChecked()){
                    String date = null;
                    try {
                        date = DateEx.getFormatedDateString(tvDate.getText().toString().trim());
                    } catch (ParseException e) {
                        Log.e(TAG, "Error while parsing date on Filter date", e);
                    }
                    List<Task> tasks = Task.getTasksByDate(ViewTaskActivity.this, date);
                    setRecyclerViewAdapter(tasks);
                }else if(rbBetween.isChecked()){
                    String startDate = null;
                    try {
                        startDate = DateEx.getFormatedDateString(tvDate.getText().toString().trim());
                    } catch (ParseException e) {
                        Log.e(TAG, "Error while parsing start date", e);
                    }
                    String endDate = null;
                    try {
                        endDate = DateEx.getFormatedDateString(tvEndDate.getText().toString().trim());
                    } catch (ParseException e) {
                        Log.e(TAG, "Error while parsing end date", e);
                    }
                    List<Task> tasks = Task.getTasksByDateRange(ViewTaskActivity.this, startDate, endDate);
                    setRecyclerViewAdapter(tasks);
                }else {
                    List<Task> tasks = Task.getAllTasks(ViewTaskActivity.this);
                    setRecyclerViewAdapter(tasks);
                }
            }
        });

    }
    // correctly list tasks in a scrollable view
    private void setRecyclerViewAdapter(List<Task> tasks){
        adapter = new DataAdapter(ViewTaskActivity.this, tasks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

}

