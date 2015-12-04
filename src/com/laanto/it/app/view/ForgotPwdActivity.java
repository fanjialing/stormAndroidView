package com.laanto.it.app.view;
import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.LOG;
import org.apache.cordova.engine.SystemWebChromeClient;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.laanto.it.app.manager.ApplicationException;
import com.laanto.it.app.manager.ApplicationManager;
import com.laanto.it.app.manager.ApplicationConstants;
import com.laanto.it.app.manager.LogManager;
import com.laanto.it.app.swichlayout.BaseEffects;
import com.laanto.it.app.swichlayout.SwichLayoutInterFace;
import com.laanto.it.app.swichlayout.SwitchLayout;
import com.laanto.it.app.util.DialogUtils;


/**
 * LoginActivity 
 * 
 * @author fanjialing
 *
 */
public class ForgotPwdActivity extends BaseActivity  {
	private String TAG = "ForgotPwdActivity";
	private CordovaWebView cordovaWebView;
	private SystemWebView systemWebView;
	private Dialog mLoading;
	
	@Override
	protected void initData() {
		SystemWebViewEngine systemWebViewEngine = new SystemWebViewEngine(
				systemWebView);
		cordovaWebView = new CordovaWebViewImpl(systemWebViewEngine);
		cordovaWebView.init(new MyCordovaInterfaceImpl(this),
		parser.getPluginEntries(), parser.getPreferences());
//		cordovaWebView.loadUrl(ApplicationConstants.ANDROID_ASSET_WWW+"main.html");

		cordovaWebView.loadUrl(ApplicationConstants.ANDROID_ASSET_WWW+""+getIntent().getStringExtra(ApplicationConstants.PATH_INTENT_URL));
		systemWebView.setWebViewClient(new SystemWebViewClient(
				systemWebViewEngine) {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);				
				mLoading.hide();
			}

			@Override
			public void onPageStarted(WebView view, String url,
					Bitmap favicon) {
				super.onPageStarted(view, url, favicon);

			}


			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {

			}

		});
	}

	@Override
	protected void initRecourse(CordovaPreferences cordovaPreferences) {
		systemWebView = (SystemWebView) findViewById(R.id.cordovaWebView);		
	    mLoading = DialogUtils.createLoadingDialog(this);
	    mLoading.show();
	}
	@Override
	public void onDestroy() {
		cordovaWebView.stopLoading();
		cordovaWebView.handleStop();
		cordovaWebView.handleDestroy();
		mLoading.dismiss();
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
