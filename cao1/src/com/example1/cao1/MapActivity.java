package com.example1.cao1;



import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.StaticLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity {

	private TextView locationteTextView;
	private LocationManager manager;
	String provider;
	private MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 SDKInitializer.initialize(getApplicationContext()); 
		   setContentView(R.layout.mylocation);
		   mapView=(MapView) findViewById(R.id.map_view);
			
		   locationteTextView=(TextView)this.findViewById(R.id.location);
		   manager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		   List<String> providerList=manager.getProviders(true);//��ȡ���п��õ�λ���ṩ��
		   if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			   provider=LocationManager.NETWORK_PROVIDER;
		}else if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			   provider=LocationManager.GPS_PROVIDER;
		}else {
			Toast.makeText(this, "no lacation privider", 1).show();
			return;
		}
		  Location location=manager.getLastKnownLocation(provider);
		  if (location!=null) {
			  showlocation(location);
		}
		  manager.requestLocationUpdates(provider, 5000, 1, locationListener);
		 
	}
	
	LocationListener locationListener=new LocationListener() {
		
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub
			 showlocation(arg0);
		}
	
	};
	
	void showlocation(final Location location){
	      BaiduMap	mBaiduMap = mapView.getMap(); 
		//����Maker�����  
		LatLng point = new LatLng(location.getLatitude(), location.getLongitude());  
		// point = new LatLng(39.963175, 116.400244);  
		//����Markerͼ��  
		BitmapDescriptor bitmap = BitmapDescriptorFactory  
		    .fromResource(R.drawable.yy);  
		//����MarkerOption�������ڵ�ͼ�����Marker  
		OverlayOptions option = new MarkerOptions()  
		    .position(point)  
		    .icon(bitmap).zIndex(16).visible(true);  
		//�ڵ�ͼ�����Marker������ʾ  
		mBaiduMap.addOverlay(option);
	        //�����ͼ״̬
	        MapStatus mMapStatus = new MapStatus.Builder()
	        .target(point)
	        .zoom(18)
	        .build();
	        //����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯


	        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
	        //�ı��ͼ״̬
	        mBaiduMap.setMapStatus(mMapStatusUpdate); 
		//��λ�ҵ�λ��
//		MapController controller=mapView.getController();
//		controller.setZoom(16);
//		GeoPoint point=new GeoPoint((int) (location.getLatitude()*1E6), (int) (location.getLongitude()*1E6));
//		controller.setCenter(point);
//		//controller.animateTo(point);
//		//������
//		MyLocationOverlay myLocationOverlay=new MyLocationOverlay(mapView);
//		LocationData data=new LocationData();
//		data.latitude=location.getAltitude();
//		data.longitude=location.getLongitude();
//		myLocationOverlay.setMarker(getResources().getDrawable(R.drawable.yy));
//		myLocationOverlay.setData(data);
//
//        // �����ų���Ӧ������    
//        myLocationOverlay.enableCompass();
//		mapView.getOverlays().add(myLocationOverlay);
//		mapView.refresh();
		

		
		//pop
//		PopupOverlay popup=new PopupOverlay(mapView, new PopupClickListener() {
//			
//			@Override
//			public void onClickedPopup(int arg0) {
//				// TODO Auto-generated method stub
//			Toast.makeText(MapActivity.this, "123", 1).show();	
//			}
//		});
//		Bitmap[] bitmaps=new Bitmap[2];
//		try {
//			
//			bitmaps[0]=BitmapFactory.decodeResource(getResources(), R.drawable.yy);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		popup.showPopup(bitmaps, point, 20);
		
		locationteTextView.setText("latitude:"+location.getLatitude()+"\n"+"longitude:"+location.getLongitude());
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					//��װ����������Ľӿڵ�ַ
					StringBuilder urlBuilder=new StringBuilder();
					urlBuilder.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
					urlBuilder.append(location.getLatitude()).append(",");
					urlBuilder.append(location.getLongitude()).append("&sensor=false");//�Ƿ������豸�Ĵ�����
					HttpClient httpClient=new DefaultHttpClient();
					HttpGet httpGet=new HttpGet(urlBuilder.toString());
					httpGet.addHeader("Accept-Language","zh-CN");
					HttpResponse httpResponse=httpClient.execute(httpGet);
					if (httpResponse.getStatusLine().getStatusCode()==200) {
						HttpEntity entity=httpResponse.getEntity();
						String response=EntityUtils.toString(entity,"utf-8");
						JSONObject jsonObject=new JSONObject(response);
						JSONArray array=jsonObject.getJSONArray("results");
						if (array.length()>0) {
							JSONObject sub=array.getJSONObject(0);
							String address=sub.getString("formatted_address");
							Message message=new Message();
							message.what=0;
							message.obj=address;
							handler.sendMessage(message); 
						}
					}
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
		
		
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String position=(String)msg.obj;
				locationteTextView.setText(position);
				
				break;

			default:
				break;
			}
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mapView.destroyDrawingCache();
		if (manager!=null) {
			manager.removeUpdates(locationListener);
		}
		super.onDestroy();
		
		}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	
	}
}
