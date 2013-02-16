package com.marakana.android.yamba.test;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.marakana.android.yamba.R;
import com.marakana.android.yamba.StatusActivity;
import com.marakana.android.yamba.StatusFragment;

/**
 * Run on command line using:
 * adb shell am instrument -w com.marakana.android.yamba.test/android.test.InstrumentationTestRunner
 */
public class StatusActivityTest extends
		ActivityInstrumentationTestCase2<StatusActivity> {
	private StatusActivity activity;
	private StatusFragment fragment;
	private EditText editStatus;
	private TextView textCount;
	private Button buttonUpdate;

	public StatusActivityTest() {
		super(StatusActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
		fragment = (StatusFragment) activity.getFragmentManager()
				.findFragmentById(R.id.fragment_status);
		editStatus = (EditText) fragment.getView().findViewById(
				R.id.edit_status);
		textCount = (TextView) fragment.getView().findViewById(R.id.text_count);
		buttonUpdate = (Button) fragment.getView().findViewById(
				R.id.button_update);
	}

	// --- Tests go here ---

	@SmallTest
	public void testUIExists() {
		Assert.assertNotNull(activity);
		Assert.assertNotNull(fragment);
		Assert.assertNotNull(editStatus);
		Assert.assertNotNull(textCount);
		Assert.assertNotNull(buttonUpdate);
	}

	@SmallTest
	public void testUiVisible() {
		View rootView = activity.getCurrentFocus().getRootView();
		ViewAsserts.assertOnScreen(rootView, editStatus);
		ViewAsserts.assertOnScreen(rootView, textCount);
		ViewAsserts.assertOnScreen(rootView, buttonUpdate);
	}

	@SmallTest
	public void testInitialTextCounter() {
		Assert.assertEquals("140", textCount.getText().toString());
	}

	@SmallTest
	public void testTextCounterDecreases() {
		TouchUtils.tapView(this, editStatus);
		sendKeys(KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_2, KeyEvent.KEYCODE_3);
		Assert.assertEquals("137", textCount.getText().toString());
	}

	@UiThreadTest
	public void testSetText() {
		editStatus.setText("Some text goes here");
		Assert.assertEquals("Some text goes here", editStatus.getText()
				.toString());
	}

	@SmallTest
	public void testText() {
		TouchUtils.tapView(this, editStatus);
		sendKeys(KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_2, KeyEvent.KEYCODE_3);
		Assert.assertEquals("123", editStatus.getText().toString());
	}

	@SmallTest
	public void testTextCounterIncreases() {
		TouchUtils.tapView(this, editStatus);
		sendKeys(KeyEvent.KEYCODE_A, KeyEvent.KEYCODE_B, KeyEvent.KEYCODE_C);
		sendKeys(KeyEvent.KEYCODE_DEL, KeyEvent.KEYCODE_DEL);
		Assert.assertEquals("139", textCount.getText().toString());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
