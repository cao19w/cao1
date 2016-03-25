package com.example1.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.R.string;
import android.util.Log;

public class SAX extends DefaultHandler{
	
	StringBuilder id;
	StringBuilder nameString;
	StringBuilder version;
	String nodename="";
@Override
public void startDocument() throws SAXException {
	// TODO Auto-generated method stub
//	super.startDocument();
	id=new StringBuilder();
	nameString=new StringBuilder();
	version=new StringBuilder();
}
@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
	//	super.startElement(uri, localName, qName, attributes);
		nodename=localName;
	}
@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
	//	super.characters(ch, start, length);
		if ("id".equals(nodename)) {
			id.append(ch,start,length);
		}else if ("name".equals(nodename)) {
			nameString.append(ch,start,length);
		}
		else if ("version".equals(nodename)) {
			version.append(ch,start,length);
		}
	}
@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
	//	super.endElement(uri, localName, qName);
		if ("app".equals(nodename)) {
			Log.i("mainactivity1","id is"+id);
			Log.i("mainactivity1","name is"+nameString);
			Log.i("mainactivity1","version is"+version);
		}
	}
@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
	//	super.endDocument();
	}
}
