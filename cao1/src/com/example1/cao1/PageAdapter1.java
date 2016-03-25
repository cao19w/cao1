package com.example1.cao1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

public class PageAdapter1 extends FragmentStatePagerAdapter {
	 Context context;

	public PageAdapter1(FragmentManager fm,Context context) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		 Bundle args = new Bundle();
		    args.putString("topic", "1");
		    Fragmentp fragment = (Fragmentp) Fragment.instantiate(
		        context, Fragmentp.class.getName(), args);
		    return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public void setPrimaryItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		super.setPrimaryItem(container, position, object);
		Fragmentp fragment = (Fragmentp) object;
		    fragment.init();
	}

}
