package com.example1.ui;

import java.util.List;

import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import com.apkplug.CloudService.ApkplugCloudAgent;
import com.apkplug.CloudService.bean.updateAppBean;
import com.apkplug.CloudService.bean.updateAppInfo;
import com.apkplug.CloudService.model.AppQueryModel;
import com.apkplug.CloudService.model.appModel;
import com.apkplug.CloudService.update.updateCallBack;
import com.example.R;
import com.example1.comment.MyApplication;
import com.example1.ui.adapter.UpdataBundleAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePlugins extends Activity {
	private FrameworkInstance frame=null;
	private List<BundleStutes> bundles=null;
	private ListView bundlelist =null;
	private UpdataBundleAdapter adapter=null;
	private TextView info=null;
	public Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pluginsup);
		frame=((MyApplication)this.getApplication()).getFrame();
		info =(TextView)this.findViewById(R.id.info);
		initBundleList();
	}
	@Override
	protected void onRestart() {
		frame=((MyApplication)this.getApplication()).getFrame();
		super.onRestart();
	}
	/**
	 * 鍒濆鍖栨樉�?哄凡瀹夎鎻掍欢鐨刄I
	 */
	public void initBundleList(){
		 //宸插畨瑁呮彃浠跺垪琛�?
        bundlelist=(ListView)findViewById(R.id.bundlelist);
		bundles=new java.util.ArrayList<BundleStutes>();

		BundleContext context =frame.getSystemBundleContext();
		updateAppBean bean=new updateAppBean();
		List<updateAppInfo> l=new java.util.ArrayList<updateAppInfo>();
		for(int i=0;i<context.getBundles().length;i++)
		{		
				BundleStutes bs=new BundleStutes();
				bs.b=context.getBundles()[i];
				bs.updatastutes=0;
				bs.appid=context.getBundles()[i].getSymbolicName();
				bundles.add(bs);   
				//濉厖鏇存柊鏌ヨ�?�楁�?
				l.add(updateAppInfo.createUpdateAppInfo(context.getBundles()[i]));
		}
		adapter=new UpdataBundleAdapter(UpdatePlugins.this,bundles);
		bundlelist.setAdapter(adapter);
		bean.setApps(l);
		//浠庝簯鏈嶅姟鍣ㄦ煡璇㈡墍鏈夊凡�?�夎鎻掍欢鐗堟湰鐘舵��?
		ApkplugCloudAgent.getInstance(null).getcheckupdate().checkupdate(bean,new impupdateCallBack());
	}
	/**
	 * 閫氳繃appid鍖归厤宸插畨瑁呮彃浠�?
	 * @param appid
	 * @return
	 */
	public BundleStutes getBundleByAppid(String appid){
		for(int i=0;i<bundles.size();i++){
			if(bundles.get(i).appid.equals(appid)){
				//鏌ユ壘鍒�?
				return bundles.get(i);
			}
		}
		return null;
	} 
	/**
	 * 鎻掍欢鐗堟湰鏇存柊OSGI鏈嶅姟鍥炶皟鍑芥�?
	 * @author 姊佸墠姝�?
	 *
	 */
	class impupdateCallBack implements updateCallBack{
		@Override
		public void onFailure(int arg0, final String arg1) {
			UpdatePlugins.this.runOnUiThread(new Runnable(){
				@Override
				public void run() {
					info.setText("查询插件版本状�?�出现异�?:\n\r"+arg1);
					Toast.makeText(UpdatePlugins.this, "查询插件版本状�?�出现异�?:\n\r"+arg1,
						     Toast.LENGTH_SHORT).show();
				}
			});
		}
		@Override
		public void onSuccess(int arg0, final AppQueryModel<appModel> arg1) {
						// arg1 鏈夋洿鏂扮増鏈殑鎻掍欢appbean arg2 鎻掍欢鏈嶅姟鐘舵�佷俊鎭�?
			UpdatePlugins.this.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								Toast.makeText(UpdatePlugins.this, "查询插件版本状�?�完�?:\n\r有新版本插件数量�?:"+arg1.getData().size(),
									     Toast.LENGTH_SHORT).show();
								info.setText("查询插件版本状�?�完�?:\n\r有新版本插件数量�?:"+arg1.getData().size());
							}
						});
						for(int i=0;i<arg1.getData().size();i++){
							appModel a=arg1.getData().get(i);
							BundleStutes bs=getBundleByAppid(a.getAppid());
							if(bs!=null){
								//鍖归厤鍒版彃浠�,骞舵洿鏂板叾鐘舵��
								bs.updatastutes=1;
								bs.ab=a;
							}
						}
						//鍒锋柊鍒楄�??
						UpdatePlugins.this.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								adapter.notifyDataSetChanged();
							}
						});
		}

		
		
	}
}
