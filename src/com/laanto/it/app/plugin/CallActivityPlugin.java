package com.laanto.it.app.plugin;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import com.laanto.it.app.manager.ApplicationConstants;
import com.laanto.it.app.manager.ApplicationManager;
import com.laanto.it.app.manager.LogManager;
import com.laanto.it.app.view.LoginActivity;
import com.laanto.it.app.view.MainActivity;
import com.laanto.it.app.view.NodeActivity;
import com.laanto.it.app.view.RemoteView;
import com.laanto.it.app.view.GuidePageActivity;

/**
 * Actitivy 之间的跳转 Call  Plugin
 * 
 * 目前提供在Javascript端跳转Activity的封装
 *
 * @author fanjialing
 *
 * @date  2015-11-23
 *
 */
public class CallActivityPlugin extends CordovaPlugin {

	public static final String ACTION_LOCAL = "callLocal";
	public static final String ACTION_REMOTE = "callRemote";
	public static final String ACTION_MAIN = "callMain";
	public static final String ACTION_LOGIN = "callLogin";
	public static final String ACTION_REG = "callReg";
	public static final String ACTION_FORGOT_PWD = "callForgotPwd";
	public static final String ACTION_ACCRED_ITATION = "callAccreditation";

	
	
	private String TAG = "CallActivityPlugin";

	
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		if (this.cordova.getActivity().isFinishing()){
			LogManager.d(TAG,"If the activity is finishing, returns true; else returns false.");
			return true;
		}
		LogManager.i(TAG, "[Action]"+"["+action+"]");
		
				LogManager.i(TAG, "[stack]"+"["+ApplicationManager.getAppManager().size()+"]");

		if (action.equals(ACTION_LOCAL)) {

			ActivityManager manager = (ActivityManager) this.cordova.getActivity().getSystemService(Context.ACTIVITY_SERVICE);    
			List<RunningTaskInfo> runningTasks = manager .getRunningTasks(1);    
			RunningTaskInfo cinfo = runningTasks.get(0);    
			ComponentName component = cinfo.topActivity;  
			String packageName =  cordova.getActivity().getPackageName();

			
			if((packageName+ApplicationConstants.NODE_MAIN).equals(component.getClassName())){
				startPage(callbackContext, ApplicationConstants.NODE_TWO,args);
			}else if((packageName+ApplicationConstants.NODE_TWO).equals(component.getClassName())){
				startPage(callbackContext, ApplicationConstants.NODE_SWWITCH,args);
			}else if((packageName+ApplicationConstants.NODE_SWWITCH).equals(component.getClassName())){
				startPage(callbackContext, ApplicationConstants.NODE_TWO,args);
			}


		}
		else
		if (action.equals(ACTION_REMOTE)) {
			startPage(callbackContext, ApplicationConstants.NODE_REMOTE,
					args);
		}
		
		else
		if(action.equals(ACTION_MAIN)){
			startPage(callbackContext, ApplicationConstants.NODE_MAIN,
					args);
		}
		else
		if(action.equals(ACTION_LOGIN)){
			startPage(callbackContext, ApplicationConstants.NODE_LOGIN,
					args);
//			String url = args.getString(0);
//			String data = args.getString(1);
//			LogManager.i(TAG,url);
//
//			FramentTest activity =  (FramentTest) this.cordova.getActivity();
//			activity.switchingLoginFrament();
//			PluginResult mPlugin = new PluginResult(
//					PluginResult.Status.NO_RESULT);
//			mPlugin.setKeepCallback(true);
//
//			callbackContext.sendPluginResult(mPlugin);
//
//			callbackContext.success("activity started.");
//			callbackContext.error("activity not start.");
//			return true;

		}else
		if(action.equals(ACTION_REG)){
			startPage(callbackContext, ApplicationConstants.NODE_REG,
					args);
		}
		else
			if(action.equals(ACTION_FORGOT_PWD)){
				startPage(callbackContext, ApplicationConstants.NODE_FORGOT_PWD,
						args);
		}
			else
				if(action.equals(ACTION_ACCRED_ITATION)){
					startPage(callbackContext, ApplicationConstants.NODE_ACCRED_ITATION,
							args);
				}
		
		return true;
	}

	public boolean startPage(CallbackContext callbackContext, String className,
			JSONArray args) {

		
		try {

			String url = args.getString(0);
			String data = args.getString(1);
			Intent intent = new Intent(cordova.getActivity(),
					Class.forName(cordova.getActivity().getPackageName()
							+ className));
			LogManager.i(TAG,"[go url] -->"+"["+url+"]");
			LogManager.i(TAG,"[go data] -->"+"["+data+"]");

			intent.putExtra(ApplicationConstants.PATH_INTENT_URL, url);
			intent.putExtra(ApplicationConstants.PATH_INTENT_DATA, data);
//
			this.cordova.getActivity().startActivityForResult( intent, 1);
			if(className.equals(ApplicationConstants.NODE_ACCRED_ITATION)){
				ApplicationManager.getAppManager().finishActivity();
			}

			LogManager.i(TAG,"[ go] -->"+"["+className+"]");

//			if(className.equals(ApplicationConstants.NODE_MAIN))
//			ApplicationManager.getAppManager().finishActivity();

			PluginResult mPlugin = new PluginResult(
					PluginResult.Status.NO_RESULT);
			mPlugin.setKeepCallback(true);

			callbackContext.sendPluginResult(mPlugin);

			callbackContext.success("activity started.");
			callbackContext.error("activity not start.");
		} catch (Exception ex) {
			ex.printStackTrace();
			LogManager.e(TAG, "",ex);
			return false;
		}
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch (resultCode) {
		case Activity.RESULT_OK:
			Bundle b = intent.getExtras();
			String str = b.getString("changge01");
			break;
		default:
			break;
		}
	}

	@Override
	public Boolean shouldAllowNavigation(String url) {
		return true;
	}

	@Override
	public Boolean shouldAllowRequest(String url) {
		return true;
	}
}
