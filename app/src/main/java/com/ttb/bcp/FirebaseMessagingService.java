package com.ttb.bcp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by filipp on 5/23/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    String notificationId=null,content=null,servicesId=null,carpool=null,normal=null;
    RemoteMessage remoteMessage;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        content = remoteMessage.getData().get("content");
        notificationId =  remoteMessage.getData().get("notificationId");
        servicesId =  remoteMessage.getData().get("servicesId");
        carpool = remoteMessage.getData().get("carpool");
        normal = remoteMessage.getData().get("normal");
        this.remoteMessage = remoteMessage;

        Log.e("Remote","Notification Delivered");

        if (servicesId!=null){
            //sendNotification();
            sendFeedback();
        }else if(notificationId!=null){
            //sendFeedback();
            sendNotification();
        }else if(carpool!=null){
            //sendFeedback();
            sendCarpoolNotification();
        }else if(normal!=null){
            //sendFeedback();
            sendNormalNotification();
        }

    }

    void sendNormalNotification(){
        Random random = new Random();
        int notificationID = random.nextInt(5000);
        String title = "ALERT (BCP)", message = content;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.bahrialogo);
        notificationBuilder.setSound(uri);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationID, notificationBuilder.build());
    }

    void sendCarpoolNotification(){
        Random random = new Random();
        int notificationID = random.nextInt(5000);
        String title = "CARPOOL REQUEST (BCP)", message = content;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.drawable.bahrialogo);
        notificationBuilder.setSound(uri);
        //notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.setWhen(0);


        Intent yesReceive = new Intent();
        yesReceive.setAction("com.ttb.bcp.YES");
        yesReceive.putExtra("id",notificationID+"");
        yesReceive.putExtra("carpoolid",remoteMessage.getData().get("carpoolid")+"");
        PendingIntent pendingIntentSure = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent noReceive = new Intent();
        noReceive.setAction("com.ttb.bcp.NO");
        noReceive.putExtra("id",notificationID+"");
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(this, 12345, noReceive, PendingIntent.FLAG_UPDATE_CURRENT);


        notificationBuilder.addAction(R.drawable.ratestar,"Sure",pendingIntentSure);
        notificationBuilder.addAction(R.drawable.ratestar,"Decline",pendingIntentCancel);


        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationID, notificationBuilder.build());
    }

    void sendNotification(){
        Random random = new Random();
        int notificationID = random.nextInt(5000);
        String title = "BCP", message = content;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent var = new Intent(this, description.class);
        var.putExtra("notificationId", notificationId);
        var.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        var.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationID, var, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setOngoing(true);
        notificationBuilder.setSmallIcon(R.drawable.bahrialogo);
        notificationBuilder.setSound(uri);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationID, notificationBuilder.build());
    }

    void sendFeedback(){
        Random random = new Random();
        int notificationID = random.nextInt(5000);
        String title = "BCP", message = content;
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent var = new Intent(this, feedbackservice.class);
        var.putExtra("servicesId", servicesId);
        var.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        var.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationID, var, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setOngoing(true);
        notificationBuilder.setSmallIcon(R.drawable.bahrialogo);
        notificationBuilder.setSound(uri);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notificationID, notificationBuilder.build());
    }


}


