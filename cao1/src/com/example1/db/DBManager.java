package com.example1.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	public static final String DB_NAME="cool_weather";
	
	public static final int version=1;
	private SQLiteDatabase database;
	private static DBManager db;
	private  DBManager(Context context) {
		dbHelper helper=new dbHelper(context, "weather", null, version);
		database=helper.getWritableDatabase();
	}
	public synchronized static  DBManager getinstance(Context context) {
		
		if (db==null) {
			db=new DBManager(context);
		}
		return db;
	}
	public void saveProvince(Province province) {
		if (province!=null) {
			ContentValues values=new ContentValues();
			values.put("province_name",province.getP_nameString());
			values.put("province_code", province.getP_code());
			database.insert("Province", null, values);
		}
	}
	public List<Province> loadProvinces() {
		List<Province> provinces=new ArrayList<Province>();
		Cursor cursor=database.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province p1=new Province();
				p1.setId(cursor.getInt(cursor.getColumnIndex("id")));
				p1.setP_code(cursor.getString(cursor.getColumnIndex("province_code")));
				p1.setP_nameString(cursor.getString(cursor.getColumnIndex("province_name")));
				provinces.add(p1);
			} while (cursor.moveToNext());
		}
		return provinces;
	}
	public void saveCity(City city) {
		if (city!=null) {
			ContentValues values=new ContentValues();
			values.put("city_code",city.getCitycode());
			values.put("city_name", city.getCityname());
			values.put("province_id", city.getProvinceid());
			database.insert("City", null, values);
		}
	}
	public List<City> loadCity() {
		List<City> citys=new ArrayList<City>();
		Cursor cursor=database.query("City", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				City p1=new City();
				p1.setId(cursor.getInt(cursor.getColumnIndex("id")));
				p1.setCitycode(cursor.getString(cursor.getColumnIndex("city_code")));
				p1.setCityname(cursor.getString(cursor.getColumnIndex("city_name")));
				p1.setProvinceid(cursor.getInt(cursor.getColumnIndex("province_id")));
				citys.add(p1);
			} while (cursor.moveToNext());
		}
		return citys;
	}	
	public void saveCountry(Country country) {
		if (country!=null) {
			ContentValues values=new ContentValues();
			values.put("county_name",country.getCountrycode());
			values.put("county_code", country.getCountryname());
			values.put("city_id", country.getCityid());
			database.insert("Country", null, values);
		}
	}
	public List<Country> loadCountry() {
		List<Country> country=new ArrayList<Country>();
		Cursor cursor=database.query("Country", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Country p1=new Country();
				p1.setId(cursor.getInt(cursor.getColumnIndex("id")));
				p1.setCountrycode(cursor.getString(cursor.getColumnIndex("county_code")));
				p1.setCountryname(cursor.getString(cursor.getColumnIndex("county_name")));
				p1.setCityid(cursor.getInt(cursor.getColumnIndex("city_id")));
				country.add(p1);
				
			} while (cursor.moveToNext());
		}
		return country;
	}	
}
