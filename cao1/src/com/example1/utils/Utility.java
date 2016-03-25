package com.example1.utils;

import android.text.TextUtils;

import com.baidu.platform.comapi.map.p;
import com.example1.db.City;
import com.example1.db.Country;
import com.example1.db.DBManager;
import com.example1.db.Province;

public class Utility {

	public synchronized static boolean  handleProvincesResponse(DBManager dbManager,String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] provinces=response.split(",");
			if (provinces!=null && provinces.length>0) {
				for (String p:provinces) {
					String[] array=p.split("\\|");
					Province province=new Province();
					province.setP_code(array[0]);
					province.setP_nameString(array[1]);
					dbManager.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	public synchronized static boolean  handleCityResponse(DBManager dbManager,String response,int pid) {
		if (!TextUtils.isEmpty(response)) {
			String[] provinces=response.split(",");
			if (provinces!=null && provinces.length>0) {
				for (String p:provinces) {
					String[] array=p.split("\\|");
					City province=new City();
					province.setCitycode(array[0]);
					province.setCityname(array[1]);
					province.setProvinceid(pid);
					dbManager.saveCity(province);
				}
				return true;
			}
		}
		return false;
	}
	public synchronized static boolean  handleCountryResponse(DBManager dbManager,String response,int cityid) {
		if (!TextUtils.isEmpty(response)) {
			String[] provinces=response.split(",");
			if (provinces!=null && provinces.length>0) {
				for (String p:provinces) {
					String[] array=p.split("\\|");
					Country province=new Country();
					province.setCountrycode(array[0]);
					province.setCountryname(array[1]);
					province.setCityid(cityid);
					dbManager.saveCountry(province);
				}
				return true;
			}
		}
		return false;
	}
	
	
}
