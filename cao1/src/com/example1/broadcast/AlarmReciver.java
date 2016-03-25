package com.example1.broadcast;

import java.util.Date;

import com.example1.service.RuningService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Log.i("mytime", "--------executed at"+new Date().toString());
   Intent intent =new Intent(arg0,RuningService.class);
   arg0.startService(intent);
	}

}
