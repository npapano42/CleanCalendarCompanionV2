package todolist.jimmy.com.cleancalendarcompanionv2.Helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import todolist.jimmy.com.cleancalendarcompanionv2.Objects.Task;
import todolist.jimmy.com.cleancalendarcompanionv2.R;
import todolist.jimmy.com.cleancalendarcompanionv2.ReminderActivity;


public class NotificationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "You got notification", Toast.LENGTH_LONG).show();
        Task task = (Task)intent.getSerializableExtra("task");
        Intent uiIntent = new Intent(context, ReminderActivity.class);
        uiIntent.putExtra("task", task);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                uiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        String soundName = task.getTask_notification_sound();
        Uri soundUri = Uri.parse("android.resource://"+context.getPackageName()+"/raw/"+"buzz");
        switch (soundName){
            case "Buzz" :
                soundUri = Uri.parse("android.resource://"+context.getPackageName()+"/raw/"+"buzz");
                break;
            case "Gets in the way" :
                soundUri = Uri.parse("android.resource://"+context.getPackageName()+"/raw/"+"gets_in_the_way");
                break;
            case "Jingle Bells" :
                soundUri = Uri.parse("android.resource://"+context.getPackageName()+"/raw/"+"jingle_bells");
                break;
            case "Rooster" :
                soundUri = Uri.parse("android.resource://"+context.getPackageName()+"/raw/"+"rooster");
                break;
            case "Siren" :
                soundUri = Uri.parse("android.resource://"+context.getPackageName()+"/raw/"+"siren");
                break;
            case "System Fault" :
                soundUri = Uri.parse("android.resource://"+context.getPackageName()+"/raw/"+"system_fault");
                break;
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                .setContentTitle(task.getTask_name())
                .setWhen(System.currentTimeMillis())
                .setContentText(task.getTask_name() + ": " + task.getTask_description()+
                        "\nLocation: "+task.getTask_location())
                .setContentIntent(pendingIntent)
                .setTicker("You have task to be done!")
                .setSound(soundUri);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
}
