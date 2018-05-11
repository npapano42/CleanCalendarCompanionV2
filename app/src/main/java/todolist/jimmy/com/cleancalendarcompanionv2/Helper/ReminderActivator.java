package todolist.jimmy.com.cleancalendarcompanionv2.Helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import todolist.jimmy.com.cleancalendarcompanionv2.Objects.Task;
import todolist.jimmy.com.cleancalendarcompanionv2.ReminderActivity;

import java.util.Calendar;


public abstract class ReminderActivator
{
    public static void runActivator(Context context, Task task){
        Intent intent = new Intent(context, NotificationReciever.class);
        intent.putExtra("ui", new Intent(context, ReminderActivity.class));
        intent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        System.out.println("Test notification time "+task.getTask_notification_time().toString());
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, task.getTask_notification_time().getTime(), pendingIntent);
    }

    public static void postponeReminder(Context context, Task task, int minutes){
        Intent intent = new Intent(context, NotificationReciever.class);
        intent.putExtra("ui", new Intent(context, ReminderActivity.class));
        intent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static void changeReminder(Context context, Task task, int minutes){
        Intent intent = new Intent(context, NotificationReciever.class);
        intent.putExtra("ui", new Intent(context, ReminderActivity.class));
        intent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static void suspendReminder(Context context, Task task){
        Intent intent = new Intent(context, NotificationReciever.class);
        intent.putExtra("ui", new Intent(context, ReminderActivity.class));
        intent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
