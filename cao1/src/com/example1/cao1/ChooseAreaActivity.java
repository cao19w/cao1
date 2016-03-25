package com.example1.cao1;

import java.util.ArrayList;
import java.util.List;

import com.example.R;
import com.example.R.id;
import com.example1.comment.LogUtil;
import com.example1.db.City;
import com.example1.db.Country;
import com.example1.db.DBManager;
import com.example1.db.Province;
import com.example1.utils.HttpCallbackListener;
import com.example1.utils.HttpUtil;
import com.example1.utils.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseAreaActivity extends BaseActivity {

	public static final int level_p=0;
	public static final int level_c=1;
	public static final int level_county=2;
	
	private ProgressDialog progressDialog;
	private TextView title;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private DBManager dbManager;
	private List<String> dataList=new ArrayList<String>();
	
	private List<Province> provincelist;
	private List<City> citylist;
	private List<Country> countylist;
	
	private Province selectdprovice;
	private City seletedcity;
	private int currentlevel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		listView=(ListView)this.findViewById(R.id.listView1);
		title=(TextView)this.findViewById(R.id.title);
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		dbManager=DBManager.getinstance(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (currentlevel==level_p) {
					selectdprovice=provincelist.get(arg2);
					
				}else if (currentlevel==level_c) {
					seletedcity=citylist.get(arg2);
				}
			}
		});
		queryProvinces();
	}
	//查询全国所有省
	private void queryProvinces(){
		provincelist=dbManager.loadProvinces();
		if (provincelist.size()>0) {
			
			dataList.clear();
			for (Province province:provincelist) {
				dataList.add(province.getP_nameString());
				
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			title.setText("中国");
			currentlevel=level_p;
			
		}else {
		queryFromServer(null, "province");
		}
	}
	//查询省内所有市
	private void querycitys(){
		citylist=dbManager.loadCity();
		if (citylist.size()>0) {
			
			dataList.clear();
			for (City province:citylist) {
				dataList.add(province.getCityname());
				
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			title.setText(selectdprovice.getP_nameString());
			currentlevel=level_c;
			
		}else {
		queryFromServer(selectdprovice.getP_code(), "city");
		}
	}
	//查询省内所有县
	private void querycountrys(){
		countylist=dbManager.loadCountry();
		if (countylist.size()>0) {
			
			dataList.clear();
			for (Country province:countylist) {
				dataList.add(province.getCountryname());
				
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			title.setText(seletedcity.getCityname());
			currentlevel=level_county;
			
		}else {
		queryFromServer(seletedcity.getCitycode(), "county");
		}
	}
	private void queryFromServer(final String code,final String type){
		String address;
		if (!TextUtils.isEmpty(code)) {
			
			address="http://www.weather.com.cn/data/list3/city"+code+".xml";
		}else {
			address="http://192.168.0.26:8088/data.xml";
		}
		showdialog();
		HttpUtil.sendhttprequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				boolean result=false;
				LogUtil.i("main", response);
				if ("province".equals(type)) {
					result=Utility.handleProvincesResponse(dbManager, response);
				}else if ("city".equals(type)) {
					result=Utility.handleCityResponse(dbManager, response, selectdprovice.getId());
				}else if ("county".equals(type)) {
					result=Utility.handleCountryResponse(dbManager, response, seletedcity.getId());
				}
					if (result) {
						//回到主线程吃力逻辑
						runOnUiThread(new Runnable() {
							public void run() {
								closedialog();
								if ("provice".equals(type)) {
									queryProvinces();
								}
								else if ("coity".equals(type)) {
									querycitys();
								}else if ("county".equals(type)) {
									querycountrys();
								}
							}
						});
					}
				
			}
			
			@Override
			public void error(Exception e) {
				// TODO Auto-generated method stub
				runOnUiThread( new Runnable() {
					public void run() {
				            closedialog();		
				            Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	private void showdialog(){
		if(progressDialog==null){
		progressDialog=new ProgressDialog(this);
		progressDialog.setMessage("正在加载。。。");
		progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	private void closedialog(){
		if(progressDialog!=null){
		progressDialog.dismiss();
			}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	if (currentlevel==level_county) {
		querycitys();
	}else if (currentlevel==level_c) {
		queryProvinces();
	}else {
		finish();
	}
	}
}

