package com.laanto.it.app.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.laanto.it.app.manager.LogManager;

import android.content.Intent;
import android.util.Log;

/**
 * 提供进入网络设置界面的插件
 * 
 *
 * @author fanjialing
 *
 * @date  2015-11-23
 *
 */
public class NetworkSettingPlugin extends CordovaPlugin{
	public static final String ACTION = "netWorkSet";

	private String TAG = "NetworkSettingPlugin";
	
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		if(ACTION.equals("netWorkSet")){
			if(android.os.Build.VERSION.SDK_INT > 10 ){
				this.cordova.startActivityForResult(this, new Intent(android.provider.Settings.ACTION_SETTINGS), -1);
			} else {
				this.cordova.startActivityForResult(this, new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), -1);

			}
		}
		
		
		return true;
	}

}
