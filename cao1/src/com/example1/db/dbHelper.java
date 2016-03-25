package com.example1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {

	public static final String province="create table Province (id integer primary key autoincrement,"
			+ "province_name text,"
			+ "province_code text)";
	static String cityString="create table City (id integer primary key autoincrement,"
			+ "city_name text,"
			+ "city_code text,"
			+ "province_id integer)";
	static String county="create table Country (id integer  primary key autoincrement,"
			+ "county_name text"
			+ "county_code text"
			+ "city_id integer)";
	
	public dbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(province);
		db.execSQL(cityString);
		db.execSQL(county);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
