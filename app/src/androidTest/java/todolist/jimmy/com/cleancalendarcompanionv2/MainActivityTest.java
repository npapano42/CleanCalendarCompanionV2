package todolist.jimmy.com.cleancalendarcompanionv2;

import android.app.Activity;
import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    public ActivityTestRule<ViewTaskActivity> viewTaskActivityActivityTestRule  = new ActivityTestRule<>(ViewTaskActivity.class);



    private MainActivity mainActivity =null;
    private ViewTaskActivity viewTaskActivity = null;
    Instrumentation.ActivityMonitor viewTaskMonitor = getInstrumentation().addMonitor(ViewTaskActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor addTaskMonitor = getInstrumentation().addMonitor(AddTaskActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor adapterMonitor = getInstrumentation().addMonitor(DataAdapter.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
        viewTaskActivity = viewTaskActivityActivityTestRule.getActivity();


    }
    @Test
    public void testLunchView(){// main view

        assertNotNull(mainActivity.findViewById(R.id.calendarView));

    }
    @Test
    public void testFabAddTask1(){// add button(on the bottom right  ) on the main view
        assertNotNull(mainActivity.findViewById(R.id.fabAddTask));
        onView(withId(R.id.fabAddTask)).perform(click());
        Activity addTaskActivity = getInstrumentation().waitForMonitorWithTimeout(addTaskMonitor,10000);
        assertNotNull(addTaskActivity);


        // test the text Name field
        assertNotNull(addTaskActivity.findViewById(R.id.txtTaskName));
        onView(withId(R.id.txtTaskName)).perform(click());

//        // test the text Location field
//        assertNotNull(addTaskActivity.findViewById(R.id.txtTaskLocation));
//        onView(withId(R.id.txtTaskLocation)).perform(click());



        addTaskActivity.finish();


    }

    @Test
    public void testFabAddTask2(){// add button(on the bottom right  ) on the main view
        assertNotNull(mainActivity.findViewById(R.id.fabAddTask));
        onView(withId(R.id.fabAddTask)).perform(click());
        Activity addTaskActivity = getInstrumentation().waitForMonitorWithTimeout(addTaskMonitor,10000);
        assertNotNull(addTaskActivity);

        assertNotNull(addTaskActivity.findViewById(R.id.txtTaskDate));
        onView(withId(R.id.txtTaskDate)).perform(click());


////        // test the text Location field
//        assertNotNull(addTaskActivity.findViewById(R.id.txtTaskLocation));
//        onView(withId(R.id.txtTaskLocation)).perform(click());



        addTaskActivity.finish();
    }
    @Test
    public void testFabAddTask3(){// add button(on the bottom right  ) on the main view
        assertNotNull(mainActivity.findViewById(R.id.fabAddTask));
        onView(withId(R.id.fabAddTask)).perform(click());
        Activity addTaskActivity = getInstrumentation().waitForMonitorWithTimeout(addTaskMonitor,5000);
        assertNotNull(addTaskActivity);

        // test the text starting time field
        assertNotNull(addTaskActivity.findViewById(R.id.txtStartTime));
        onView(withId(R.id.txtStartTime)).perform(click());



        addTaskActivity.finish();
    }

    @Test
    public void testFabAddTask4(){// add button(on the bottom right  ) on the main view
        assertNotNull(mainActivity.findViewById(R.id.fabAddTask));
        onView(withId(R.id.fabAddTask)).perform(click());
        Activity addTaskActivity = getInstrumentation().waitForMonitorWithTimeout(addTaskMonitor,5000);
        assertNotNull(addTaskActivity);

        // test the ending time field
        assertNotNull(addTaskActivity.findViewById(R.id.txtEndTime));
        onView(withId(R.id.txtEndTime)).perform(click());



        addTaskActivity.finish();
    }
    @Test
    public void testFabAddTask5(){// add button(on the bottom right  ) on the main view
        assertNotNull(mainActivity.findViewById(R.id.fabAddTask));
        onView(withId(R.id.fabAddTask)).perform(click());
        Activity addTaskActivity = getInstrumentation().waitForMonitorWithTimeout(addTaskMonitor,5000);
        assertNotNull(addTaskActivity);

        // test the description field
        assertNotNull(addTaskActivity.findViewById(R.id.txtTaskDescription));
        onView(withId(R.id.txtTaskDescription)).perform(click());


        assertNotNull(addTaskActivity.findViewById(R.id.txtTaskParticipants));
        onView(withId(R.id.txtTaskParticipants)).perform();







        addTaskActivity.finish();
    }

    @Test
    public void testFabAddTask6(){// add button(on the bottom right  ) on the main view
        assertNotNull(mainActivity.findViewById(R.id.fabAddTask));
        onView(withId(R.id.fabAddTask)).perform(click());
        Activity addTaskActivity = getInstrumentation().waitForMonitorWithTimeout(addTaskMonitor,5000);
        assertNotNull(addTaskActivity);

        assertNotNull(addTaskActivity.findViewById(R.id.chkAllDay));
        onView(withId(R.id.chkAllDay)).perform(click());




        addTaskActivity.finish();
    }
    @Test
    public void testFabAddTask7(){// add button(on the bottom right  ) on the main view
        assertNotNull(mainActivity.findViewById(R.id.fabAddTask));
        onView(withId(R.id.fabAddTask)).perform(click());
        Activity addTaskActivity = getInstrumentation().waitForMonitorWithTimeout(addTaskMonitor,8000);
        assertNotNull(addTaskActivity);

        assertNotNull(addTaskActivity.findViewById(R.id.btnAddTask));
        onView(withId(R.id.btnAddTask)).perform(click());


        addTaskActivity.finish();
    }




    @Test
    public void testViewTaskButtonAndViewTaskActivity(){// VIEW button on the main view
        assertNotNull(mainActivity.findViewById(R.id.btnViewTask));
        onView(withId(R.id.btnViewTask)).perform(click());
        Activity viewTaskActivity = getInstrumentation().waitForMonitorWithTimeout(viewTaskMonitor,5000);
        assertNotNull(viewTaskActivity);

        //on button
        assertNotNull(viewTaskActivity.findViewById(R.id.rbOn));
        onView(withId(R.id.rbOn)).perform(click());

        // Between button
        assertNotNull(viewTaskActivity.findViewById(R.id.rbBetween));
        onView(withId(R.id.rbBetween)).perform(click());
        // All button
        assertNotNull(viewTaskActivity.findViewById(R.id.rbAll));
        onView(withId(R.id.rbAll)).perform(click());

        // filter button
        assertNotNull(viewTaskActivity.findViewById(R.id.btnFilter));
        onView(withId(R.id.btnFilter)).perform(click());

        Activity adapterActivity = getInstrumentation().waitForMonitorWithTimeout(adapterMonitor,5000);

//        assertNotNull(adapterActivity.findViewById(R.id.));
//        onView(withId(R.id.)).perform(click());




        // onView(withId(R.id.rbOn)).perform(click());

        viewTaskActivity.finish();
    }
    @Test
    public void testTodayButton(){// TODAY button on the main view
        assertNotNull(mainActivity.findViewById(R.id.btnToday));
        onView(withId(R.id.btnToday)).perform(click());
    }









    @After
    public void tearDown() throws Exception {
        mainActivity=null;
    }
}