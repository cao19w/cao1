package com.example1.ui.test;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.example1.ui.db.SqlHelper;

import junit.framework.TestCase;

public class test extends AndroidTestCase  {
void create(){
	SqlHelper helper=new SqlHelper(null, "mydb.db", null, 1);
	helper.getWritableDatabase();
}
}
