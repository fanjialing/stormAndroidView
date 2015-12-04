package com.laanto.it.app.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.laanto.it.app.manager.ApplicationManager;
import com.laanto.it.app.view.NodeActivity;

import android.util.Log;

/**
 * 提供 html 页面上的 回退按钮 返回到上一个 页面
 * @author fanjialing
 *
 */
public class RetreatActivityPlugin extends CordovaPlugin {
	
    public static final String  ACTION = "Retreat";
    
    public String TAG = "RetreatActivityPlugin";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals(ACTION)) {
//            cordova.getActivity().finish();
		      ApplicationManager.getAppManager().finishActivity();
        }
        
        return true;
    }
}
