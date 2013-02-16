package com.marakana.android.yambadata;

import com.marakana.android.yamba.yambalib.StatusContract;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context context) {
		super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
	}

	/** Runs only once, when you do not have the database at all. */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("create table %s (%s int primary key, "
				+ "%s text, %s text, %s int)", StatusContract.TABLE,
				StatusContract.Columns.ID, StatusContract.Columns.USER,
				StatusContract.Columns.MESSAGE,
				StatusContract.Columns.CREATED_AT);
		Log.d("DbHelper", "onCreate sql: "+sql);
		db.execSQL(sql);
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Typically you do ALTER TABLE ...
		db.execSQL("drop table if exists " + StatusContract.TABLE);
		this.onCreate(db);
	}

}
