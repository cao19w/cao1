package com.example1.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.R;
import com.example1.ui.db.SqlHelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends BaseActivity implements OnClickListener{
	private Fragmentp1 fragment1;
	private Fragment2 fragment2;
	private Fragment3 fragment3;
	private Fragment4 fragment4;
	private FragmentManager mFragmentManager;
	private FragmentTransaction transaction;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_main);
        mFragmentManager = getSupportFragmentManager();
		transaction = mFragmentManager.beginTransaction();
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear1.setOnClickListener(this);
		linear2.setOnClickListener(this);
		linear3.setOnClickListener(this);
		linear4.setOnClickListener(this);
		setTabSelection(1);
		
		SqlHelper helper=new SqlHelper(this, "mydb.db", null, 1);
		SQLiteDatabase db=  helper.getWritableDatabase();
		ContentValues values=new ContentValues();
//		values.put("name", "aa");
//		values.put("age", 30);
//		db.insert("people", null, values);
    }
@Override
protected void onActivityResult(int arg0, int arg1, Intent arg2) {
	// TODO Auto-generated method stub
	super.onActivityResult(arg0, arg1, arg2);
}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	ActivityCollector.removeActivity(this);
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements OnClickListener{

        public PlaceholderFragment() {
        }
//        private List<DataItem> dataItems = new ArrayList<DataItem>();
//    	private int currentScreenNo = -1;// 当前屏数
//    	private int screenCount = 0;// 总屏数
//    	private final int perScreenCount = 8;// 每一屏显示数量
//
//    	private BaseAdapter adapter;
//    	class GridViewAdapter extends BaseAdapter {
//
//    		/**
//    		 * 当前屏的数量
//    		 */
//    		@Override
//    		public int getCount() {
//    			if (currentScreenNo < screenCount - 1) {
//    				return perScreenCount;
//    			}
//    			return dataItems.size() - (screenCount - 1) * perScreenCount;
//    
//    		}
//
//    		/**
//    		 * 数据
//    		 */
//    		@Override
//    		public Object getItem(int position) {
//    			int index = currentScreenNo * perScreenCount + position;
//    			return dataItems.get(index);
//    	
//    		}
//
//    		@Override
//    		public long getItemId(int position) {
//    			return position;
//    		}
//
//    		@Override
//    		public View getView(int position, View convertView, ViewGroup parent) {
//    			if (convertView == null) {
//    				convertView = inflater1.inflate(R.layout.lable1, null);
//    			}
//    			ImageView imageView = (ImageView) convertView
//    					.findViewById(R.id.lable);
//    			DataItem dataItem = (DataItem) dataItems.get(position);
//    			imageView.setImageDrawable(dataItem.drawable);
//    			TextView textView = (TextView) convertView
//    					.findViewById(R.id.textView1);
//    			textView.setText(dataItem.lable);
//    			return convertView;
//    		}
//
//    	}
//
//    	int[] images = { R.drawable.ms, R.drawable.dy,
//    			R.drawable.xxyl, R.drawable.lr,
//    			R.drawable.shsg, R.drawable.wm,
//    			R.drawable.jd, R.drawable.zby,
//    			R.drawable.zlam, R.drawable.gw,
//    			R.drawable.ktv, R.drawable.dj,
//    			R.drawable.ydjs, R.drawable.jd1,
//    			R.drawable.qz, R.drawable.yc
//    			};
//String[] ss={"美食","电影","休闲","丽人","团购","外卖","酒店","旅游","足疗","购物","KTV","到家","健身","景点","亲子","演出"}; 
//    	static class DataItem {
//    		String lable;
//    		Drawable drawable;
//    	}
        protected ViewPager mViewPager;
        protected CirclePageIndicator mIndicator;
    	private List<View> views;
    	private LayoutInflater inflater1;
    	private GridView gridView,gridView2;
    	private Spinner spinner;
    	
    	private Fragmentp1 fragment1;
    	private Fragment2 fragment2;
    	private Fragment3 fragment3;
    	private Fragment4 fragment4;
    	private FragmentManager mFragmentManager;
    	private FragmentTransaction transaction;
    	private LinearLayout linear1;
    	private LinearLayout linear2;
    	private LinearLayout linear3;
    	private LinearLayout linear4;
    	
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	//  inflater1=LayoutInflater.from(getActivity());
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            mViewPager=(ViewPager)rootView.findViewById(R.id.pager);
//            mIndicator=(CirclePageIndicator)rootView.findViewById(R.id.indicator);
            
        	mFragmentManager = getActivity().getSupportFragmentManager();
    		transaction = mFragmentManager.beginTransaction();
    		
    		linear1 = (LinearLayout) rootView.findViewById(R.id.linear1);
    		linear2 = (LinearLayout) rootView.findViewById(R.id.linear2);
    		linear3 = (LinearLayout) rootView.findViewById(R.id.linear3);
    		linear4 = (LinearLayout) rootView.findViewById(R.id.linear4);
    		linear1.setOnClickListener(this);
    		linear2.setOnClickListener(this);
    		linear3.setOnClickListener(this);
    		linear4.setOnClickListener(this);
    		
    		
//        spinner=(Spinner)rootView.findViewById(R.id.spinner1);
//        ArrayAdapter< String> adapter1 =new ArrayAdapter<String>(getActivity(),android.R.layout.browser_link_context_header);
//        adapter1.add("北京");
//        adapter1.add("上海");
//        String[] languages = getResources().getStringArray(R.array.array1);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner.setAdapter(adapter1);
//            
//    		for (int i = 0; i < images.length; i++) {
//    			DataItem dataItem = new DataItem();
//    			dataItem.lable = ss[i];
//    			dataItem.drawable = getResources().getDrawable(images[i]);
//    			dataItems.add(dataItem);
//    		}
//    		
//    		
//    		// 计算总屏数
//    		if (dataItems.size() % perScreenCount == 0) {
//    			screenCount = dataItems.size() / perScreenCount;
//    		} else {
//    			screenCount = dataItems.size() / perScreenCount + 1;
//    		}
//    		currentScreenNo++;
//    		adapter = new GridViewAdapter();
//        //   
//        	views = new ArrayList<View>();
//            // 初始化引导图片列表
//            views.add(inflater.inflate(R.layout.p1, null));
//            views.add(inflater.inflate(R.layout.p2, null));
         //   gridView=(GridView)inflater.inflate(R.layout.p1, null).findViewById(R.id.gridView1);
          //  gridView=(GridView)rootView.findViewById(R.id.gridView3);
        //    gridView2=(GridView)inflater.inflate(R.layout.p2, null).findViewById(R.id.gridView2);
         //   gridView.setAdapter(adapter);
            
        //  gridView2.setAdapter(adapter);
//            mViewPager.setAdapter(new GuidePagerAdapter());
//    		mIndicator.setViewPager(mViewPager);
    	//	setTabSelection(1);
            return rootView;
        }
    
