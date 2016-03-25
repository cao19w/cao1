package com.example1.comment;

import android.util.Log;

public class LogUtil {
static final int verbose=1;
static final int debug=2;
static final int info=3;

static final int level=3;
public static void  v(String tag,String msg) {
	if (level<=verbose) {
		Log.v(tag, msg);
	}
}
public static void  d(String tag,String msg) {
	if (level<=debug) {
		Log.d(tag, msg);
	}
}
public static void  i(String tag,String msg) {
	if (level<=info) {
		Log.i(tag, msg);
	}
}
}
