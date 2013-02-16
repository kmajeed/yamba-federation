package com.marakana.android.yamba;


import com.marakana.android.yamba.R;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StatusFragment extends Fragment implements TextWatcher,
		OnClickListener {

	private static final int MAX_COUNT = 140;
	private Button buttonUpdate;
	private EditText editStatus;
	private TextView textCount;
	private int defaultTextColor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.fragment_status, container, false);

		buttonUpdate = (Button) view.findViewById(R.id.button_update);
		buttonUpdate.setOnClickListener(this);
		textCount = (TextView) view.findViewById(R.id.text_count);
		textCount.setText(String.valueOf(MAX_COUNT));
		defaultTextColor = textCount.getTextColors().getDefaultColor();

		editStatus = (EditText) view.findViewById(R.id.edit_status);
		editStatus.addTextChangedListener(this);

		return view;
	}
	
	@Override
	public void onClick(View v) {
		String status = editStatus.getText().toString();
		new PostStatusTask().execute(status);
	}

	class PostStatusTask extends AsyncTask<String, Void, String> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(getActivity(), null,
					"Posting...please wait");
		}

		/** Executes on a separate worker thread. */
		@Override
		protected String doInBackground(String... params) {			
			YambaApp yambaApp = (YambaApp)(getActivity().getApplication());
			boolean hasPosted = yambaApp.yambaManager.updateStatus(params[0]);
			if(hasPosted) {
				return "Successfully posted";
			} else {
				return "Failed to post";				
			}
		}

		/** Executes on UI thread after doInBackground() is done. */
		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

			getActivity().startService(
					new Intent(getActivity(), RefreshService.class));
		}

	}

	// --- TextWatcher Callbacks ---
	@Override
	public void afterTextChanged(Editable s) {
		int count = 140 - s.length();
		textCount.setText(String.valueOf(count));

		if (count < 50) {
			textCount.setTextColor(Color.RED);
		} else {
			textCount.setTextColor(defaultTextColor);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

}
