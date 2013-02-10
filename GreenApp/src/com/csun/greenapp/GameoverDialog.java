package com.csun.greenapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameoverDialog extends DialogFragment {
	public GameoverDialog() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_network_error, container);
		Button ok = (Button) view.findViewById(R.id.dialog_network_error_XML_button_ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onClickCloseDialog();
			}
		});
		return view;
	}
	
	private void onClickCloseDialog() {
		this.dismiss();
	}
}
