package com.joshua_lsj.goldenage.Notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.joshua_lsj.goldenage.Experiment.MainActivity;
import com.joshua_lsj.goldenage.Objects.Calender;
import com.joshua_lsj.goldenage.Objects.Schedule;
import com.joshua_lsj.goldenage.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by limsh on 12/5/2017.
 */

public class NotificationScheduler {
    public static final int REMINDER_CODE = 0;
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String NOTIFICATION_DESCRIPTION = "notification_description";


    public static void setReminder(Context context, Class<?> cls, Schedule schedule){
        String dateTimeString = schedule.getDate() + " " + schedule.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            Date dateTime = simpleDateFormat.parse(dateTimeString);


    //        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
    //        Calendar calender = Calendar.getInstance(timeZone); //TODO: put +8:00
            Calendar calender = Calendar.getInstance();
            calender.setTime(dateTime);

            String bla = simpleDateFormat.format(calender.getTime());

        //    Toast.makeText(context, bla, Toast.LENGTH_SHORT).show();

            Calendar nowC = Calendar.getInstance();

            if(nowC.before(calender)) {


                Toast.makeText(context, bla, Toast.LENGTH_SHORT).show();


                String description = schedule.getDescription();

/*
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);

int hour = c.get(


calender.set(Calender.YEAR, year);
*/
                int id = Integer.parseInt(schedule.getID());

                Random random = new Random();
                int randomNumber = random.nextInt(500) + 20;

                ComponentName receiver = new ComponentName(context, cls);
                PackageManager pm = context.getPackageManager();

                pm.setComponentEnabledSetting(receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);


                Intent intent = new Intent(context, cls);
                intent.putExtra(NOTIFICATION_ID, id);
                intent.putExtra(NOTIFICATION_DESCRIPTION, description);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, FLAG_ACTIVITY_NEW_TASK);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calender.getTimeInMillis(), pendingIntent);

            }

        }catch(ParseException ex){
            ex.printStackTrace();
//ERROR EXCEPTION MESSAGE
            return;
        }
    }

    public static void showNotification(Context context,Class<?> cls, int id, String title,String content){
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(REMINDER_CODE, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }
}
