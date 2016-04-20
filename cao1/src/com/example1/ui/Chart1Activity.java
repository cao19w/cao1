package com.example1.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;
import com.example1.bean.ChatInfo;
import com.example1.comment.LogUtil;
import com.example1.ui.adapter.ChatLVAdapter;
import com.example1.ui.adapter.FaceGVAdapter;
import com.example1.ui.adapter.FaceVPAdapter;
import com.example1.ui.view.DropdownListView;
import com.example1.ui.view.DropdownListView.OnRefreshListenerHeader;
import com.example1.ui.view.MyEditText;
import com.example1.utils.SharedPreferencesUtils;
import com.example1.utils.SystemUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

@SuppressLint("NewApi") public class Chart1Activity extends Activity implements OnClickListener,OnRefreshListenerHeader{
	private ViewPager mViewPager;
	private LinearLayout mDotsLayout;
	private MyEditText input;
	private Button send;
	private DropdownListView mListView;
	private ChatLVAdapter mLvAdapter;
	
	private LinearLayout chat_face_container;
	private ImageView image_face;//表情图标
	// 7列3行
	private int columns = 6;
	private int rows = 4;
	private List<View> views = new ArrayList<View>();
	private List<String> staticFacesList;
	private LinkedList<ChatInfo> infos = new LinkedList<ChatInfo>();
	private SimpleDateFormat sd;
	
