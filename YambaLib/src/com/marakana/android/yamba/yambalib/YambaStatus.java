package com.marakana.android.yamba.yambalib;

import android.os.Parcel;
import android.os.Parcelable;

public class YambaStatus implements Parcelable {
	private int id;
	private String user, text;
	private long timestamp;

	public YambaStatus(int id, String user, String text, long timestamp) {
		super();
		this.id = id;
		this.user = user;
		this.text = text;
		this.timestamp = timestamp;
	}

	public static final Parcelable.Creator<YambaStatus> CREATOR = new Parcelable.Creator<YambaStatus>() {

		@Override
		public YambaStatus createFromParcel(Parcel source) {
			return new YambaStatus(source.readInt(), source.readString(),
					source.readString(), source.readLong());
		}

		@Override
		public YambaStatus[] newArray(int size) {
			return new YambaStatus[size];
		}

	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(user);
		dest.writeString(text);
		dest.writeLong(timestamp);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
