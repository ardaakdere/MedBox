package com.example.mymedicines;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;



public class NotifyWorker extends Worker {
    Context mContext;
    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Log.i("HATIRLATICI1", "hatırlatıcı1");
            SharedPreferences sp = mContext.getSharedPreferences("username", Context.MODE_PRIVATE);
            String username2 = sp.getString("username", "");
            if(username2.equals(""))
            {
                displayNotification("MedicineBox",
                        "İlaçları kontrol etmeyi unutma.");
            }
            else
            {
                displayNotification("MedicineBox",
                        "Merhaba "+username2+" ilaçlarını kontrol etmeyi unutma");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return Result.success();
    }

    private void displayNotification(String title, String task) {
        NotificationCompat.Builder notification;
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent gidilecekIntent = PendingIntent.getActivity(getApplicationContext(),1,new Intent(),PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("medicinebox", "medicinebox", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);

            notification = new NotificationCompat.Builder(getApplicationContext(), "medicinebox")
                    .setContentTitle(title)
                    .setContentText(task)
                    .setSmallIcon(R.drawable.transparent_logo)
                    .setContentIntent(gidilecekIntent)
                    .setAutoCancel(true);
        }
        else
        {
            notification = new NotificationCompat.Builder(getApplicationContext());
            notification.setContentTitle(title);
            notification.setContentText(task);
            notification.setSmallIcon(R.drawable.transparent_logo);
            notification.setAutoCancel(true);
            notification.setContentIntent(gidilecekIntent);
            notification.setPriority(Notification.PRIORITY_HIGH);
        }


        notificationManager.notify(1, notification.build());
    }
}
