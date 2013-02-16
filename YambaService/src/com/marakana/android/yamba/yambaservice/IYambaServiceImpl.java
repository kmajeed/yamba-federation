package com.marakana.android.yamba.yambaservice;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.Log;

import com.marakana.android.yamba.yambalib.IYambaService;
import com.marakana.android.yamba.yambalib.YambaStatus;
import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class IYambaServiceImpl extends IYambaService.Stub {
	private static final String TAG = "YambaService";
	private Context context;

	public IYambaServiceImpl(Context context) {
		this.context = context;
		YambaLib.log("IYambaServiceImpl created");
	}

	@Override
	public boolean updateStatus(String status) throws RemoteException {

		if (context.checkCallingOrSelfPermission("com.marakana.android.yamba.permission.YAMBA_SERVICE_UPDATE") 
				!= PackageManager.PERMISSION_GRANTED) {
			Log.e(TAG, "updateStatus security exception");
			throw new SecurityException("Not allowed to update yamba status!");
		}

		try {
			YambaClient yamba = new YambaClient("student", "password");
			yamba.postStatus(status);
			return true;
		} catch (YambaClientException e) {
			Log.e(TAG, "Failed to post", e);
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<YambaStatus> getTimeline(int records) throws RemoteException {
		List<YambaStatus> ret = new ArrayList<YambaStatus>();
		try {
			YambaClient yamba = new YambaClient("student", "password");
			List<Status> timeline = yamba.getTimeline(records);
			for (Status status : timeline) {
				ret.add(new YambaStatus((int) status.getId(), status.getUser(),
						status.getMessage(), status.getCreatedAt().getTime()));
			}
		} catch (YambaClientException e) {
			Log.e(TAG, "Failed to post", e);
			e.printStackTrace();
		}

		YambaLib.log("got records:" + ret.size());

		return ret;
	}

}
