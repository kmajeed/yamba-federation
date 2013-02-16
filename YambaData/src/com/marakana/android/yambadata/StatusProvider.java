package com.marakana.android.yambadata;

import com.marakana.android.yamba.yambalib.StatusContract;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class StatusProvider extends ContentProvider {
	private static final String TAG = "StatusProvider";
	private DbHelper dbHelper;

	private static final UriMatcher matcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		matcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE,
				StatusContract.CONTENT_TYPE_DIR);
		matcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE + "/#",
				StatusContract.CONTENT_TYPE_ITEM);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext());
		return (dbHelper == null) ? false : true;
	}

	@Override
	public String getType(Uri uri) {
		switch (matcher.match(uri)) {
		case StatusContract.CONTENT_TYPE_DIR:
			return "vnd.android.cursor.dir/vnd.com.marakana.android.yamba.status";
		case StatusContract.CONTENT_TYPE_ITEM:
			return "vnd.android.cursor.item/vnd.com.marakana.android.yamba.status";
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// Assert
		if (matcher.match(uri) != StatusContract.CONTENT_TYPE_DIR)
			return null;

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = db.insertWithOnConflict(StatusContract.TABLE, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);

		if (id == -1) {
			return null;
		}

		getContext().getContentResolver().notifyChange(uri, null);

		Uri ret = ContentUris.withAppendedId(uri,
				values.getAsLong(StatusContract.Columns.ID));
		Log.d(TAG, "insert uri: " + ret);
		return ret;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	// uri: content://com.marakana.android.android.yamba.provider.timeline/status/414
	// delete from status where id=414 and selection=selectionArgs
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String where;

		if (selection == null)
			selection = "";

		switch (matcher.match(uri)) {
		case StatusContract.CONTENT_TYPE_DIR:
			where = "";
			break;
		case StatusContract.CONTENT_TYPE_ITEM:
			long id = ContentUris.parseId(uri);
			where = String.format(" and %s=%d", StatusContract.Columns.ID, id);
			break;
		default:
			throw new IllegalArgumentException("Invalid uri: " + uri);
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int recs = db.delete(StatusContract.TABLE, selection + where,
				selectionArgs);

		if (recs > 0)
			getContext().getContentResolver().notifyChange(uri, null);

		Log.d(TAG, "deleted recs: " + recs);
		return recs;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String where = (selection == null) ? "" : selection;

		switch (matcher.match(uri)) {
		case StatusContract.CONTENT_TYPE_DIR:
			break;
		case StatusContract.CONTENT_TYPE_ITEM:
			long id = ContentUris.parseId(uri);
			where = String.format("%s and %s=%d", where,
					StatusContract.Columns.ID, id);
			break;
		default:
			throw new IllegalArgumentException("Invalid uri: " + uri);
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(StatusContract.TABLE, projection, where,
				selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

}
