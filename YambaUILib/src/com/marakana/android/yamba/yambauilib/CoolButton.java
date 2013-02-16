package com.marakana.android.yamba.yambauilib;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

public class CoolButton extends Button {

	public CoolButton(Context context) {
		super(context);
	}

	public CoolButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CoolButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		Paint paint = new Paint();
//		paint.setColor(Color.RED);
//		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
//		paint.setColor(Color.DKGRAY);
//		paint.setTextSize(15);
//		canvas.drawText(getText().toString(), 0, 25, paint);
	}

	
}
