package com.example1.cao1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.R;

import android.R.raw;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class Fragment2 extends Fragment implements OnClickListener{
	private IntentFilter intentFilter;
	private NetworkChangeReceiver netmorkreciver;
	
	
	private LocalBroadcastManager localBroadcastManager;
	
	private Button button2,button3,button4,notication,send,play,mapButton;
	private ListView listView;
	List<String> list=new ArrayList<String>();
	ArrayAdapter<String> adapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//动态注册广播
		intentFilter=new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//检测网络变化的广播
		netmorkreciver=new NetworkChangeReceiver();
		getActivity().registerReceiver(netmorkreciver, intentFilter);
		//注册本地广播,其他程序接收不到,本地广播需要动态注册，静态注册接收器是全局广播
		localBroadcastManager=LocalBroadcastManager.getInstance(getActivity());
		localBroadcastManager.registerReceiver(netmorkreciver, intentFilter);

	}
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View view=inflater.inflate(R.layout.fragment2, null);
	Button button=(Button)view.findViewById(R.id.button1);
	button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent=new Intent("com.example.cao1.test");
			getActivity().sendBroadcast(intent);
		}
	});
	mapButton=(Button)view.findViewById(R.id.map);
	button2=(Button)view.findViewById(R.id.insert);
	button3=(Button)view.findViewById(R.id.select);
	button4=(Button)view.findViewById(R.id.delete);
	play=(Button)view.findViewById(R.id.player);
	notication=(Button)view.findViewById(R.id.sendnotification);
	send=(Button)view.findViewById(R.id.message1);
	send.setOnClickListener(this);
	notication.setOnClickListener(this);
	button2.setOnClickListener(this);
	button3.setOnClickListener(this);
	button4.setOnClickListener(this);
	play.setOnClickListener(this);
	listView=(ListView)view.findViewById(R.id.listView1);
	Uri uri=Uri.parse(pathString);
//	Cursor cursor=getActivity().getContentResolver().query(uri, null, null, null, null);
	mapButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(getActivity(),MapActivity.class);
			startActivity(intent);
		}
	});
 adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list);
	listView.setAdapter(adapter);
	return view;
}

@Override
public void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	getActivity().unregisterReceiver(netmorkreciver);
	localBroadcastManager.unregisterReceiver(netmorkreciver);
}
class NetworkChangeReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
		if (networkInfo!=null && networkInfo.isAvailable()) {
			Toast.makeText(getActivity(), "network is available", 1).show();//有网络
		}
		else {
			Toast.makeText(getActivity(), "network is unavailable", 1).show();
		}
		
	}
	
}
static String pathString="content://com.example1.provider/people";
String newid;
@Override
public void onClick(View arg0) {
	// TODO Auto-generated method stub
	Uri uri=Uri.parse(pathString);
	switch (arg0.getId()) {
	case R.id.insert:		
		ContentValues values=new ContentValues();
		values.put("name", "aa");
		values.put("age", 301);
		Uri newUri=getActivity().getContentResolver().insert(uri, values);
		newid=newUri.getPathSegments().get(1);
		break;
case R.id.select:
	    list.removeAll(list);
		Cursor cursor=getActivity().getContentResolver().query(uri, null, null, null, null);
		if (cursor!=null) {
			while (cursor.moveToNext()) {
				String name=cursor.getString(cursor.getColumnIndex("name"));
				int age=cursor.getInt(cursor.getColumnIndex("age"));
				list.add(name+":"+age);
				Log.i("FRagment2", name+":"+age);
			}
			
			cursor.close();
		}
		break;
case R.id.delete:
	 list.removeAll(list);
	// uri=Uri.parse(pathString+"/"+newid);
	getActivity().getContentResolver().delete(uri, null, null);
	break;
case R.id.sendnotification:
	NotificationManager manager=(NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
	Notification notification=new Notification(R.drawable.ic_launcher,"this is ticker text",System.currentTimeMillis());
//	Intent intent=new Intent(this,);
//	PendingIntent piIntent=PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	notification.setLatestEventInfo(getActivity(), "title", "content", null);
	//通知音频
	Uri sounduri=Uri.fromFile(new File("/system/media/audio/alarm/GoodLuck.ogg"));
	notification.sound=sounduri;
	//通知时震动
	long[] values1={0,1000,1000,1000};//静止震动交替
	notification.vibrate=values1;
	//指示灯
	notification.ledARGB=Color.RED;
	notification.ledOnMS=1000;//亮起时间
	notification.ledOffMS=1000;//暗去时间
	notification.flags=Notification.FLAG_SHOW_LIGHTS;
	// 可以设置默认灯光，声音，震动
	//notification.defaults=Notification.DEFAULT_LIGHTS;
	manager.notify(1001,notification);
	break;
case R.id.message1:
	Intent intent=new Intent(getActivity(),MessageActivity.class);
	startActivity(intent);
	break;
	case R.id.player:
		Intent intent1=new Intent(getActivity(),PlayerActivity.class);
		startActivity(intent1);
		
		break;
	}
	adapter.notifyDataSetChanged();
}
}
