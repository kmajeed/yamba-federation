package com.marakana.android.yamba;

import com.marakana.android.yamba.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
	public static final int NOTIFICATION_ID = 42;

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		Notification notification;

		PendingIntent operation = PendingIntent.getActivity(context, 0,
				new Intent(context, MainActivity.class),
				PendingIntent.FLAG_UPDATE_CURRENT);

		if (Build.VERSION.SDK_INT > 16) {
			notification = new Notification.Builder(context)
					.setContentTitle("New status!")
					.setContentIntent(operation)
					.setAutoCancel(true)
					.setContentText(
							String.format("You got %d new statuses",
									intent.getIntExtra("count", 0)))
					.setSmallIcon(R.drawable.ic_launcher).build();
		} else {
			notification = new Notification.Builder(context)
					.setContentTitle("New status!")
					.setContentIntent(operation)
					.setAutoCancel(true)
					.setContentText(
							String.format("You got %d new statuses",
									intent.getIntExtra("count", 0)))
					.setSmallIcon(R.drawable.ic_launcher).getNotification();
		}

		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(NOTIFICATION_ID, notification);

		Log.d("NotificationReceiver", "onRecieved intent: " + intent);
	}
}
