package com.joshua_lsj.goldenage.Notification;

/**
 * Created by limsh on 12/5/2017.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.joshua_lsj.goldenage.Experiment.MainActivity;
import com.joshua_lsj.goldenage.Other.DownloadData;

public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        int id = intent.getIntExtra(NotificationScheduler.NOTIFICATION_ID, 0);
        String description = intent.getStringExtra(NotificationScheduler.NOTIFICATION_DESCRIPTION);
        //Trigger the notification
        NotificationScheduler.showNotification(context, MainActivity.class, id,
                "You have 5 unwatched videos", description);

        DownloadData downloadData = new DownloadData(context);
        downloadData.downloadForAdmin();
    }
}
