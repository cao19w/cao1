package com.example1.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.R;
import com.example1.ui.adapter.GridAdapter;
import com.example1.ui.adapter.PageAdapter1;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class Fragmentp1 extends Fragment {
	
	 private List<DataItem> dataItems = new ArrayList<DataItem>();
 	private int currentScreenNo = -1;// 当前屏数
 	private int screenCount = 0;// 总屏数
 	private final int perScreenCount = 8;// 每一屏显示数量
 	private LayoutInflater inflater1;
 	private BaseAdapter adapter,adapter4;
 	GridAdapter adapter2,adapter3;
 	class GridViewAdapter extends BaseAdapter {

 		private List<DataItem> da1;
 		public  GridViewAdapter(List<DataItem> da) {
			this.da1=da;
		}
 		/**
 		 * 当前屏的数量
 		 */
 		@Override
 		public int getCount() {
 			if (currentScreenNo < screenCount - 1) {
 				return perScreenCount;
 			}
 			return da1.size() - (screenCount - 1) * perScreenCount;
 	//		return da1.size() ;
 		}

 		/**
 		 * 数据
 		 */
 		@Override
 		public Object getItem(int position) {
 			int index = currentScreenNo * perScreenCount + position;
 			return da1.get(index);
 	 //return  da1.get(position);
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
 			DataItem dataItem = (DataItem) getItem(position);
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
 	public static class DataItem {
 		public String lable;
 		public Drawable drawable;
 	}
	private View view;
 	private GridView gridView,gridView2,gridView3;
 	protected ViewPager mViewPager;
    protected CirclePageIndicator mIndicator;
	private Spinner spinner;
	private List<View> views;
	 private List<Fragmentp> mFragments = new ArrayList<Fragmentp>();
	 PageAdapter1 pageAdapter1;
	 Boolean  stopThread=false;
	 
		private ScheduledExecutorService scheduledExecutorService;

		// 切换当前显示的图片
		private Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (!stopThread) {
					
		//			System.out.println("currentItem2: " + currentScreenNo);
				mViewPager.setCurrentItem(currentScreenNo);// 切换当前显示的图片
				}
			};
		};
		private Runnable mRunnable= new Runnable() {

			public void run() {
		//		System.out.println("currentItem: " + currentScreenNo+" "+stopThread);
		if (!stopThread) {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				synchronized (mViewPager) {
				
					currentScreenNo = (currentScreenNo + 1) % views.size();
					//System.out.println("currentItem1: " + currentScreenNo);
				
					handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
				//	handler.postDelayed(mRunnable, 2000);
				}
			}
		
	}

		};
		
		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(mRunnable, 1, 2, TimeUnit.SECONDS);

		// new Thread(mRunnable).start();
			super.onStart();
		}
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		inflater1=inflater;
		view = inflater.inflate(R.layout.f1, null);
		initview();
		return view;
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		scheduledExecutorService.shutdown();
		handler.removeCallbacks(mRunnable);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		  
		     mViewPager.setAdapter(new GuidePagerAdapter());
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(mRunnable);
		stopThread=true;
		mViewPager=null;
	 //	scheduledExecutorService.shutdown();
		super.onDestroy();
		
	}
	public void initview() {
		   mViewPager=(ViewPager)view.findViewById(R.id.pager);
		      mIndicator=(CirclePageIndicator)view.findViewById(R.id.indicator);
		      spinner=(Spinner)view.findViewById(R.id.spinner1);
		      ArrayAdapter< String> adapter1 =new ArrayAdapter<String>(getActivity(),android.R.layout.browser_link_context_header);
		      adapter1.add("北京");
		      adapter1.add("上海");
		      String[] languages = getResources().getStringArray(R.array.array1);
		      adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		          spinner.setAdapter(adapter1);
			
					views = new ArrayList<View>();
		            // 初始化引导图片列表
		            views.add(inflater1.inflate(R.layout.p1, null));
		            views.add(inflater1.inflate(R.layout.p2, null));
		        //    views.add(inflater.inflate(R.layout.test, null));
		       	 
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
					adapter = new GridViewAdapter(dataItems);
			 	 gridView=(GridView)views.get(0).findViewById(R.id.gridView1);
				  gridView.setAdapter(adapter);

					adapter2 = new GridAdapter(dataItems,getActivity());
					 gridView2=(GridView)views.get(1).findViewById(R.id.gridView2);
					  gridView2.setAdapter(adapter2);
//					  gridView3=(GridView)views.get(2).findViewById(R.id.gridView3);
//					  gridView3.setAdapter(adapter2);
		 	     mViewPager.setAdapter(new GuidePagerAdapter());
		 	     mIndicator.setViewPager(mViewPager);
	}
	 private class GuidePagerAdapter extends PagerAdapter{
		// private ViewPager mViewPager;
			private FragmentManager mFragmentManager;
			 private List<Fragmentp> mFragments;

 		@Override
 		public int getCount() {
 			
 			return views.size();
 		//	return mFragments.size();
 		}

 		@Override
 		public boolean isViewFromObject(View arg0, Object arg1) {
 			
 			return arg0==arg1;
 		}
 		
 		@Override
 		public Object instantiateItem(ViewGroup container, int position) {
 			
 			View view=views.get(position);
 //			if(position==0){
// 				adapter = new GridViewAdapter(dataItems);
//	 		 	 gridView=(GridView)view.findViewById(R.id.gridView1);
//	 			  gridView.setAdapter(adapter);
 //			}		
// 			else{
// 				currentScreenNo++;	
// 				for (int i = 0; i < 8; i++) {
// 					
// 					dataItems.remove(0);
//				}
 				
// 				adapter2 = new GridAdapter(dataItems);
// 				gridView2=(GridView)view.findViewById(R.id.gridView2);
// 				gridView2.setAdapter(adapter2);
 //			}
 			Log.i("sd", "-----------"+currentScreenNo);
 			mViewPager.addView(view, position);
// 	        if (position == views.size() - 1) {
// 	        	position=0;
// 	  
// 	        }
 	        return view;
 	        

 		}
 	    @Override
 	    public void destroyItem(View arg0, int arg1, Object arg2) {
 	        ((ViewPager) arg0).removeView(views.get(arg1));
 	    }
 		
 	}
}
