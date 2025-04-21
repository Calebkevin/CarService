package com.mock.carservice;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ServiceCompat;

import com.mock.carservice.net.ServiceCall;

public class CarSpeedService extends Service {
    ServiceCall mServiceCall = new ServiceCall();
    public CarSpeedService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @SuppressLint("ForegroundServiceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {

                            Log.e("Service", "Service is running...");
                            try {
                                int speedVale = mServiceCall.getSpeedValue("1000");
                                Thread.sleep(2000);
                                if(speedVale>50){
                                    mServiceCall.pushSpeedValue(speedVale,"1000");
                                    createNotificationChannel("Over Speed Limit Exceed");
                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            startForegroundService();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("ForegroundServiceType")
    public void startForegroundService(){
        final String CHANNELID = "Foreground Service ID";
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNELID, CHANNELID, NotificationManager.IMPORTANCE_LOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
        Notification.Builder notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, CHANNELID)
                    .setContentText("Service is running")
                    .setContentTitle("Service enabled")
                    .setSmallIcon(R.drawable.ic_launcher_background);
        }
        //startForeground(1001, notification.build());
        ServiceCompat.startForeground(this,1001,notification.build(), ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC);
    }
    private void createNotificationChannel(String desc) {
        final String CHANNELID = "Foreground Service ID";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNELID,
                    desc,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.enableLights(true); // Turn on notification light
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true); // Allow vibration for notifications

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }
}