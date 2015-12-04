package com.laanto.it.app.view;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.laanto.it.app.manager.ApplicationConstants;
import com.laanto.it.app.manager.ApplicationException;
import com.laanto.it.app.manager.ApplicationManager;
import com.laanto.it.app.manager.LogManager;
import com.laanto.it.app.swichlayout.BaseEffects;
import com.laanto.it.app.swichlayout.SwichLayoutInterFace;
import com.laanto.it.app.swichlayout.SwitchLayout;

public class NodeSwitchingActivity extends CordovaActivity implements CordovaInterface, SwichLayoutInterFace{

	private Intent intent;
	private String TAG = "NodeSwitchingActivity";
    /** Called when the activity is first created. */
    @Override 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.node_activity);
        super.init();
        // Load your application
        // launchUrl = "file:///android_asset/www/index.html"
	      Thread.setDefaultUncaughtExceptionHandler(ApplicationException
	  			.getAppExceptionHandler());
	        ApplicationManager.getAppManager().addActivity(this); 
    	intent = getIntent();
        loadUrl(ApplicationConstants.ANDROID_ASSET_WWW+""+intent.getStringExtra(ApplicationConstants.PATH_INTENT_URL));

//		setEnterSwichLayout();
    }

    

    @Override
    protected CordovaWebView makeWebView() {
    	SystemWebView webView  = (SystemWebView)findViewById(R.id.cordovaWebView);

        
        return new CordovaWebViewImpl(new SystemWebViewEngine(webView));
    }
    private CordovaPlugin activityResultCallback;
    
    

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
		     ApplicationManager.getAppManager().finishActivity();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

    @Override
    protected void createViews() {
        //Why are we setting a constant as the ID? This should be investigated
//        appView.getView().setId(100);
//        appView.getView().setLayoutParams(new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//
//        setContentView(appView.getView());

        if (preferences.contains("BackgroundColor")) {
            int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
            // Background of activity:
            appView.getView().setBackgroundColor(backgroundColor);
        }

        appView.getView().requestFocusFromTouch();
    }
    
    @Override
    public void setActivityResultCallback(CordovaPlugin plugin) {
        this.activityResultCallback = plugin;
    }

    @Override
    public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
        this.activityResultCallback = command;
        //this.activityResultKeepRunning = this.keepRunning;
        // If multitasking turned on, then disable it for activities that return results
        Log.i(TAG, command.toString());
        if (command != null) {
            //this.keepRunning = false;
        }
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	LogManager.i(TAG, "onActivityResult");

        CordovaPlugin callback = this.activityResultCallback;
        if (callback != null) {
            callback.onActivityResult(requestCode, resultCode, intent);
        }
        
        super.onActivityResult(requestCode, resultCode, intent);

    }

    @Override
    public ExecutorService getThreadPool() {
    	LogManager.i(TAG, "getThreadPool");

        return Executors.newCachedThreadPool();
    }

    @Override
    public Object onMessage(String id, Object data) {
        LogManager.i(TAG, "onMessage" +"id--"+id+"data--"+ data);

        return null;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

	@Override
	public void setEnterSwichLayout() {
			SwitchLayout.getSlideFromRight(this, false, null);

	}

	@Override
	public void setExitSwichLayout() {
			SwitchLayout.getSlideToRight(this, true, null);
	}

}

