package com.laanto.it.app.view;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Toast;

import com.laanto.it.app.manager.ApplicationException;
import com.laanto.it.app.manager.ApplicationManager;
import com.laanto.it.app.manager.ApplicationConstants;
import com.laanto.it.app.manager.ApplicationVersionCheck;
import com.laanto.it.app.manager.LogManager;
import com.laanto.it.app.manager.UpdateInfo;
import com.laanto.it.app.swichlayout.BaseEffects;
import com.laanto.it.app.swichlayout.SwichLayoutInterFace;
import com.laanto.it.app.swichlayout.SwitchLayout;
import com.laanto.it.app.util.DialogUtils;

/**
 *
 * 主Activity,放入主页
 *
 * @author fanjialing
 *
 * @date 2015-11-23
 *
 */
public class MainActivity extends BaseActivity implements SwichLayoutInterFace {
	private long exitTime = 0;
	private String TAG = "MainActivity";
	private CordovaWebView cordovaWebView;
	private SystemWebView systemWebView;
	private Dialog mloading;

	
	/**
	 * Handler:跳转到不同界�?
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1000:
				String data =   getIntent().getStringExtra("data");
				String info =   getIntent().getStringExtra("info");

				if(data!=null&&!data.equals("null")){
					LogManager.i(TAG, data);

					Intent intent = new Intent(MainActivity.this, NodeActivity.class);
					intent.putExtra(ApplicationConstants.PATH_INTENT_URL, data);
//					MainActivity.this.startActivity(intent);
					MainActivity.this.startActivityForResult(intent, 1);
				}else 		
					if(info!=null&&!info.equals("null")){
					LogManager.i(TAG, info);
					Intent intent = new Intent(MainActivity.this, MessageInfoActivity.class);
					intent.putExtra(ApplicationConstants.PATH_INTENT_URL, info);
					intent.putExtra(ApplicationConstants.PATH_INTENT_DATA, getIntent().getStringExtra("messageinfo"));
//					MainActivity.this.startActivity(intent);
					MainActivity.this.startActivityForResult(intent, 1);
				}
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}
	};
	
	/**
	 * Handler:跳转到不同界�?
	 */
	private Handler versionHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData(); 
	        Serializable obj = bundle.getSerializable("updateinfo");  
	        if(obj != null && obj instanceof UpdateInfo)  
	        {  
	        	UpdateInfo g = (UpdateInfo)obj;  
				ApplicationVersionCheck.getInstance(MainActivity.this).updateApp(g);
	        }  
			super.handleMessage(msg);
		}
	};
	
	@Override
	public void onDestroy() {
		cordovaWebView.stopLoading();
		cordovaWebView.handleStop();
		cordovaWebView.handleDestroy();
		mloading.dismiss();
		super.onDestroy();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}

	public void zeroEc() {
		int a = 0;
		int b = 1;
		int c = b / a;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		LogManager.i(TAG, System.currentTimeMillis() - exitTime + "--");

		
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), R.string.back_exit,
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				  ApplicationManager.getAppManager().exitApp(this);
			}
			return true;
		}
		// 获取 Menu键
		else if (keyCode == KeyEvent.KEYCODE_MENU) {
			LOG.i(TAG, "您点击了Menu");
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void initData() {
		SystemWebViewEngine systemWebViewEngine = new SystemWebViewEngine(
				systemWebView);
		cordovaWebView = new CordovaWebViewImpl(systemWebViewEngine);
		cordovaWebView.init(new MyCordovaInterfaceImpl(this),
				parser.getPluginEntries(), parser.getPreferences());// 初始化
		cordovaWebView.loadUrl(ApplicationConstants.ANDROID_ASSET_WWW+""+getIntent().getStringExtra(ApplicationConstants.PATH_INTENT_URL));
		systemWebView.setWebViewClient(new SystemWebViewClient(
				systemWebViewEngine) {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				mloading.hide();

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
		mHandler.sendEmptyMessageDelayed(1000, 0);

	}
	

	@Override
	protected void initRecourse(CordovaPreferences cordovaPreferences) {
		systemWebView = (SystemWebView) findViewById(R.id.cordovaWebView);
		mloading = DialogUtils.createLoadingDialog(this);
		mloading.show();
	    ApplicationVersionCheck.getInstance(this).checkIsUpdate(versionHandler,cordovaPreferences);

	}

	@Override
	protected View initView() {
   	      Thread.setDefaultUncaughtExceptionHandler(ApplicationException
	  			.getAppExceptionHandler());
//	        ApplicationManager.getAppManager().finishActivity(MainActivity.class);
	        ApplicationManager.getAppManager().addActivity(this); 
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(R.layout.node_activity, null);
	}

	@Override
	public void setEnterSwichLayout() {
			SwitchLayout.getSlideFromRight(this, false, null);
	}

	@Override
	public void setExitSwichLayout() {
			SwitchLayout.getSlideToRight(this, true, null);

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
