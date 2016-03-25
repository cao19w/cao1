package com.example1.cao1;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.R;
import com.example1.service.RuningService;
import com.example1.utils.HttpCallbackListener;
import com.example1.utils.HttpUtil;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;


public class Fragment4 extends Fragment implements HttpCallbackListener{
	
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private List<String> list1=new ArrayList<String>();
	private Button photoButton,selectButton,startservice,pull,sax,weather;
	private ImageView imageView;
	private Uri imageUri;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
       switch (requestCode) {
	case 1:
		if (resultCode==getActivity().RESULT_OK) {
			//剪切图片
			Intent intent =new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(imageUri,"image/*");
			intent.putExtra("scale", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, 2);
			
			
//			//载入图片
//			try {
//				Bitmap bitmap=BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
//				imageView.setImageBitmap(bitmap);
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
		}
		break;

	case 2:
		if (resultCode==getActivity().RESULT_OK) {
			//载入图片
			try {
				Bitmap bitmap=BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
				imageView.setImageBitmap(bitmap);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		break;
	}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment4, null);
		photoButton=(Button)view.findViewById(R.id.photo);
		selectButton=(Button)view.findViewById(R.id.selectphoto);
		startservice=(Button)view.findViewById(R.id.service1);
		imageView=(ImageView)view.findViewById(R.id.imageView1);
		listView=(ListView)view.findViewById(R.id.listView1);
		pull=(Button)view.findViewById(R.id.pull);
		sax=(Button)view.findViewById(R.id.sax);
		weather=(Button)view.findViewById(R.id.weather);
		weather.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent  intent=new Intent(getActivity(),ChooseAreaActivity.class);
				startActivity(intent);
			}
		});
		pull.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				HttpUtil.sendrequestHttpclient("http://192.168.0.26:8088/data.xml");
				HttpUtil.sendhttprequest("http://192.168.0.26:8088/data.xml",Fragment4.this);
			}
		});
		startservice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				Intent  intent=new Intent(getActivity(),RuningService.class);
				getActivity().startService(intent);
			}
		});
		photoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//创建file对象
				File outFile=new File(Environment.getExternalStorageDirectory(),"image.jpg");
				try {
					if (outFile.exists()) {
						outFile.delete();
					}
					outFile.createNewFile();
				} catch (Exception e) {
					// TODO: handle exception
				}
				imageUri=Uri.fromFile(outFile);
				Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, 1);
			}
		});
		selectButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//创建file对象
				File outFile=new File(Environment.getExternalStorageDirectory(),"image.jpg");
				try {
					if (outFile.exists()) {
						outFile.delete();
					}
					outFile.createNewFile();
				} catch (Exception e) {
					// TODO: handle exception
				}
				imageUri=Uri.fromFile(outFile);
				Intent intent=new Intent("android.intent.action.GET_CONTENT");
				intent.setType("image/*");
				intent.putExtra("scale", true);
				intent.putExtra("crop", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, 2);
			}
		});
		adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,list1);
		listView.setAdapter(adapter);
		Cursor cursor=null;
		try {
			cursor=getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
			while (cursor.moveToNext()) {
				String displaynameString=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				list1.add(displaynameString+":"+name);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if (cursor!=null) {
				cursor.close();
			}
		}
		return view;
	}
	@Override
	public void onFinish(String response) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void error(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
