package com.csun.greenapp;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.csun.greenapp.RoomAdapter.ViewHolder;
import com.csun.greenapp.types.LobbyItem;

public class LobbyAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<LobbyItem> items;
	
	static class ViewHolder {
		TextView username;
		CheckBox ready;
	}
	
	public LobbyAdapter(Context context, List<LobbyItem> items) {
		this.context = context;
		this.items = items;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int index) {
		return items.get(index);
	}
	
	public long getItemId(int position) {
		return 0;
	}
	
	public View getView(int index, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_lobby, parent, false);
			holder = new ViewHolder();
			holder.username = (TextView) convertView.findViewById(R.id.item_lobby_XML_textview_username);
			holder.ready = (CheckBox) convertView.findViewById(R.id.item_lobby_XML_checkbox_ready);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.username.setText(" " + items.get(index).getUsername());
		holder.ready.setChecked(items.get(index).isReady());
		return convertView;
	} 
}