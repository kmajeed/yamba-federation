package com.marakana.android.yamba.yambalib;

import com.marakana.android.yamba.yambalib.YambaStatus;

interface IYambaService {
	boolean updateStatus( in String status );
	List<YambaStatus> getTimeline(int records);
}