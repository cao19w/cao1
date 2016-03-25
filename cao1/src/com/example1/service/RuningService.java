package com.example1.service;

import java.util.Date;

import com.example1.broadcast.AlarmReciver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class RuningService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
	// TODO Auto-generated method stub
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.i("mytime", "--------executed at"+new Date().toString());
		}
	}).start();
	//定时任务管理器，可以唤醒cpu，保持后台运行，而timer不可以
	AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
	long autotime=SystemClock.elapsedRealtime()+5*1000;//当前时间+6s
	Intent intent1=new Intent(this,AlarmReciver.class);
	PendingIntent pi=PendingIntent.getBroadcast(this, 0, intent1, 0);
	manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, autotime, pi);
	return super.onStartCommand(intent, flags, startId);
}
}
