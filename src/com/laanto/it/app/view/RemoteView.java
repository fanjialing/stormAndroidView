package com.laanto.it.app.view;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebChromeClient;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

import com.laanto.it.app.manager.ApplicationException;
import com.laanto.it.app.manager.ApplicationManager;
import com.laanto.it.app.manager.ApplicationConstants;
import com.laanto.it.app.manager.LogManager;
import com.laanto.it.app.swichlayout.SwichLayoutInterFace;
import com.laanto.it.app.swichlayout.SwitchLayout;
import com.laanto.it.app.util.DialogUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 加载远程页面的活动
 * 
 * @author fanjialing
 * 
 */
public class RemoteView extends BaseActivity {

	private String TAG = "RemoteView";
	private TextView textView;
	private CordovaWebView cordovaWebView;
	private SystemWebView systemWebView;
	private Timer mTimer;
	private String reloadUrl;
	private ProgressBar mProgressBar;
	private TimerTask mTimerTask;
	private int progressNum = 0;
	public void goback(View v) {
//		finish();
	      ApplicationManager.getAppManager().finishActivity();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			finish();
		      ApplicationManager.getAppManager().finishActivity();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	      Thread.setDefaultUncaughtExceptionHandler(ApplicationException
		  			.getAppExceptionHandler());
		        ApplicationManager.getAppManager().finishActivity(RemoteView.class);
		        ApplicationManager.getAppManager().addActivity(this); 
		super.onCreate(savedInstanceState);
	}
	

	@Override
	public void onDestroy() {
		cordovaWebView.stopLoading();
		cordovaWebView.handleStop();
		stopTimerTask();
		cordovaWebView.handleDestroy();
		// systemWebView.stopLoading();
		// systemWebView.clearHistory();
		super.onDestroy();
	}


	/**
	 *  任务停止
	 */
	private void stopTimerTask() {

		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}

	}

	@Override
	protected void initData() {
		SystemWebViewEngine systemWebViewEngine = new SystemWebViewEngine(
				systemWebView);
		cordovaWebView = new CordovaWebViewImpl(systemWebViewEngine);
		cordovaWebView.init(new MyCordovaInterfaceImpl(this),
				parser.getPluginEntries(), parser.getPreferences());// 初始化
		cordovaWebView.loadUrl(getIntent().getStringExtra(ApplicationConstants.PATH_INTENT_URL));
		StringBuilder  stringBuilder = new StringBuilder();
		stringBuilder.append(systemWebView.getSettings().getUserAgentString());
		stringBuilder.append(" baofeng/666.33");
		systemWebView.getSettings().setUserAgentString(stringBuilder.toString());
		systemWebView.setVerticalScrollBarEnabled(false); //垂直不显示
//		systemWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);//滚动条在WebView内侧显示
//		systemWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条在WebView外侧显示

		String str = systemWebView.getSettings().getUserAgentString();
		LogManager.i(TAG,str);
		systemWebView.setWebViewClient(new SystemWebViewClient(
				systemWebViewEngine) {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				Log.i(TAG, url);
				reloadUrl = url;
				LogManager.e(TAG, progressNum + "------完成页面");

				mProgressBar.setVisibility(View.GONE);
				stopTimerTask();
			}

			@Override
			public void onPageStarted(WebView view, String url,
					Bitmap favicon) {
				mProgressBar.setVisibility(View.VISIBLE);
				final Handler handler = new Handler();
				mTimer = new Timer();
				reloadUrl = url;
//				progressNum = view.getProgress();
				mTimerTask = new TimerTask() {
					@Override
					public void run() {
						LogManager.e(TAG, progressNum + "------连接超时");

						if (progressNum < 100) {
							LogManager.e(TAG, "连接超时");
							handler.post(runnable);
							stopTimerTask();
						}
					}
				};
				mTimer.schedule(mTimerTask, 40000);// 30最大链接时间为30秒

				super.onPageStarted(view, url, favicon);

			}

			Runnable runnable = new Runnable() {
				public void run() {
					cordovaWebView
							.loadUrl(ApplicationConstants.ANDROID_ASSET_WWW
									+ "wifi.html?reloadUrl=" + reloadUrl);
				}
			};

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {

				LogManager.e(TAG, "失败 url " + reloadUrl);
				LogManager.e(TAG, "失败 code " + errorCode);
				LogManager.e(TAG, "失败 description " + description);
				LogManager.e(TAG, "失败 failingUrl " + failingUrl);
				stopTimerTask();
				switch (errorCode) {
				case ApplicationConstants.ERROR_NO_SUCH_AGREEMENT:
					break;
				case ApplicationConstants.ERROR_SERVER_CONNECTION_TIMEOUT:
					break;
				case ApplicationConstants.ERROR_UNABLE_TO_CONNECT_TO_SERVER:
					break;
				default:
					break;
				}
				view.loadUrl(ApplicationConstants.ANDROID_ASSET_WWW
						+ "wifi.html?reloadUrl=" + reloadUrl);
				super.onReceivedError(view, errorCode, description, failingUrl);

			}

		});

		systemWebView.setWebChromeClient(new SystemWebChromeClient(
				systemWebViewEngine) {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				textView.setText(title);
				super.onReceivedTitle(view, title);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				LogManager.e(TAG, newProgress + "------加载情况");
				progressNum = newProgress;
				mProgressBar.setProgress(newProgress);
			}

		});
	}

	@Override
	protected void initRecourse(CordovaPreferences cordovaPreferences) {
		textView = (TextView) findViewById(R.id.title_tv);
		mProgressBar = (ProgressBar) this.findViewById(R.id.mProgress);
		systemWebView = (SystemWebView) findViewById(R.id.remote_view);
	}

	@Override
	protected View initView() {
//	      Thread.setDefaultUncaughtExceptionHandler(ApplicationException
//	  			.getAppExceptionHandler());
//	      ApplicationManager.getAppManager().addActivity(this); 
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(R.layout.remote_view_layout, null);
	}

}

class MyCordovaInterfaceImpl extends CordovaInterfaceImpl {
	private Activity mActivity;

	public MyCordovaInterfaceImpl(Activity activity) {
		super(activity);
		mActivity = activity;
	}

	@Override
	public Activity getActivity() {
		return mActivity;
	}
}