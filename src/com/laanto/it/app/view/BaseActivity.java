package com.laanto.it.app.view;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaPreferences;

import com.laanto.it.app.manager.ApplicationConstants;
import com.laanto.it.app.manager.LogManager;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;

/**
 * 提供公共的Activity
 * 
 *
 * @author fanjialing
 *
 * @date  2015-11-23
 *
 */
public abstract class BaseActivity extends CordovaActivity {  
	  
	private String TAG = "BaseActivity";

	
    public Handler handler;  
  
    /** 初始化数据 */  
    protected abstract void initData();  
  
    /** 初始化资源 */  
    protected abstract void initRecourse(CordovaPreferences cordovaPreferences);  
  
    /** 初始化界面 */  
    protected abstract View initView();  
    
    public ConfigXmlParser parser=new ConfigXmlParser();
  
    /** 处理handler回传的信息 */  
    public void dispatchMessage(Message msg) {  
    }  
  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  
        
        parser.parse(this);
  
        CordovaPreferences cordovaPreferences = parser.getPreferences();
        
        LogManager.isDebug = cordovaPreferences.getBoolean(ApplicationConstants.LOG_DEBUG, true);
        LogManager.isError = cordovaPreferences.getBoolean(ApplicationConstants.LOG_ERROR, true);
        LogManager.isInfo = cordovaPreferences.getBoolean(ApplicationConstants.LOG_INFO, true);
        cordovaPreferences.getString(ApplicationConstants.GET_CURR_VERSION_URL, null);
  
        setContentView(initView());  
        initRecourse(cordovaPreferences); 
        initData();  
        

        handler = new Handler() {  
            public void dispatchMessage(Message msg) {  
                BaseActivity.this.dispatchMessage(msg);  
            }  
        };  
    }  
} 