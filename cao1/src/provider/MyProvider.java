package provider;

import com.example1.cao1.db.SqlHelper;

import android.R.integer;
import android.R.string;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyProvider extends ContentProvider {

	private static final int book=0;
	private static final int book_item=1;

	
	private static final String path="com.example1.provider";
	private static UriMatcher  urimacher;
	
	private SqlHelper helper;
	static{
		urimacher=new UriMatcher(UriMatcher.NO_MATCH);
		urimacher.addURI(path, "people", book);
		urimacher.addURI(path, "people/#", book_item);
	}
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		SQLiteDatabase dbDatabase=helper.getReadableDatabase();
		int row=0;
		switch (urimacher.match(arg0)) {
		case book:
			row=dbDatabase.delete("people", arg1, arg2);
			break;
	case book_item:
			String pid=arg0.getPathSegments().get(1);
		row=dbDatabase.delete("people", "_id=?", new String[]{pid});
			break;
		default:
			break;
		}
		return row;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		switch (urimacher.match(arg0)) {
		case book:
		return "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.people";
		
	case book_item:
		return "vnd.android.cursor.item/vnd.com.example.databasetest.provider.people";
		
		default:
			break;
		}
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		SQLiteDatabase dbDatabase=helper.getReadableDatabase();
		Uri uri=null;
		long pid=dbDatabase.insert("people", null, arg1);
		uri=Uri.parse("content://"+path+"/people/"+pid);
		return uri;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		 helper=new SqlHelper(getContext(), "mydb.db", null, 1);
		return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		SQLiteDatabase dbDatabase=helper.getReadableDatabase();
		Cursor cursor=null;
		switch (urimacher.match(arg0)) {
		case book:
			cursor=dbDatabase.query("people", arg1, arg2, arg3, null, null,arg4);
			break;
	case book_item:
			String pid=arg0.getPathSegments().get(1);
		cursor=dbDatabase.query("people", arg1, "_id=?", new String[]{pid}, null, null,arg4);
			break;
		default:
			break;
		}
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
