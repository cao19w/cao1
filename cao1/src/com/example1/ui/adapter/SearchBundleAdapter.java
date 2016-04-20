package com.example1.ui.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apkplug.Bundle.InstallBundler;
import org.apkplug.Bundle.InstallInfo;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import com.apkplug.CloudService.ApkplugCloudAgent;
import com.apkplug.CloudService.download.AppDownload;
import com.apkplug.CloudService.download.AppDownloadCallBack;
import com.apkplug.CloudService.model.appModel;
import com.example.R;
import com.example1.comment.LogUtil;
import com.example1.comment.MyApplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchBundleAdapter extends LListAdapter<appModel>{
	private BundleContext context=null;
	private InstallBundler ib=null;
	FrameworkInstance frame=null;
	 List<org.osgi.framework.Bundle> bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
	public SearchBundleAdapter(Context c, BundleContext context,List<appModel> data) {
		super(c, data);
		this.context=context;
		ib=new InstallBundler(context);
		frame=((MyApplication)c.getApplicationContext()).getFrame();
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	
	public appModel getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	
	public View getView(int position, View convertView, ViewGroup arg2) {
		final ListViewHolder viewHolder;
		final appModel ab=list.get(position);
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.index_item_listview, null);
			viewHolder = new ListViewHolder();
			viewHolder.imageViewIcon = (ImageView)convertView.findViewById(R.id.image_item_1);
			viewHolder.appName = (TextView)convertView.findViewById(R.id.text_item_1);
			//viewHolder.dsize = (TextView)convertView.findViewById(R.id.text_item_2);
			viewHolder.appinfo = (TextView)convertView.findViewById(R.id.text_item_2);
			viewHolder.appSize = (TextView)convertView.findViewById(R.id.text_item_3);
			viewHolder.download = (TextView)convertView.findViewById(R.id.text_item_4);
			viewHolder.imgSplit = (ImageView)convertView.findViewById(R.id.image_item_2);
			viewHolder.imgDownLoad = (ImageView)convertView.findViewById(R.id.image_item_3);
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ListViewHolder)convertView.getTag();
		}
		
		//if(position % 2 == 0) {
			viewHolder.imgDownLoad.setBackgroundResource(R.drawable.down_btn_10);
		//}else {
			//viewHolder.imgDownLoad.setBackgroundResource(R.drawable.down_btn_0);
		//}
			
		viewHolder.appName.setText(ab.getAppname());
		//viewHolder.dsize.setText(""+ab.getD_num());
		viewHolder.appinfo.setText(ab.getInfo());
		viewHolder.appSize.setText(String.format("%2.2fM", (float)ab.getSize()/(1024*1024)));
		//查询�?下是否已经安装过该插�?
		org.osgi.framework.Bundle b=gatHadBundle(context,ab);
    	if(b==null){
    		//没有安装过该插件
    		viewHolder.download.setText("�?  �?") ;
    	}else{
    		//已经安装过该插件
    		if(ab.getBundlevarsion().equals(b.getVersion())){
    			//插件存在且版本相�?
    			viewHolder.download.setText("�? �?") ;
    		}else{
    			viewHolder.download.setText("�? �?") ;
    		}
    	}	
	 // FinalBitmap fb=FinalBitmap.create(this.mContext);
				 //System.out.println("iconurl "+ab.getIconurl());
	 // fb.display(viewHolder.imageViewIcon, ab.getIconurl());
    	viewHolder.imgDownLoad.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) {
						if(viewHolder.download.getText().equals("�?  �?") ){
							try {
								download(context,ab,viewHolder);
							} catch (IOException e) {
								// TODO 鑷姩鐢熸垚鐨� catch 鍧�
								e.printStackTrace();
							}
						}else if(viewHolder.download.getText().equals("�? �?") ){
									try {
										download(context,ab,viewHolder);
									} catch (IOException e) {
										// TODO 鑷姩鐢熸垚鐨� catch 鍧�
										e.printStackTrace();
									}
						}else if(viewHolder.download.getText().equals("�? �?")){
							//启动主题
//							LogUtil.i("aaa",ab.getAppname());
//                            if (ab.getAppname().toString().equals("IMTheme1")) {
//                            	
//									BundleContext context1 =frame.getSystemBundleContext();
//							for(int i=0;i<context1.getBundles().length;i++)
//							{
//								//获取已安装主题插�?
//								//if(context.getBundles()[i].getBundleId()!=0&&!context.getBundles()[i].getSymbolicName().equals("com.example.bundledemotheme"))
//									bundles.add(context1.getBundles()[i]);        	        
//							}
//						
//							try {
//								if (bundles.get(1).getState()!=bundles.get(1).ACTIVE) {
//									bundles.get(1).start();
//								}
//								
//							} catch (BundleException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							
//                            	}
//							if(bundles.get(1).getBundleActivity()!=null){
//								Intent i=new Intent();
//				    			i.setClassName(mContext, bundles.get(1).getBundleActivity().split(",")[0]);
//								i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//								mContext.startActivity(i);
//							}else{
//								
//								Toast.makeText(mContext, "该插件没有配置BundleActivity",
//									     Toast.LENGTH_SHORT).show();
//							}
//							return;
//							}
                        
						
									org.osgi.framework.Bundle b= gatHadBundle(context,ab);
									if(b!=null){
										if(b.getBundleActivity()!=null){
											Intent i=new Intent();
											i.setClassName(mContext, b.getBundleActivity().split(",")[0]);
											i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
											mContext.startActivity(i);
										}else{
											try {
												b.start();
											} catch (BundleException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}
										
						}else if(viewHolder.download.getText().equals("�? �?")){
									org.osgi.framework.Bundle bb= gatHadBundle(context,ab);
									if(bb.getState()==org.osgi.framework.Bundle.STOPPING){
										try {
											bb.stop();
										} catch (BundleException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
						}
				
			});
		convertView.setOnLongClickListener(
				new OnLongClickListener(){

					@Override
					public boolean onLongClick(View v) {
						AlertDialog.Builder alertbBuilder = new AlertDialog.Builder(mContext);
						alertbBuilder
								.setMessage("")
								.setNegativeButton("卸载",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,
													int which) {
														//鐩存帴浣跨敤 Bundle.uninstall()鍗歌�?
												org.osgi.framework.Bundle b= gatHadBundle(context,ab);
												if(b!=null)	{	
													try {
															b.uninstall();
													} catch (Exception e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
													}
												}
												dialog.cancel();
											}
										}).create();
						alertbBuilder.show();
						return false;
					}
			
		});
			return convertView;
		}
	/**
	 * AppDownload 鎻掍欢涓嬭浇鏈嶅�?
	 * @param context
	 * @param ab
	 * @param viewHolder
	 * @throws IOException
	 */
    public void download(BundleContext context,final appModel ab,final ListViewHolder viewHolder) throws IOException{
    	AppDownload service=ApkplugCloudAgent.getInstance(null).getAppDownload();
    	if(service!=null){	
				  service.download(ab,mContext, 
						  new AppDownloadCallBack(){
						public void onFailure(int arg1, final String arg2) {
							//鎻掍欢涓嬭浇澶辫�?
							viewHolder.appinfo.post(new Runnable(){
				        		public void run(){
				        			viewHolder.appinfo.setText(arg2) ;
				        		}
				        	});
						}
						public void onInstallSuccess(final int stutas,org.osgi.framework.Bundle arg0) {
							//鎻掍欢�?�夎鎴愬姛
							viewHolder.appinfo.post(new Runnable(){
				        		public void run(){
				        			viewHolder.download.setText("�? �?") ;
				        			viewHolder.appinfo.setText(stutasToStr(stutas)) ;
				        		}
				        	});
						}
						public void onProgress(final int bytesWritten, final int totalSize,final String Speed) {
							//鎻掍欢鏂囦欢涓嬭浇杩涘害
							viewHolder.appinfo.post(new Runnable(){
				        		public void run(){
				        			viewHolder.appinfo.setText(String.format("(%d%%) speed %s", (totalSize > 0) ? (int)(((float)bytesWritten / (float)totalSize) * 100) : -1,Speed)) ;
				        		}
				        	});
						}
					
						@Override
						public void onDownLoadSuccess(String arg0) {
							// TODO Auto-generated method stub
							InstallInfo installinfo=new InstallInfo();
							installinfo.setApkFilePath(arg0);
							installinfo.isInstallAPK=true;
							installinfo.isCheckVersion=false;
							try {
								ib.installBundleFile(installinfo, new installCallback(){
									@Override
									public void callback(final int arg0,
											org.osgi.framework.Bundle arg1) {
										if(arg0==installCallback.stutas5||arg0==installCallback.stutas7){
											//鎻掍欢�?�夎鎴愬姛
											viewHolder.appinfo.post(new Runnable(){
								        		public void run(){
								        			viewHolder.download.setText("�? �?") ;
								        			viewHolder.appinfo.setText(stutasToStr(arg0)) ;
								        		}
								        	});
										}else {
											viewHolder.appinfo.post(new Runnable(){
								        		public void run(){
								        			viewHolder.download.setText("�? �?") ;
								        			viewHolder.appinfo.setText(stutasToStr(arg0)) ;
								        		}
								        	});
										}
									}
								});
							} catch (Exception e) {
								e.printStackTrace();
							}
					
						}
					});
    	}
    }
	/**
	 * appBean姣旇緝鏌ヨ宸插畨瑁呮彃锟�?
	 * @param context
	 * @param ab
	 * @return
	 */
	public org.osgi.framework.Bundle gatHadBundle(BundleContext context,appModel ab) {
		// TODO Auto-generated method stub
    	 org.osgi.framework.Bundle[] bs=context.getBundles();
    	 for(int i=0;i<bs.length;i++)
 		{
 			if(bs[i].getSymbolicName().equals(ab.getSymbolicName())){
 				return bs[i];
 			}    	        
 		}
 		return null;
	}
	private String stutasToStr(int stutas){
		if(stutas==installCallback.stutas){
			return "缺少SymbolicName";
		}else if(stutas==installCallback.stutas1){
			return "已是�?新版�?";
		}else if(stutas==installCallback.stutas2){
			return "版本号不正确";
		}else if(stutas==installCallback.stutas3){
			return " 版本相等";
		}else if(stutas==installCallback.stutas4){
			return "无法获取正确的证�?";
		}else if(stutas==installCallback.stutas5){
			return "更新成功";
		}else if(stutas==installCallback.stutas6){
			return "证书不一�?";
		}else if(stutas==installCallback.stutas7){
			return "安装成功";
		}
		return "状�?�信息不正确";
	}
	private final class ListViewHolder {
    	public ImageView imageViewIcon;
    	public TextView appName;
    	public TextView dsize;
    	public TextView appinfo;
    	public TextView appSize;
    	public TextView download;
    	public ImageView imgSplit;
    	public ImageView imgDownLoad;
    	public TextView appPriceFlag;
    }
}
