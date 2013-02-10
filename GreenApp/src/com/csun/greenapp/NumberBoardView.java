package com.csun.greenapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csun.greenapp.types.GameState;
import com.csun.greenapp.utils.UiUtil;

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
	private static final int SPACING = 64;
	private static final int ROW = 10;
	private static final int COL = 5;
	private static final int TEXT_SIZE = 35;
	private static final Paint painter;
	private Context context;
	
	static {
		painter = new Paint();
		// painter.setStyle(Style.FILL);
		painter.setColor(Color.RED);
		painter.setTextSize(TEXT_SIZE);
		painter.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
	}
	
	private List<Integer> numbers;
	private Map<Integer, Integer> states;
	private Map<String, Integer> locations;
	
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
		this.context = context;
		states = new HashMap<Integer, Integer>();
		locations = new HashMap<String, Integer>();
		numbers = new ArrayList<Integer>();
		String key;
		int count = 1;
		for (int y = SPACING; y <= ROW * SPACING; y += SPACING) {
			for (int x = SPACING; x <= COL * SPACING; x += SPACING) {
				key = x + ":" + y;
				locations.put(key, count);
				numbers.add(count);
				count++;
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int index = 0;
		for (int y = SPACING; y <= ROW * SPACING; y += SPACING) {
			for (int x = SPACING; x <= COL * SPACING; x += SPACING) {
				painter.setColor(Color.BLACK);
				canvas.drawText(numbers.get(index).toString(), x, y, painter);
				if (states.containsKey(numbers.get(index))) {
					painter.setStyle(Style.FILL);
					int id = states.get(numbers.get(index));
					switch (id) {
						case 1:
							painter.setARGB(128, 255, 0, 0);
							break;
						case 2:
							painter.setARGB(128, 0, 255, 0);
							break;
						case 3:
							painter.setARGB(128, 0, 0, 255);
							break;
					}
					canvas.drawRect(x - 20, y - 40, x + 40, y + 30, painter);
				}
				index++;
			}
		}
	}
	
	private int estimateFingerPositionCoordinate(int coordinate, int offset) {
		if ((coordinate % offset) > offset/2) {
			return ((coordinate / offset) + 1) * offset;
		} else {
			return (coordinate / offset)  * offset;
		}
	}
	
	public int getNumberAt(MotionEvent event) {
		int x = estimateFingerPositionCoordinate((int) event.getX(), SPACING/2);
		int y = estimateFingerPositionCoordinate((int) event.getY(), SPACING/2);
		String key = x + ":" + y;
		if (locations.containsKey(key)) {
			return locations.get(key);
		}
		return 0;
	}

	public synchronized void addNewState(GameState state) {
		if (!states.containsKey(state.getNumber())) {
			states.put(state.getNumber(), state.getUserId());
		}
	}
	
}
