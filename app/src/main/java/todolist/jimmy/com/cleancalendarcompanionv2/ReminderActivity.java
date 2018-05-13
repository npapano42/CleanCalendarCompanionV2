package todolist.jimmy.com.cleancalendarcompanionv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import todolist.jimmy.com.cleancalendarcompanionv2.Helper.DateEx;
import todolist.jimmy.com.cleancalendarcompanionv2.Objects.Task;

// Class attached with the reminder xml file
// Pops up when reminder time is triggered
public class ReminderActivity extends AppCompatActivity {

    TextView txtTaskName, txtDescription;

    // executes when activity is switched to
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // assign textviews to their xml
        txtTaskName = (TextView) findViewById(R.id.txtTaskName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);

        // pulls the task from the database, parsing it's input
        Task task = (Task)getIntent().getSerializableExtra("task");
        txtTaskName.setText(task.getTask_name().toUpperCase());
        String description = task.getTask_description()+
                "\nLocation: "+task.getTask_location()+
                "\nOn: "+ DateEx.getDateString(task.getTask_date());
        if(task.is_all_day_task())
        {
            description = description
                    +"\nIn whole day.";
        }
        else
            {
            description = description
                    +"\nFrom: "+DateEx.getTimeString(task.getTask_start())+" to "+DateEx.getTimeString(task.getTask_end())+".";
        }
        if(task.getTask_participants().length() > 0)
        {
            description = description
                    +"\n"+task.getTask_participants()+" will be with you!";
        }
        txtDescription.setText(description);
    }

}
