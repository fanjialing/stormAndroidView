package com.laanto.it.app.view;
import java.util.Map;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaPreferences;

import com.laanto.it.app.manager.ApplicationConstants;
import com.laanto.it.app.manager.ApplicationException;
import com.laanto.it.app.manager.ApplicationManager;
import com.laanto.it.app.manager.LogManager;
import com.laanto.it.app.swichlayout.BaseEffects;
import com.laanto.it.app.swichlayout.SwitchLayout;
import com.laanto.it.app.util.AnimationUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Splash 处理类
 * @author fanjialing
 *
 */
public class SplashActivity extends BaseActivity {
	
	private String TAG = "SplashActivity";
	// flag
	boolean isFirstIn = false;
    //first -> app
	private static final int GO_GUIDE = 1000;
	//no first -> app
	private static final int GO_MAIN = 1001;
	//splash time
	private static final long SPLASH_DELAY_MILLIS = 3000;

	private static final String SHAREDPREFERENCES_NAME = "first_pref";
	

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_GUIDE:
				Intent intent = new Intent(SplashActivity.this, GuidePageActivity.class);
				SplashActivity.this.startActivity(intent);
				SplashActivity.this.finish();

				break;
			case GO_MAIN:
				go("main.html");
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// 设置进入Activity的Activity特效动画，同理可拓展为布局动画
	
//		
//	      Thread.setDefaultUncaughtExceptionHandler(ApplicationException
//					.getAppExceptionHandler());
//		      ApplicationManager.getAppManager().addActivity(this); 
	}


	private void go(String path) {
		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		intent.putExtra(ApplicationConstants.PATH_INTENT_URL, path);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}

	@Override
	protected void initData() {
		SharedPreferences preferences = getSharedPreferences(
				SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		Log.i(TAG, isFirstIn + "");

		if (isFirstIn) {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
			Editor editor = preferences.edit();
	 		editor.putBoolean("isFirstIn", false);
	 		editor.commit();
		} else {
			mHandler.sendEmptyMessageDelayed(GO_MAIN, SPLASH_DELAY_MILLIS);
		}		
	}

	@Override
	protected void initRecourse(CordovaPreferences cordovaPreferences) {
		
	}

	@Override
	protected View initView() {
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		return inflater.inflate(R.layout.splash_layout, null);
	}
}