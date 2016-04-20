package com.example.test;

import com.example1.ui.ActivityCollector;
import com.example1.ui.LoginActivity;
import com.example1.ui.MainActivity;

import android.test.AndroidTestCase;

public class Activitytest extends AndroidTestCase {
 
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}public Activitytest() {
		// TODO Auto-generated constructor stub
	}
	public  void testAdd(){
		
		assertEquals(0, ActivityCollector.activities.size());
		LoginActivity loginActivity=new LoginActivity();
		ActivityCollector.addActivity(loginActivity);
		assertEquals(1, ActivityCollector.activities.size());
		ActivityCollector.addActivity(new MainActivity());
		assertEquals(2, ActivityCollector.activities.size());//比较大小
	}
}
