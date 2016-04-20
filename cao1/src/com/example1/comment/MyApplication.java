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
				//启动云服务包括插件搜索 下载 更新功能
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
		// 默认添加好友时，是不需要验证的，改成需要验证
		options.setAcceptInvitationAlways(false);
		
		context = this;
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		// 如果app启用了远程的service，此application:onCreate会被调用2次
		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		// 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
		 
		if (processAppName == null ||!processAppName.equalsIgnoreCase(context.getPackageName())) {
		    Log.e("LOGAPP", "enter the service process!");
		    //"com.easemob.chatuidemo"为demo的包名，换到自己项目中要改成自己包名
		 
		    // 则此application::onCreate 是被service 调用的，直接返回
		    return;
		}
		//初始化
		EMClient.getInstance().init(context, options);
		//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
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
