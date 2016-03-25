package com.example1.cao1.test;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.example1.cao1.db.SqlHelper;

import junit.framework.TestCase;

public class test extends AndroidTestCase  {
void create(){
	SqlHelper helper=new SqlHelper(null, "mydb.db", null, 1);
	helper.getWritableDatabase();
}
}
