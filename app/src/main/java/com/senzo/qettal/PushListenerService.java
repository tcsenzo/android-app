package com.senzo.qettal;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.gcm.GcmListenerService;

import java.io.IOException;
import java.util.List;

public class PushListenerService extends GcmListenerService {

    private static final String LOG_TAG = PushListenerService.class.getSimpleName();

    public static final String ACTION_SNS_NOTIFICATION = "sns-notification";
    public static final String INTENT_SNS_NOTIFICATION_FROM = "from";
    public static final String INTENT_SNS_NOTIFICATION_DATA = "data";

    @Override
    public void onMessageReceived(final String from, final Bundle bundle) {
        Log.d(LOG_TAG, "Received a push notification with data: " + bundle + " from: "+ from);
        try {
            String defaultData = bundle.getString("default");
            if (isForeground(this)) {
                broadcast(from, defaultData);
            } else {
                PushNotificationData data = new ObjectMapper().readValue(defaultData, PushNotificationData.class);
                displayNotification(data);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Couldn't display push notification", e);
        }
    }

    private static boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();

        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : tasks) {
            if (ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND == appProcess.importance
                && packageName.equals(appProcess.processName)) {
                return true;
            }
        }
        return false;
    }

    private void displayNotification(final PushNotificationData data) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        int requestID = (int) System.currentTimeMillis();


        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(
                R.mipmap.push)
                .setContentTitle(data.getTitle())
                .setContentText(data.getMessage())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }

    private void broadcast(final String from, final String data) {
        Intent intent = new Intent(ACTION_SNS_NOTIFICATION);
        intent.putExtra(INTENT_SNS_NOTIFICATION_FROM, from);
        intent.putExtra(INTENT_SNS_NOTIFICATION_DATA, data);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
