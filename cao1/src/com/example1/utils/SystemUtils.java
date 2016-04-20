package com.example1.utils;
import com.example1.comment.MyApplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/**
 * Created by xy on 15/12/23.
 */
public class SystemUtils {
    public static int getKeyboardHeight(Activity paramActivity) {

        int height = SystemUtils.getScreenHeight(paramActivity) - SystemUtils.getStatusBarHeight(paramActivity)
                - SystemUtils.getAppHeight(paramActivity);
        if (height == 0) {
            height = SharedPreferencesUtils.getIntShareData("KeyboardHeight", 787);//787涓洪粯璁よ蒋閿洏楂樺害 鍩烘湰宸笉绂�
        }else{
            SharedPreferencesUtils.putIntShareData("KeyboardHeight", height);
        }
        return height;
    }
    /**灞忓箷鍒嗚鲸鐜囬珮**/
    public static int getScreenHeight(Activity paramActivity) {
        Display display = paramActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }
    /**statusBar楂樺害**/
    public static int getStatusBarHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.top;

    }
    /**鍙灞忓箷楂樺害**/
    public static int getAppHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }
    /**鍏抽棴閿洏**/
    public static void hideSoftInput(View paramEditText) {
        ((InputMethodManager) MyApplication.getcontext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
    }
    // below actionbar, above softkeyboard
    public static int getAppContentHeight(Activity paramActivity) {
        return SystemUtils.getScreenHeight(paramActivity) - SystemUtils.getStatusBarHeight(paramActivity)
                - SystemUtils.getActionBarHeight(paramActivity) - SystemUtils.getKeyboardHeight(paramActivity);
    }
    /**鑾峰彇actiobar楂樺害**/
    public static int getActionBarHeight(Activity paramActivity) {
        if (true) {
            return SystemUtils.dip2px(56);
        }
        int[] attrs = new int[] { android.R.attr.actionBarSize };
        TypedArray ta = paramActivity.obtainStyledAttributes(attrs);
        return ta.getDimensionPixelSize(0, SystemUtils.dip2px(56));
    }
    /**dp杞琾x**/
    public static int dip2px(int dipValue) {
        float reSize =  MyApplication.getcontext().getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }
    /**閿洏鏄惁鍦ㄦ樉绀�**/
    public static boolean isKeyBoardShow(Activity paramActivity) {
        int height = SystemUtils.getScreenHeight(paramActivity) - SystemUtils.getStatusBarHeight(paramActivity)
                - SystemUtils.getAppHeight(paramActivity);
        return height != 0;
    }
    /**鏄剧ず閿洏**/
    public static void showKeyBoard(final View paramEditText) {
        paramEditText.requestFocus();
        paramEditText.post(new Runnable() {
            @Override
            public void run() {
                ((InputMethodManager)  MyApplication.getcontext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(paramEditText, 0);
            }
        });
    }
}
