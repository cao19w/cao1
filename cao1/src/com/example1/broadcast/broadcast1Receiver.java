package com.example1.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/*
 *
 */
public class broadcast1Receiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub

		Toast.makeText(arg0, "�յ��㲥1", 1).show();
		
		//��������㲥ʱ���ضϹ㲥����ֹ���´���
		//abortBroadcast();
	}

}
