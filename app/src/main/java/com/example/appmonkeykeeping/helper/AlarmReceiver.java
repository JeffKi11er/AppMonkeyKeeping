package com.example.appmonkeykeeping.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.appmonkeykeeping.annotation.AnnotationCode;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = NotificationHelper.getInstance();
        notificationHelper.sendOnChannel(context,"Period Alarm", AnnotationCode.CHANNEL_ALARM);
    }
}
