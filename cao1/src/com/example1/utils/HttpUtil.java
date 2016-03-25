package com.example1.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example1.comment.LogUtil;

import android.R.string;
import android.content.Entity;
import android.util.Log;
import android.view.View.OnClickListener;

public class HttpUtil {

	
	public static void sendhttprequest(final String address, final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				LogUtil.i("mainactivity","id is nnnnnnnnn");
				// TODO Auto-generated method stub
				HttpURLConnection connection=null;
				try {
					URL url=new URL(address);
					connection=(HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					connection.setDoInput(true);
					connection.setDoOutput(true);
					InputStream in=connection.getInputStream();
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder res=new StringBuilder();
					String line;
					while ((line=reader.readLine())!=null) {

						res.append(line);
					}
					LogUtil.i("mainactivity","id is"+res.toString());
					if (listener!=null) {
						listener.onFinish(res.toString());
					}
				} catch (Exception e) {
					// TODO: handle exception
					if (listener!=null) {
						listener.error(e);
					}
				}
				finally{
					if (connection!= null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	public static void sendrequestHttpclient(final String path) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					HttpClient httpClient=new DefaultHttpClient();
					HttpGet httpGet=new HttpGet(path);
					HttpResponse httpResponse=httpClient.execute(httpGet);
				//	Log.i("mainactivity","id is"+httpResponse.toString());
					if (httpResponse.getStatusLine().getStatusCode()==200) {
						HttpEntity entity=httpResponse.getEntity();
						String	result=EntityUtils.toString(entity,"utf-8");
					//	parseXMLwithpull(result);
						parseSAX(result);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
	
	}
	
	private static void parseXMLwithpull(String xmldata) {
		try {
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser=factory.newPullParser();
			xmlPullParser.setInput(new StringReader(xmldata));
			int eventType=xmlPullParser.getEventType();
			String id="";
			String nameString="";
			String version="";
			while (eventType!=XmlPullParser.END_DOCUMENT) {

				String nodename=xmlPullParser.getName();
				switch (eventType) {
				//½âÎö½Úµã
				case XmlPullParser.START_TAG:
					if ("id".equals(nodename)) {
						id=xmlPullParser.nextText();
					}else if ("name".equals(nodename)) {
						nameString=xmlPullParser.nextText();
					}
					else if ("version".equals(nodename)) {
						version=xmlPullParser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					if ("app".equals(nodename)) {
						Log.i("mainactivity","id is"+id);
						Log.i("mainactivity","name is"+nameString);
						Log.i("mainactivity","version is"+version);
					}
				default:
					break;
				}
				eventType=xmlPullParser.next();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private static void parseSAX(String xmldata) {
		SAXParserFactory factory=SAXParserFactory.newInstance();
	
		try {
			XMLReader reader=factory.newSAXParser().getXMLReader();
			SAX handleSax=new SAX();
		
			reader.setContentHandler(handleSax);
			reader.parse(new InputSource(new StringReader(xmldata)));
			Log.i("mainactivity1","id is sax");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