//        private class GuidePagerAdapter extends PagerAdapter{
//
//    		@Override
//    		public int getCount() {
//    			
//    			return views.size();
//    		}
//
//    		@Override
//    		public boolean isViewFromObject(View arg0, Object arg1) {
//    			
//    			return arg0==arg1;
//    		}
//    		
//    		@Override
//    		public Object instantiateItem(View container, int position) {
//    			
//    			View view=views.get(position);
//    			//  gridView.setAdapter(adapter);
//    			mViewPager.addView(view, 0);
//    	        if (position == views.size() - 1) {
//    	        	//position=0;
//    	  
//    	        }
//    	        return view;
//    		}
//    	    @Override
//    	    public void destroyItem(View arg0, int arg1, Object arg2) {
//    	        ((ViewPager) arg0).removeView(views.get(arg1));
//    	    }
//    		
//    	}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
		}
		
    
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linear1:
			setTabSelection(1);
			break;
		case R.id.linear2:
			setTabSelection(2);
			break;
		case R.id.linear3:
			setTabSelection(3);
			break;
		case R.id.linear4:
			setTabSelection(4);
			break;
		default:
			break;
		}
	}
	private void hideFragments(FragmentTransaction transaction){
		if(fragment1 != null){
			transaction.hide(fragment1);
		}
		if(fragment4 != null){
			transaction.hide(fragment4);
		}
		if(fragment3 != null){
			transaction.hide(fragment3);
		}
		if(fragment2 != null){
			transaction.hide(fragment2);
		}
	}
	private void setTabSelection(int index){

		 // 开启一个Fragment事务  
        transaction = mFragmentManager.beginTransaction();  
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况  
        hideFragments(transaction);
        switch (index) {
		case 1:

			if(fragment1 == null){
				fragment1 = new Fragmentp1();
				transaction.add(R.id.frament, fragment1);
			} else {
				transaction.show(fragment1);
			}
			index = 1;
			break;
		case 2:
			
			if(fragment4 != null){
				transaction.remove(fragment4);
			} 
			fragment4 = new Fragment4();
			transaction.add(R.id.frament, fragment4);
			transaction.show(fragment4);
			index = 2;
			break;
		case 3:
			if(fragment3 == null){
				fragment3 = new Fragment3();
				transaction.add(R.id.frament, fragment3);
			} else {
				transaction.show(fragment3);
			}
			index = 3;
			break;
		case 4:
			if(fragment2 == null){
				fragment2 = new Fragment2();
				transaction.add(R.id.frament, fragment2);
			} else {
				transaction.show(fragment2);
			}
			index = 4;
			break;

		default:
			break;
		}
        transaction.commit();
	}

}