	private String reply="";//模拟回复
    private LinearLayout boom;
	@SuppressLint("SimpleDateFormat")
	private void initViews() {
		mListView = (DropdownListView) findViewById(R.id.message_chat_listview);
		sd=new SimpleDateFormat("MM-dd HH:mm");
		//模拟收到信息
		infos.add(getChatInfoFrom("你好啊！"));
		infos.add(getChatInfoFrom("认识你很高兴#[face/png/f_static_018.png]#"));
		mLvAdapter = new ChatLVAdapter(this, infos);
		mListView.setAdapter(mLvAdapter);
		
		
		boom=(LinearLayout)this.findViewById(R.id.bottom);
		//表情图标
		image_face=(ImageView) findViewById(R.id.image_face);
		//表情布局
		chat_face_container=(LinearLayout) findViewById(R.id.chat_face_container);
		mViewPager = (ViewPager) findViewById(R.id.face_viewpager);
		mViewPager.setOnPageChangeListener(new PageChange());
		//表情下小圆点
		mDotsLayout = (LinearLayout) findViewById(R.id.face_dots_container);
		input = (MyEditText) findViewById(R.id.input_sms);
		input.setOnClickListener(this);
		send = (Button) findViewById(R.id.send_sms);
		InitViewPager();
		//表情按钮
		image_face.setOnClickListener(this);
		// 发送
		send.setOnClickListener(this);
		
		mListView.setOnRefreshListenerHead(this);
		mListView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if(arg1.getAction()==MotionEvent.ACTION_DOWN){
				
						chat_face_container.setVisibility(View.GONE);
						hideSoftInputView();
					isshow=0;
				}
				return false;
			}
		});
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtil.i("log", SharedPreferencesUtils.getShareData("user"));
//		if (SharedPreferencesUtils.getShareData("user")=="") {
//			//注册失败会抛出HyphenateException
//			try {
//				EMClient.getInstance().createAccount("cao150", "123456");
//				SharedPreferencesUtils.putShareData("user","cao150");
//				SharedPreferencesUtils.putShareData("pwd","123456");
//			} catch (HyphenateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}//同步方法
//		}else {
//			//登录
//			if (EMClient.getInstance().isLoggedInBefore()) {
//				
//			}else {
//				
//			}
//		}
		if (EMClient.getInstance().isLoggedInBefore()) {
			
		}
		SharedPreferencesUtils.putShareData("user","cao150");
		SharedPreferencesUtils.putShareData("pwd","123456");
		EMClient.getInstance().login("cao150", "123456", new EMCallBack() {

			@Override
			public void onSuccess() {
			

				// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
				// ** manually load all local groups and
			    EMClient.getInstance().groupManager().loadAllGroups();
			    EMClient.getInstance().chatManager().loadAllConversations();
			}

			@Override
			public void onProgress(int progress, String status) {
			
			}

			@Override
			public void onError(final int code, final String message) {
			
				runOnUiThread(new Runnable() {
					public void run() {
	
					}
				});
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		EMClient.getInstance().chatManager().addMessageListener(msgListener);
	
		
	
	}
	  @Override
	    public void onStop() {
	        super.onStop();
	        // unregister this event listener when this activity enters the
	        // background
	     //   EMClient.getInstance().chatManager().removeMessageListener(msgListener);
	    }
	  String startMsgId;
	EMMessageListener msgListener = new EMMessageListener() {
	 
		@Override
		public void onMessageReceived(List<EMMessage> messages) {
			//收到消息
			  for (EMMessage message : messages) {
				  String aString=message.getBody().toString();
				  try {
					JSONObject jsStr = new JSONObject("{"+aString+"}");
					aString=jsStr.getString("txt");
					Log.i("vvv", aString);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				  startMsgId=message.getMsgId();
				  infos.add(getChatInfoFrom(aString));
			
						mLvAdapter.setList(infos);
						mLvAdapter.notifyDataSetChanged();
						mListView.setSelection(infos.size() - 1);
			  }
		}
	 
		@Override
		public void onCmdMessageReceived(List<EMMessage> messages) {
			//收到透传消息
		}
	 
		@Override
		public void onMessageReadAckReceived(List<EMMessage> messages) {
			//收到已读回执
		}
	 
		@Override
		public void onMessageDeliveryAckReceived(List<EMMessage> message) {
			//收到已送达回执
		}
	 
		@Override
		public void onMessageChanged(EMMessage message, Object change) {
			//消息状态变动
		}
	};
	@Override
	public void onDestroy() {
		super.onDestroy();
		//EMClient.getInstance().chatManager().removeMessageListener(msgListener);
	}
    private void lockContainerHeight(int paramInt) {
        LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams) chat_face_container.getLayoutParams();
        localLayoutParams.height = paramInt;
        localLayoutParams.weight = 0.0F;
    }
	private void showview(boolean showAnimation) {
		
	    if (showAnimation) {
            transitioner.setDuration(200);
        } else {
            transitioner.setDuration(0);
        }
	    if (isshow==1) {
	    	hideEmotionView(true);
	    	isshow=3;
		}
		mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		 transitioner.setDuration(100);
		 emotionHeight = SystemUtils.getKeyboardHeight(this);
		  SystemUtils.hideSoftInput(input);
		  chat_face_container.getLayoutParams().height = emotionHeight;
		chat_face_container.setVisibility(View.VISIBLE);
		 getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	        //在5.0有navigationbar的手机，高度高了一个statusBar
	        int lockHeight = SystemUtils.getAppContentHeight(this);
//	            lockHeight = lockHeight - statusBarHeight;
//	        lockContainerHeight(lockHeight);
	        //5.0一下的手机
	        lockContainerHeight(emotionHeight);
	}
	 private void hideEmotionView(boolean showKeyBoard) {
	        
            if (showKeyBoard) {
            	mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
            	transitioner.setDuration(100);
                LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams) chat_face_container.getLayoutParams();
                localLayoutParams.height = chat_face_container.getTop();
                localLayoutParams.weight = 0.0F;
              //  chat_face_container.setVisibility(View.GONE);
                SystemUtils.showKeyBoard(input);
                lockContainerHeight(emotionHeight);
                
             //   getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                if (isshow==0) {
                	 getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				}else {
					getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
				}
                
          
            }
	        
	    }
	 private Integer isshow=0;//0键盘开始显示或未显示1表情开始显示2表情显示键盘隐藏3键盘显示表情隐藏
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.input_sms://输入框
			
				
				hideEmotionView(true);
				isshow=0;
			break;
		case R.id.image_face://表情
		//	hideSoftInputView();//隐藏软键盘
			if(isshow==0){
				
				showview(SystemUtils.isKeyBoardShow(this));
				isshow=1;
			}else if (SystemUtils.isKeyBoardShow(this)) {
				
				showview(SystemUtils.isKeyBoardShow(this));
				isshow=2;
			}else if (isshow==3) {
				showview(SystemUtils.isKeyBoardShow(this));
				isshow=2;
			}else{
				
				hideEmotionView(true);
				isshow=3;
			}
			break;
		case R.id.send_sms://发送
			reply=input.getText().toString();
			   EMMessage message = EMMessage.createTxtSendMessage(reply, "cao160");
			  EMClient.getInstance().chatManager().sendMessage(message);
			if (!TextUtils.isEmpty(reply)) {
				infos.add(getChatInfoTo(reply));
			//	mLvAdapter.setList(infos);
				mLvAdapter.notifyDataSetChanged();
				mListView.setSelection(infos.size() - 1);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						infos.add(getChatInfoFrom(reply));
						mLvAdapter.setList(infos);
						mLvAdapter.notifyDataSetChanged();
						mListView.setSelection(infos.size() - 1);
					}
				}, 1000);
				input.setText("");
				if((isshow==0)||(isshow==3)){
					 chat_face_container.setVisibility(View.GONE);
				
				}
			}
			break;

		default:
			break;
		}
	}

	/*
	 * 初始表情 *
	 */
	private void InitViewPager() {
		// 获取页数
		for (int i = 0; i < getPagerCount(); i++) {
			views.add(viewPagerItem(i));
			LayoutParams params = new LayoutParams(16, 16);
			mDotsLayout.addView(dotsItem(i), params);
		}
		FaceVPAdapter mVpAdapter = new FaceVPAdapter(views);
		mViewPager.setAdapter(mVpAdapter);
		mDotsLayout.getChildAt(0).setSelected(true);
	}

	private View viewPagerItem(int position) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.face_gridview, null);//表情布局
		GridView gridview = (GridView) layout.findViewById(R.id.chart_face_gv);
		/**
		 * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns *　rows　－　1; 空出最后一个位置给删除图标
		 * */
		List<String> subList = new ArrayList<String>();
		subList.addAll(staticFacesList
				.subList(position * (columns * rows - 1),
						(columns * rows - 1) * (position + 1) > staticFacesList
								.size() ? staticFacesList.size() : (columns
								* rows - 1)
								* (position + 1)));
		/**
		 * 末尾添加删除图标
		 * */
		subList.add("emotion_del_normal.png");
		FaceGVAdapter mGvAdapter = new FaceGVAdapter(subList, this);
		gridview.setAdapter(mGvAdapter);
		gridview.setNumColumns(columns);
		// 单击表情执行的操作
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				try {
					String png = ((TextView) ((LinearLayout) view).getChildAt(1)).getText().toString();
					if (!png.contains("emotion_del_normal")) {// 如果不是删除图标
						insert(getFace(png));
					} else {
						delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		return gridview;
	}

	private SpannableStringBuilder getFace(String png) {
		SpannableStringBuilder sb = new SpannableStringBuilder();
		try {
			/**
			 * 经过测试，虽然这里tempText被替换为png显示，但是但我单击发送按钮时，获取到輸入框的内容是tempText的值而不是png
			 * 所以这里对这个tempText值做特殊处理
			 * 格式：#[face/png/f_static_000.png]#，以方便判斷當前圖片是哪一個
			 * */
			String tempText = "#[" + png + "]#";
			sb.append(tempText);
			sb.setSpan(
					new ImageSpan(Chart1Activity.this, BitmapFactory
							.decodeStream(getAssets().open(png))), sb.length()
							- tempText.length(), sb.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb;
	}

	/**
	 * 向输入框里添加表情
	 * */
	private void insert(CharSequence text) {
		int iCursorStart = Selection.getSelectionStart((input.getText()));
		int iCursorEnd = Selection.getSelectionEnd((input.getText()));
		if (iCursorStart != iCursorEnd) {
			((Editable) input.getText()).replace(iCursorStart, iCursorEnd, "");
		}
		int iCursor = Selection.getSelectionEnd((input.getText()));
		((Editable) input.getText()).insert(iCursor, text);
	}

	/**
	 * 删除图标执行事件
	 * 注：如果删除的是表情，在删除时实际删除的是tempText即图片占位的字符串，所以必需一次性删除掉tempText，才能将图片删除
	 * */
	private void delete() {
		if (input.getText().length() != 0) {
			int iCursorEnd = Selection.getSelectionEnd(input.getText());
			int iCursorStart = Selection.getSelectionStart(input.getText());
			if (iCursorEnd > 0) {
				if (iCursorEnd == iCursorStart) {
					if (isDeletePng(iCursorEnd)) {
						String st = "#[face/png/f_static_000.png]#";
						((Editable) input.getText()).delete(
								iCursorEnd - st.length(), iCursorEnd);
					} else {
						((Editable) input.getText()).delete(iCursorEnd - 1,
								iCursorEnd);
					}
				} else {
					((Editable) input.getText()).delete(iCursorStart,
							iCursorEnd);
				}
			}
		}
	}

	/**
	 * 判断即将删除的字符串是否是图片占位字符串tempText 如果是：则讲删除整个tempText
	 * **/
	private boolean isDeletePng(int cursor) {
		String st = "#[face/png/f_static_000.png]#";
		String content = input.getText().toString().substring(0, cursor);
		if (content.length() >= st.length()) {
			String checkStr = content.substring(content.length() - st.length(),
					content.length());
			String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(checkStr);
			return m.matches();
		}
		return false;
	}

	private ImageView dotsItem(int position) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dot_image, null);
		ImageView iv = (ImageView) layout.findViewById(R.id.face_dot);
		iv.setId(position);
		return iv;
	}

	/**
	 * 根据表情数量以及GridView设置的行数和列数计算Pager数量
	 * @return
	 */
	private int getPagerCount() {
		int count = staticFacesList.size();
		return count % (columns * rows - 1) == 0 ? count / (columns * rows - 1)
				: count / (columns * rows - 1) + 1;
	}

	/**
	 * 初始化表情列表staticFacesList
	 */
	private void initStaticFaces() {
		try {
			staticFacesList = new ArrayList<String>();
			String[] faces = getAssets().list("face/png");
			//将Assets中的表情名称转为字符串一一添加进staticFacesList
			for (int i = 0; i < faces.length; i++) {
				staticFacesList.add(faces[i]);
			}
			//去掉删除图片
			staticFacesList.remove("emotion_del_normal.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 表情页改变时，dots效果也要跟着改变
	 * */
	class PageChange implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < mDotsLayout.getChildCount(); i++) {
				mDotsLayout.getChildAt(i).setSelected(false);
			}
			mDotsLayout.getChildAt(arg0).setSelected(true);
		}

	}

	/**
	 * 发送的信息
	 * @param message
	 * @return
	 */
	private ChatInfo getChatInfoTo(String message) {
		Log.i("vvv", message);
		ChatInfo info = new ChatInfo();
		info.content = message;
		info.fromOrTo = 1;
		info.time=sd.format(new Date());
		return info;
	}
	
	/**
	 * 接收的信息
	 * @param message
	 * @return
	 */
	private ChatInfo getChatInfoFrom(String message) {
		Log.i("vvv", message);
		ChatInfo info = new ChatInfo();
		info.content = message;
		info.fromOrTo = 0;
		info.time=sd.format(new Date());
		return info;
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				mLvAdapter.setList(infos);
				mLvAdapter.notifyDataSetChanged();
				//mListView.onRefreshCompleteHeader();
				break;
			}
		}
	};
	 private final LayoutTransition transitioner = new LayoutTransition();//键盘和表情切换
	    private int emotionHeight;
	    private RelativeLayout contentLay;
     @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_main);
		initStaticFaces();
		initViews();
		   /**上下平移动画**/
		contentLay=(RelativeLayout)this.findViewById(R.id.chat_main);
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "translationY",
                SystemUtils.getScreenHeight(this), emotionHeight).
                setDuration(transitioner.getDuration(LayoutTransition.APPEARING));
        transitioner.setAnimator(LayoutTransition.APPEARING, animIn);
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "translationY",
                emotionHeight,
                SystemUtils.getScreenHeight(this)).
                setDuration(transitioner.getDuration(LayoutTransition.DISAPPEARING));
        transitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
        contentLay.setLayoutTransition(transitioner);
        
       // EMClient.getInstance().chatManager().deleteConversation("cao160", true);
    	EMConversation conversation = EMClient.getInstance().chatManager().getConversation("cao160");
		//获取此会话的所有消息
		List<EMMessage> messages = conversation.getAllMessages();
		  for (EMMessage message : messages) {
			  String nameString=message.getFrom();
			  String aString=message.getBody().toString();
			  try {
				JSONObject jsStr = new JSONObject("{"+aString+"}");
				aString=jsStr.getString("txt");
				Log.i("vvv", aString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			  startMsgId=message.getMsgId();
			  if (nameString.trim().equals("cao160")) {
				  infos.add(getChatInfoFrom(aString));
			}else {
				 infos.add(getChatInfoTo(aString));
			}
		//	  infos.add(getChatInfoFrom(aString));
		
					mLvAdapter.setList(infos);
					mLvAdapter.notifyDataSetChanged();
					mListView.setSelection(infos.size() - 1);  
		  }
	}

	@Override
	public void onRefresh() {
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(1000);
					Message msg = mHandler.obtainMessage(0);
					mHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
