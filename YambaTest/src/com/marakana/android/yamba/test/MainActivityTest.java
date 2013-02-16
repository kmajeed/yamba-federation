package com.marakana.android.yamba.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.marakana.android.yamba.MainActivity;
import com.marakana.android.yamba.R;
import com.marakana.android.yamba.TimelineFragment;

/**
 * Run on command line using: adb shell am instrument -w
 * com.marakana.android.yamba.test/android.test.InstrumentationTestRunner
 */
public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity activity;
	private TimelineFragment fragment;
	private ListView timeline;
	private Menu menu;
	private MenuItem itemRefresh;
	private MenuItem itemPurge;

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		fragment = (TimelineFragment) activity.getFragmentManager()
				.findFragmentById(R.id.fragment_timeline);
		timeline = fragment.getListView();
		activity.getMenuInflater().inflate(R.menu.activity_main, menu);
		itemRefresh = menu.findItem(R.id.item_refresh);
		itemPurge = menu.findItem(R.id.item_purge);
	}

	@UiThreadTest
	public void testPurge() {
		// TODO
	}

	public void testRefresh() {
		// TODO
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

}
