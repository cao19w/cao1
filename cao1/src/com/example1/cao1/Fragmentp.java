package com.example1.cao1;

import java.util.ArrayList;
import java.util.List;

import com.example.R;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class Fragmentp extends Fragment {
    private List<DataItem> dataItems = new ArrayList<DataItem>();
	private int currentScreenNo = -1;// 当前屏数
	private int screenCount = 0;// 总屏数
	private final int perScreenCount = 8;// 每一屏显示数量

	private BaseAdapter adapter;
	class GridViewAdapter extends BaseAdapter {

		/**
		 * 当前屏的数量
		 */
		@Override
		public int getCount() {
			if (currentScreenNo < screenCount - 1) {
				return perScreenCount;
			}
			return dataItems.size() - (screenCount - 1) * perScreenCount;

		}

		/**
		 * 数据
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
				convertView = inflater1.inflate(R.layout.lable1, null);
			}
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.lable);
			DataItem dataItem = (DataItem) dataItems.get(position);
			imageView.setImageDrawable(dataItem.drawable);
			TextView textView = (TextView) convertView
					.findViewById(R.id.textView1);
			textView.setText(dataItem.lable);
			return convertView;
		}

	}

	int[] images = { R.drawable.ms, R.drawable.dy,
			R.drawable.xxyl, R.drawable.lr,
			R.drawable.shsg, R.drawable.wm,
			R.drawable.jd, R.drawable.zby,
			R.drawable.zlam, R.drawable.gw,
			R.drawable.ktv, R.drawable.dj,
			R.drawable.ydjs, R.drawable.jd1,
			R.drawable.qz, R.drawable.yc
			};
String[] ss={"美食","电影","休闲","丽人","团购","外卖","酒店","旅游","足疗","购物","KTV","到家","健身","景点","亲子","演出"}; 
	static class DataItem {
		String lable;
		Drawable drawable;
	}
    protected ViewPager mViewPager;
    protected CirclePageIndicator mIndicator;
	private List<View> views;
	private LayoutInflater inflater1;
	private GridView gridView,gridView2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater1=LayoutInflater.from(getActivity());
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.p1,container, false);
		String topic=getArguments().getString("topic");
		for (int i = 0; i < images.length; i++) {
		DataItem dataItem = new DataItem();
		dataItem.lable = ss[i];
		dataItem.drawable = getResources().getDrawable(images[i]);
		dataItems.add(dataItem);
	}
	
	
	// 计算总屏数
	if (dataItems.size() % perScreenCount == 0) {
		screenCount = dataItems.size() / perScreenCount;
	} else {
		screenCount = dataItems.size() / perScreenCount + 1;
	}
	currentScreenNo++;
	if(topic.equals("1")){
		currentScreenNo=0;
		
	}else {
		currentScreenNo=1;
	}
	adapter = new GridViewAdapter();
//   
//	views = new ArrayList<View>();
//    // 初始化引导图片列表
//    views.add(inflater.inflate(R.layout.p1, null));
//    views.add(inflater.inflate(R.layout.p2, null));
    gridView=(GridView)inflater.inflate(R.layout.p1, null).findViewById(R.id.gridView1);
    gridView.setAdapter(adapter);
		return view;
	}
	  public void init() {
			currentScreenNo=0;
			adapter = new GridViewAdapter();
		    gridView.setAdapter(adapter);
		  }
}
