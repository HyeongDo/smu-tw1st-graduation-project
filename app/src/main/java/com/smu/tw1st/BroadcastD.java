package com.smu.tw1st;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class BroadcastD extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고


         PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
         Notification.Builder builder = new Notification.Builder(context,"default");

        builder.setSmallIcon(R.mipmap.tw1sticon);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("여행은 신나! TW1ST");
        builder.setContentText("최근 검색하신 경로의 가격이 떨어졌습니다.");
        builder.setContentIntent(pendingIntent);

        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(
                    new NotificationChannel("default","기본채널",NotificationManager.IMPORTANCE_DEFAULT));
        }
        notificationManager.notify(1,builder.build());

    }
}