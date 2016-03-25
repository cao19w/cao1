package com.example1.cao1;

import com.example.R;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MessageActivity extends Activity {
	private TextView message;
	private Button button;
	private EditText number,contenText;
	private IntentFilter recevieFilter;
	private MessageReceiver messageReceiver;
	
	private IntentFilter sendFilter;
	private SendStatusReceiver receiver;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.duanxin);
	//接收系统短信
	message=(TextView)findViewById(R.id.messagecontent);
	recevieFilter=new IntentFilter();
	recevieFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
	recevieFilter.setPriority(100);
	messageReceiver=new MessageReceiver();
	registerReceiver(messageReceiver, recevieFilter);
	
	button=(Button)this.findViewById(R.id.send);
	number=(EditText)this.findViewById(R.id.number);
	contenText=(EditText)this.findViewById(R.id.content);
	
	//发送短信
	sendFilter=new IntentFilter();
	sendFilter.addAction("SENT_SMS_ACTION");
	receiver=new SendStatusReceiver();
	registerReceiver(receiver, sendFilter);
	button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			SmsManager manager=SmsManager.getDefault();
			Intent intent=new Intent("SENT_SMS_ACTION");
			PendingIntent pendingIntent=PendingIntent.getBroadcast(MessageActivity.this, 0, intent, 0);
			manager.sendTextMessage(number.getText().toString(), null, contenText.getText().toString(), pendingIntent, null);
			
		}
	});
}

@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	unregisterReceiver(messageReceiver);
	unregisterReceiver(receiver);
}
class MessageReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Bundle bundle=arg1.getExtras();
		Object[] pdusObjects=(Object[])bundle.get("pdus");
		SmsMessage[] messages=new SmsMessage[pdusObjects.length];
		for (int i = 0; i < messages.length; i++) {
			messages[i]=SmsMessage.createFromPdu((byte[])pdusObjects[i]);
			
		}
		String address=messages[0].getOriginatingAddress();
		String fullmessage="";
		for (SmsMessage message:messages) {
			fullmessage+=message.getMessageBody();
		}
		message.setText(address+":"+fullmessage);
		abortBroadcast();//拦截短信
	}
	
}

class SendStatusReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		if (getResultCode()==RESULT_OK) {
			//短信发送成功
			Toast.makeText(arg0, "succeeded", 1).show();
		}else {
			//失败
			Toast.makeText(arg0, "failed", 1).show();
		}
	}
	
}
}
