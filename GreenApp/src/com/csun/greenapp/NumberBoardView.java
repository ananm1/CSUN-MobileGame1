package com.csun.greenapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.csun.greenapp.types.GameState;
import com.csun.greenapp.utils.UiUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

public class NumberBoardView extends SurfaceView implements Callback {
	private static final int SPACING = 80;
	private static final int ROW = 6;
	private static final int COL = 5;
	private static final int N = ROW * COL;
	private static final int TEXT_SIZE = 35;
	private static final Paint painter;
	private Context context;
	private SurfaceHolder surfaceHolder;
	private DrawingThread drawingThread;

	static {
		painter = new Paint();
		// painter.setStyle(Style.FILL);
		painter.setColor(Color.RED);
		painter.setTextSize(TEXT_SIZE);
		painter.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
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

	private int[] getRandomNumberList() {
		int[] numbers = new int[N];
		for (int i = 0; i < N; ++i) {
			numbers[i] = i + 1;
		}

		Random gen = new Random();
		for (int i = 0; i < N; ++i) {
			int j = i + gen.nextInt(N - i);
			// swap
			int temp = numbers[i];
			numbers[i] = numbers[j];
			numbers[j] = temp;
		}
		return numbers;
	}

	private void init(Context context) {
		this.context = context;
		states = new HashMap<Integer, Integer>();
		locations = new HashMap<String, Integer>();
		numbers = new ArrayList<Integer>();
		int[] values = getRandomNumberList();
		String key;
		int idx = 0;
		for (int y = SPACING; y <= ROW * SPACING; y += SPACING) {
			for (int x = SPACING; x <= COL * SPACING; x += SPACING) {
				key = x + ":" + y;
				locations.put(key, values[idx]);
				numbers.add(values[idx]);
				idx++;
			}
		}

		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// empty
	}

	public void surfaceCreated(SurfaceHolder holder) {
		drawingThread = new DrawingThread(holder, this);
		drawingThread.setFlag(true);
		drawingThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		drawingThread.setFlag(false);
	}

	public synchronized int getStateSize() {
		return states.size();
	}

	public synchronized int getUserScore(int id) {
		int scores = 1;
		for (Map.Entry<Integer, Integer> item : states.entrySet()) {
			Integer userId = item.getValue();
			if (userId.equals(id)) {
				scores++;
			}
		}
		return scores;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (canvas != null) {
			canvas.drawColor(Color.WHITE);
			int index = 0;
			for (int y = SPACING; y <= ROW * SPACING; y += SPACING) {
				for (int x = SPACING; x <= COL * SPACING; x += SPACING) {

					// draw text
					painter.setColor(Color.BLACK);
					canvas.drawText(numbers.get(index).toString(), x, y,
							painter);

					// draw board
					painter.setStyle(Style.STROKE);
					painter.setColor(Color.parseColor("#088da5"));
					canvas.drawRect(x - 20, y - 40, x + 50, y + 40, painter);

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
						case 4:
							painter.setARGB(128, 255, 0, 255);
							break;
						}
						canvas.drawRect(x - 20, y - 40, x + 50, y + 40, painter);
					}
					index++;
				}
			}
		}
	}

	private int estimateFingerPositionCoordinate(int coordinate, int offset) {
		if ((coordinate % offset) > offset / 2) {
			return ((coordinate / offset) + 1) * offset;
		} else {
			return (coordinate / offset) * offset;
		}
	}

	public int getNumberAt(MotionEvent event) {
		int x = estimateFingerPositionCoordinate((int) event.getX(),
				SPACING / 2);
		int y = estimateFingerPositionCoordinate((int) event.getY(),
				SPACING / 2);
		String key = x + ":" + y;
		if (locations.containsKey(key)) {
			return locations.get(key);
		}
		return 0;
	}

	public synchronized boolean haveAllNumbersCrossed() {
		return (states.size() == N);
	}

	public synchronized void addNewState(GameState state) {
		if (!states.containsKey(state.getNumber())) {
			states.put(state.getNumber(), state.getUserId());
		}
	}

	class DrawingThread extends Thread {
		private volatile boolean flag;
		private SurfaceHolder myHolder;
		private NumberBoardView board;

		public DrawingThread(SurfaceHolder holder, NumberBoardView board) {
			myHolder = holder;
			this.board = board;
		}

		public synchronized void setFlag(boolean myFlag) {
			flag = myFlag;
		}

		public void run() {
			Canvas canvas = null;
			while (flag) {
				try {
					canvas = myHolder.lockCanvas(null);
					board.onDraw(canvas);
				} finally {
					if (canvas != null) {
						myHolder.unlockCanvasAndPost(canvas);
					}
				}
			}
		}
	}
}
