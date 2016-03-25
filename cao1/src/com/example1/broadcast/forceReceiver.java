package com.example1.broadcast;

import com.example1.cao1.ActivityCollector;
import com.example1.cao1.LoginActivity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.Toast;

/*
 *
 */
public class forceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent arg1) {
		// TODO Auto-generated method stub

	AlertDialog.Builder diaBuilder=new AlertDialog.Builder(context);
	diaBuilder.setTitle("warning");
	diaBuilder.setMessage("you are forced to be offline");
	diaBuilder.setCancelable(false);
	diaBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg2, int arg1) {
			// TODO Auto-generated method stub
			ActivityCollector.finishall();
			Intent intent=new Intent(context,LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	});
	AlertDialog alertDialog=diaBuilder.create();
	alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	alertDialog.show();

	}

}
