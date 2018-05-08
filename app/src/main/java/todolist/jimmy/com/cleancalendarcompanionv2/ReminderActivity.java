package todolist.jimmy.com.cleancalendarcompanionv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import todolist.jimmy.com.cleancalendarcompanionv2.Helper.DateEx;
import todolist.jimmy.com.cleancalendarcompanionv2.Helper.ReminderActivator;
import todolist.jimmy.com.cleancalendarcompanionv2.Models.Task;

public class ReminderActivity extends AppCompatActivity {

    TextView txtTaskName, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        txtTaskName = (TextView) findViewById(R.id.txtTaskName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);

        Task task = (Task)getIntent().getSerializableExtra("task");
        txtTaskName.setText(task.getTask_name().toUpperCase());
        String description = task.getTask_description()+
                "\nLocation: "+task.getTask_location()+
                "\nOn: "+DateEx.getDateString(task.getTask_date());
        if(task.is_all_day_task()){
            description = description
                    +"\nIn whole day.";
        }else{
            description = description
                    +"\nFrom: "+DateEx.getTimeString(task.getTask_start())+" to "+DateEx.getTimeString(task.getTask_end())+".";
        }
        if(task.getTask_participants().length() > 0){
            description = description
                    +"\n"+task.getTask_participants()+" will be with you!";
        }
        txtDescription.setText(description);
    }

    public void RemindMeAgain(View view){
        Task task = (Task)getIntent().getSerializableExtra("task");
        ReminderActivator.postponeReminder(ReminderActivity.this, task, 1);
        finish();
    }

    public void StopReminder(View view){
        Task task = (Task)getIntent().getSerializableExtra("task");
        ReminderActivator.suspendReminder(ReminderActivity.this, task);
        finish();
    }
}
