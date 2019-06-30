package com.genv3.gendelivery.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.genv3.gendelivery.View.Activtys.BaseView;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.View.Fragments.PedidosFragmen;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
public class NotificationService extends FirebaseMessagingService {
    private static final String TAG = "MiserviocioFirebase";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "From " + remoteMessage.getFrom());
            Log.e(TAG, "Message Notificaiton body: " + remoteMessage.getNotification().getBody());
            sendnotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
            update();
        }

    }

    private void update() {
        Intent i = new Intent(PedidosFragmen.UPDATE);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }

    public void sendnotification(String title, String Body) {
        Intent intent = new Intent(this, BaseView.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //
        String NOTIFICATION_CHANNEL_ID = getString(R.string.default_notification_channel_id);


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder
                .setSmallIcon(R.drawable.ic_twotone_moto_pink)
                .setColor(getResources().getColor(R.color.ColorFarmanor))
                .setContentTitle(title)
                .setContentText(Body)
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

                .setContentInfo("info");

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Descripcion");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
