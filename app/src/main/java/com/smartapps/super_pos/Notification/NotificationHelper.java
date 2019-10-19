package com.smartapps.super_pos.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.smartapps.super_pos.MainActivity;
import com.smartapps.super_pos.R;

public class NotificationHelper {

    public static void displayNotification(Context context, String title, String body){
        Uri uri=Uri.parse("android.resource://com.smartapps.super_pos/raw/notification1");

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, "Default")
                        .setSmallIcon(R.drawable.ic_location)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSound(uri)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);


       /* NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);*/

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notificationBuilder.build());
    }
}
