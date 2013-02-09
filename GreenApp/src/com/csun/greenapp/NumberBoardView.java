package com.csun.greenapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class NumberBoardView extends View {
	private static final int SPACING = 65;
	private static final int ROW = 10;
	private static final int COL = 5;
	private static final int TEXT_SIZE = 40;
	private static final Paint painter;
	static {
		painter = new Paint();
		// painter.setStyle(Style.FILL);
		painter.setColor(Color.RED);
		painter.setTextSize(TEXT_SIZE);
		painter.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
	}
	
	private List<Integer> numbers;
	
	
	public NumberBoardView(Context context) {
		super(context);
		init(context);
	}
	
	public NumberBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public NumberBoardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		numbers = new ArrayList<Integer>();
		for (int i = 0; i < 50; ++i) {
			numbers.add(i);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int index = 0;
		for (int y = SPACING; y <= ROW * SPACING; y += SPACING) {
			for (int x = SPACING; x <= COL * SPACING; x += SPACING) {
				canvas.drawText(numbers.get(index).toString(), x, y, painter);
				index++;
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				break;
				
			case MotionEvent.ACTION_UP:
				break;
				
			case MotionEvent.ACTION_CANCEL:
				break;
				
			case MotionEvent.ACTION_MOVE:
				break;
				
			case MotionEvent.ACTION_OUTSIDE:
				break;
		}
		
		// force redraw 
		invalidate();
		return true;
	}
}
