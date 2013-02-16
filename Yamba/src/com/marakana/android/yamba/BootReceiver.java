package com.marakana.android.yamba;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
 
public class BootReceiver extends BroadcastReceiver {
	private static final long INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

	@Override
	public void onReceive(Context context, Intent intent) {
		PendingIntent operation = PendingIntent.getService(context, 0,
				new Intent(context, RefreshService.class), PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC,
				System.currentTimeMillis(), INTERVAL, operation);

		Log.d("BootReceiver", "onReceived");
	}

}
