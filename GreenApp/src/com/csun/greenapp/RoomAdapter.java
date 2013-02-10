package com.csun.greenapp;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RoomAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<Room> rooms;
	private AtomicBoolean keepOnAppending = new AtomicBoolean(true);
	
	static class ViewHolder {
		TextView id;
		TextView count;
	}
	
	public RoomAdapter(Context context, List<Room> rooms) {
		this.context = context;
		this.rooms = rooms;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return rooms.size();
	}

	public Object getItem(int index) {
		return rooms.get(index);
	}
	
	public long getItemId(int position) {
		return 0;
	}
	
	public View getView(int index, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_room, parent, false);
			holder = new ViewHolder();
			holder.id = (TextView) convertView.findViewById(R.id.item_room_XML_textview_room_number);
			holder.count = (TextView) convertView.findViewById(R.id.item_room_XML_textview_capacity);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.id.setText("Room #" + Integer.toString(rooms.get(index).getId()));
		holder.count.setText(Integer.toString(rooms.get(index).getCount()) + "/4");
		return convertView;
	} 
}
