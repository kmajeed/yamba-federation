package com.marakana.android.yamba;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import com.marakana.android.yamba.R;
import com.marakana.android.yamba.yambalib.StatusContract;

public class TimelineFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {
	private static final String[] FROM = { StatusContract.Columns.USER,
			StatusContract.Columns.MESSAGE, StatusContract.Columns.CREATED_AT };
	private static final int[] TO = { R.id.text_user, R.id.text_message,
			R.id.text_created_at };
	private static final int LOADER_ID = 42;
	private SimpleCursorAdapter adapter;

	private static final ViewBinder VIEW_BINDER = new ViewBinder() {
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if (view.getId() != R.id.text_created_at)
				return false;
			
			// Custom binding
			long timestamp = cursor.getLong(columnIndex);
			CharSequence relTime = DateUtils.getRelativeTimeSpanString(timestamp);
			((TextView)view).setText(relTime);
			
			return true;
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setEmptyText("Loading...");

		adapter = new SimpleCursorAdapter(getActivity(), R.layout.row, null,
				FROM, TO, 0);
		adapter.setViewBinder(VIEW_BINDER);

		setListAdapter(adapter);

		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	// --- Loader Callbacks ---

	/** Executes on a non-UI thread. */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (id != LOADER_ID)
			return null;
		return new CursorLoader(getActivity(), StatusContract.CONTENT_URI,
				null, null, null, StatusContract.DEFAULT_SORT);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
}
