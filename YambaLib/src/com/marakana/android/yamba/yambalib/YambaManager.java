package com.marakana.android.yamba.yambalib;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class YambaManager {
	private static final Intent YAMBA_SERVICE_INTENT = new Intent(
			"com.marakana.android.yamba.yambalib.IYambaService");
	private Context context;
	private IYambaService yambaService;

	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			yambaService = IYambaService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			yambaService = null;
		}
	};

	public YambaManager(Context context) {
		this.context = context;
		this.context.bindService(YAMBA_SERVICE_INTENT, connection,
				Context.BIND_AUTO_CREATE);
	}
	
	@Override
	public void finalize() {
		this.context.unbindService(connection);
	}

	// --- Proxy Calls ---

	public boolean updateStatus(String status) {
		if (yambaService == null) {
			Log.e("Yamba", "yambaService not bound");
			return false;
		}
		if (status == null) {
			throw new IllegalArgumentException("Status cannot be null");
		}
		try {
			yambaService.updateStatus(status);
			return true;
		} catch (RemoteException e) {
			Log.e("Yamba", "Failed to post", e);
			e.printStackTrace();
			return false;
		}
	}

	public List<YambaStatus> getTimeline(int records) {
		if (yambaService == null) {
			Log.e("Yamba", "yambaService not bound");
			return null;
		}

		try {
			return yambaService.getTimeline(records);
		} catch (RemoteException e) {
			Log.e("Yamba", "Failed to get timeline", e);
			e.printStackTrace();
			return null;
		}
	}

}
