package com.example1.ui;

import java.util.ArrayList;
import java.util.List;

import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import com.apkplug.CloudService.ApkplugCloudAgent;
import com.apkplug.CloudService.SearchApp.AppSearchCallBack;
import com.apkplug.CloudService.bean.appSearchBean;
import com.apkplug.CloudService.model.AppQueryModel;
import com.apkplug.CloudService.model.appModel;
import com.example.R;
import com.example1.comment.LogUtil;
import com.example1.comment.MyApplication;
import com.example1.ui.adapter.SearchBundleAdapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SearchPlugins extends ListActivity {
	private FrameworkInstance frame=null;
	private appSearchBean bean=null;
	private SearchBundleAdapter adapter;
    private List<org.osgi.framework.Bundle> bundles=null;
    private ArrayList<appModel> apps = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.pluginslist);
    	 frame=((MyApplication)this.getApplication()).getFrame();
         apps = new ArrayList<appModel>();
         updataDate(frame.getSystemBundleContext());
         adapter = new SearchBundleAdapter(SearchPlugins.this,frame.getSystemBundleContext(),apps);
         setListAdapter(adapter);
    }
	public void updataDate(BundleContext context){
		appSearchBean bean=new appSearchBean();
    	bean.setPagenum(10);
    	bean.setPage(0);
    	//查询

    	ApkplugCloudAgent.getInstance(null).getAppSearch().search(bean,new impAppSearchCallBack());
    	bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
		BundleContext context1 =frame.getSystemBundleContext();
		for(int i=0;i<context1.getBundles().length;i++)
		{
			//获取已安装插�?
			//if(context.getBundles()[i].getBundleId()!=0&&!context.getBundles()[i].getSymbolicName().equals("com.example.bundledemotheme"))
				bundles.add(context1.getBundles()[i]);      
				LogUtil.i("aaa", bundles.get(i).getName()+bundles.get(i).getVersion());
		}
	
	}
	/**
	 * 查询回调接口
	 * @author 梁前�?
	 *
	 */
	  class impAppSearchCallBack implements AppSearchCallBack{
		   public void onFailure(int arg0, final String arg1) {
			   //服务查询失败
			   SearchPlugins.this.runOnUiThread(new Runnable(){
					public void run() {
						Toast.makeText(SearchPlugins.this, arg1,
					     Toast.LENGTH_SHORT).show();
					}
				});	
			}
		   @Override
			public void onSuccess(int arg0, AppQueryModel<appModel> arg1) {
			   System.out.println("查询成功");
				for(int i=0;i<arg1.getData().size();i++){
					System.out.println(arg1.getData().get(i).getAppname());
				}
				for (int i = 0; i < arg1.getData().size(); i++) {
					appModel ab=arg1.getData().get(i);
					apps.add(ab);
				 }
				//更新列表
				SearchPlugins.this.runOnUiThread(new Runnable(){
				 		public void run(){
						 adapter.notifyDataSetChanged();
				 		}
				});
			}
	
			
			
	   }
}
