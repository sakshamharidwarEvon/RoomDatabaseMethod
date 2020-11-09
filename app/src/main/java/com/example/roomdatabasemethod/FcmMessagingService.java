package com.example.roomdatabasemethod;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FcmMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("Title",remoteMessage.getNotification().getTitle());
        if (remoteMessage.getData().size()>0){
            String title = remoteMessage.getData().get("title");
            String message=remoteMessage.getData().get("message");
            Intent intent=new Intent("com.example.roomdatabasemethod_FCM-MESSAGE");
            String a=intent.putExtra("title",title).toString();
            String b=intent.putExtra("message",message).toString();
            Log.d("ni",a);
            Log.d("nu",b);
            LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(intent);
        }
//        super.onMessageReceived(remoteMessage);
//        String title=remoteMessage.getNotification().getTitle();
//        String message=remoteMessage.getNotification().getBody();
//
//        Intent intent = new Intent(this,MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//        NotificationCompat.Builder notificationbuilder=new NotificationCompat.Builder(this);
//        notificationbuilder.setContentTitle(title);
//        notificationbuilder.setContentText(message);
//        notificationbuilder.setAutoCancel(true);
//        notificationbuilder.setSmallIcon(R.mipmap.ic_launcher);
//        notificationbuilder.setContentIntent(pendingIntent);
//        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0,notificationbuilder.build());
    }
}

