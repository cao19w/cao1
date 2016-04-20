package com.example1.ui;


import java.util.ArrayList;
import java.util.List;

import com.example.R;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class xxaa extends Activity {

	 private GestureDetector detector;
     
     private ViewSwitcher switcher;
 	private LayoutInflater inflater1;
 	private List<DataItem> dataItems = new ArrayList<DataItem>();
 	private int currentScreenNo = -1;// ��ǰ����
 	private int screenCount = 0;// ������
 	private final int perScreenCount = 8;// ÿһ����ʾ����
	private GridView gridView,gridView2;
 	private BaseAdapter adapter = new GridViewAdapter();

 	int[] images = { R.drawable.ms, R.drawable.dy,
 			R.drawable.xxyl, R.drawable.lr,
 			R.drawable.shsg, R.drawable.wm,
 			R.drawable.jd, R.drawable.zby,
 			R.drawable.zlam, R.drawable.gw,
 			R.drawable.ktv, R.drawable.dj,
 			R.drawable.ydjs, R.drawable.jd1,
 			R.drawable.qz, R.drawable.yc
 			};
String[] ss={"��ʳ","��Ӱ","����","����","�Ź�","����","�Ƶ�","����","����","����","KTV","����","����","����","����","�ݳ�"}; 
 	static class DataItem {
 		String lable;
 		Drawable drawable;
 	}
 	
 	protected CirclePageIndicator mIndicator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		   gridView=(GridView)this.findViewById(R.id.gridView1);
			for (int i = 0; i < images.length; i++) {
    			DataItem dataItem = new DataItem();
    			dataItem.lable = ss[i];
    			dataItem.drawable = getResources().getDrawable(images[i]);
    			dataItems.add(dataItem);
    		}
    		
    		
    		// ����������
    		if (dataItems.size() % perScreenCount == 0) {
    			screenCount = dataItems.size() / perScreenCount;
    		} else {
    			screenCount = dataItems.size() / perScreenCount + 1;
    		}
    		currentScreenNo++;
    		adapter = new GridViewAdapter();
    		  gridView.setAdapter(adapter);
	}
	class GridViewAdapter extends BaseAdapter {

		/**
		 * ��ǰ��������
		 */
		@Override
		public int getCount() {
			if (currentScreenNo < screenCount - 1) {
				return perScreenCount;
			}
			return dataItems.size() - (screenCount - 1) * perScreenCount;
		}

		/**
		 * ����
		 */
		@Override
		public Object getItem(int position) {
			int index = currentScreenNo * perScreenCount + position;
			return dataItems.get(index);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.lable1, null);
			}
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.lable);
			DataItem dataItem = (DataItem) getItem(position);
			imageView.setImageDrawable(dataItem.drawable);
			TextView textView = (TextView) convertView
					.findViewById(R.id.textView1);
			textView.setText(dataItem.lable);
			return convertView;
		}

	}
	
}
