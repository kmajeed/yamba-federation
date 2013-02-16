package com.marakana.android.yamba;

import java.util.List;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.marakana.android.yamba.yambalib.StatusContract;
import com.marakana.android.yamba.yambalib.YambaStatus;

public class RefreshService extends IntentService {
	private static final String TAG = "RefreshService";

	public RefreshService() {
		super(TAG);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	/** Executes on the worker thread. */
	@Override
	public void onHandleIntent(Intent intent) {
		Log.d(TAG, "onStarted intent"+intent);
		int counter = 0;

		ContentValues values = new ContentValues();
		List<YambaStatus> timeline = ((YambaApp) getApplication()).yambaManager
				.getTimeline(20);
		if (timeline == null) {
			Log.d(TAG, "no timeline");
			return;
		}
		for (YambaStatus status : timeline) {

			values.clear();
			values.put(StatusContract.Columns.ID, status.getId());
			values.put(StatusContract.Columns.USER, status.getUser());
			values.put(StatusContract.Columns.MESSAGE, status.getText());
			values.put(StatusContract.Columns.CREATED_AT, status.getTimestamp());

			if (getContentResolver().insert(StatusContract.CONTENT_URI, values) != null) {
				counter++;
			}

			Log.d(TAG,
					String.format("%s: %s", status.getUser(), status.getText()));
		}

		if (counter > 0) {
			sendBroadcast(new Intent("com.marakana.android.yamba.action.NEW_STATUS")
					.putExtra("count", counter));
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
