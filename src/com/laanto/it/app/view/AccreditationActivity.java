package com.laanto.it.app.view;

import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

import com.laanto.it.app.manager.ApplicationConstants;
import com.laanto.it.app.manager.ApplicationException;
import com.laanto.it.app.manager.ApplicationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

/**
 * 保代资格认证活动页
 * 
 * @author fanjialing
 *
 * @date 2015年12月2日 下午4:32:22
 *
 */
public class AccreditationActivity extends BaseActivity{

	private String TAG = "LoginActivity";
	private CordovaWebView cordovaWebView;
	private SystemWebView systemWebView;
	@Override
	protected void initData() {
		SystemWebViewEngine systemWebViewEngine = new SystemWebViewEngine(
				systemWebView);
		cordovaWebView = new CordovaWebViewImpl(systemWebViewEngine);
		cordovaWebView.init(new MyCordovaInterfaceImpl(this),
		parser.getPluginEntries(), parser.getPreferences());
		cordovaWebView.loadUrl(ApplicationConstants.ANDROID_ASSET_WWW+""+getIntent().getStringExtra(ApplicationConstants.PATH_INTENT_URL));
	}

	@Override
	protected void initRecourse(CordovaPreferences cordovaPreferences) {
		systemWebView = (SystemWebView) findViewById(R.id.cordovaWebView);		
	}
	@Override
	public void onDestroy() {
		cordovaWebView.stopLoading();
		cordovaWebView.handleStop();
		cordovaWebView.handleDestroy();
		super.onDestroy();
	}
	@Override
	protected View initView() {
	      Thread.setDefaultUncaughtExceptionHandler(ApplicationException
	  			.getAppExceptionHandler());
	    ApplicationManager.getAppManager().addActivity(this); 
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(R.layout.node_activity, null);
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
		      ApplicationManager.getAppManager().finishActivity();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	

}
