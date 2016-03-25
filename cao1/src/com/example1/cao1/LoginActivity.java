package com.example1.cao1;

import com.example.R;

import android.R.bool;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
	
	
	private Button button;
	private EditText account,pwd;
	private CheckBox reBox;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.login);
	button=(Button)this.findViewById(R.id.login);
	account=(EditText)this.findViewById(R.id.account);
	pwd=(EditText)this.findViewById(R.id.pwd);
	reBox=(CheckBox)this.findViewById(R.id.checkBox1);
	preferences=PreferenceManager.getDefaultSharedPreferences(this);
    boolean re=preferences.getBoolean("remeber", false);
    if (re) {
		account.setText(preferences.getString("account", ""));
		pwd.setText(preferences.getString("pwd", ""));
		reBox.setChecked(true);
	}
	
	button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String ac=account.getText().toString();
			String ppString=pwd.getText().toString();
			if (ac.equals("admin") &&  ppString.equals("123456")) {
				editor=preferences.edit();
				if (reBox.isChecked()) {
					editor.putBoolean("remeber", true);
					editor.putString("account", ac);
					editor.putString("pwd", ppString);
					
				}else{
					editor.clear();
				}
				editor.commit();
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}else {
				Toast.makeText(LoginActivity.this, "²»ÕýÈ·", 1).show();
			}
		}
	});
}
}
