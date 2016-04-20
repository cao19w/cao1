package com.example1.ui;

import java.util.ArrayList;

import com.example.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class Fragment3 extends Fragment implements OnClickListener{
	
	Button button;
	Button downplugins,updateplugins,themeButton,chartButton;
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment3, null);
        downplugins=(Button)view.findViewById(R.id.dp);
        updateplugins=(Button)view.findViewById(R.id.up);
        themeButton=(Button)view.findViewById(R.id.theme);
        themeButton.setOnClickListener(this);
        downplugins.setOnClickListener(this);
        updateplugins.setOnClickListener(this);
        chartButton=(Button)view.findViewById(R.id.chart);
        chartButton.setOnClickListener(this);
        
		button=(Button)view.findViewById(R.id.send);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ActivityCollector.finishall();
				Intent intent=new Intent("com.example.cao1.force");
				getActivity().sendBroadcast(intent);
			}
		});
		return view;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.dp:
			Intent intent=new Intent(getActivity(),SearchPlugins.class);
			startActivity(intent);
			break;
		case R.id.up:
			Intent intent1=new Intent(getActivity(),UpdatePlugins.class);
			startActivity(intent1);
			break;
		case R.id.theme:
			Intent intent2=new Intent(getActivity(),ChartActivity.class);
			startActivity(intent2);
			break;
		case R.id.chart:
			Intent intent3=new Intent(getActivity(),Chart1Activity.class);
			startActivity(intent3);
			break;
		default:
			break;
		}
	}

}
