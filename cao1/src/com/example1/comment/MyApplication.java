package com.example1.comment;


import java.util.Iterator;
import java.util.List;

import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import com.apkplug.AdsPlug.AdsCloudAgent;
import com.apkplug.AdsPlug.ApkplugAdsPlugAgent;
import com.apkplug.CloudService.ApkplugCloudAgent;
import com.example1.ui.ThemeFactory;
import com.example1.utils.SharedPreferencesUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public class MyApplication extends Application {
	private static Context context;
private FrameworkInstance frame=null;
	
	public FrameworkInstance getFrame() {
		return frame;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		context=getApplicationContext();
		 SharedPreferencesUtils.config(this);
		try {
			 frame=FrameworkFactory.getInstance().start(null,context);
				BundleContext context =frame.getSystemBundleContext();
				//�����Ʒ������������� ���� ���¹���
				ApkplugCloudAgent.getInstance(context).init();
				//ApkplugAdsPlugAgent.getInstance(frame.getSystemBundleContext()).InitAdsPlug();
				ApkplugAdsPlugAgent.getInstance(frame.getSystemBundleContext()).setAdsDownloadWifi(true);
				ApkplugAdsPlugAgent.getInstance(frame.getSystemBundleContext()).setAdsShowAuto(AdsCloudAgent.top_bottom_activity_auto_show);
				
			ThemeFactory.getInstance(frame.getSystemBundleContext());
		} catch (Exception e) {
//			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Could not create : " + e);
            e.printStackTrace();
            int nPid = android.os.Process.myPid();
			android.os.Process.killProcess(nPid);
		}
		
		
		EMOptions options = new EMOptions();
		// Ĭ����Ӻ���ʱ���ǲ���Ҫ��֤�ģ��ĳ���Ҫ��֤
		options.setAcceptInvitationAlways(false);
		
		context = this;
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		// ���app������Զ�̵�service����application:onCreate�ᱻ����2��
		// Ϊ�˷�ֹ����SDK����ʼ��2�Σ��Ӵ��жϻᱣ֤SDK����ʼ��1��
		// Ĭ�ϵ�app�����԰���ΪĬ�ϵ�process name�����У�����鵽��process name����app��process name����������
		 
		if (processAppName == null ||!processAppName.equalsIgnoreCase(context.getPackageName())) {
		    Log.e("LOGAPP", "enter the service process!");
		    //"com.easemob.chatuidemo"Ϊdemo�İ����������Լ���Ŀ��Ҫ�ĳ��Լ�����
		 
		    // ���application::onCreate �Ǳ�service ���õģ�ֱ�ӷ���
		    return;
		}
		//��ʼ��
		EMClient.getInstance().init(context, options);
		//�����������ʱ���ر�debugģʽ���������Ĳ���Ҫ����Դ
		EMClient.getInstance().setDebugMode(true);
		
	}
	private String getAppName(int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = this.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == pID) {
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				// Log.d("Process", "Error>> :"+ e.toString());
			}
		}
		return processName;
	}
	public static Context getcontext() {
		return context;
	}

}
