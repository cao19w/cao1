package com.example1.cao1;

import java.util.List;

import com.example.R;
import com.example1.cao1.Fragmentp1.DataItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	private List<DataItem> da1;
	Context context;
	private int currentScreenNo = -1;// 当前屏数
 	private int screenCount = 0;// 总屏数
 	private final int perScreenCount = 8;// 每一屏显示数量
		public  GridAdapter(List<DataItem> da,Context context) {
		this.da1=da;
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return  da1.get(arg0+8);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.lable1, null);
			}
			
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.lable);
			DataItem dataItem = (DataItem) da1.get(position+8);
			imageView.setImageDrawable(dataItem.drawable);
			TextView textView = (TextView) convertView
					.findViewById(R.id.textView1);
			textView.setText(dataItem.lable);
			return convertView;
	}

}
