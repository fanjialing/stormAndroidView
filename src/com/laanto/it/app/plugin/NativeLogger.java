package com.laanto.it.app.plugin;

import android.util.Log;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import com.laanto.it.app.manager.LogManager;

/**
 * 对JS端 console 函数进行深度封装后
 * 
 * 让Android端能够看到web console 的日志
 * 
 *
 * @author fanjialing
 *
 * @date  2015-11-23
 *
 */
public class NativeLogger extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
    	
        if (action.equals("info"))  {
            String tagName = data.getString(0);
            String message = data.getString(1);
            LogManager.i(tagName, message);
            callbackContext.success();
            return true;
        } else if (action.equals("debug")) {
            String tagName = data.getString(0);
            String message = data.getString(1);
            LogManager.d(tagName, message);
            callbackContext.success();
            return true;
        } else if (action.equals("error")) {
            String tagName = data.getString(0);
            String message = data.getString(1);
            LogManager.e(tagName, message); 
            callbackContext.success();
            return true;
        } 
        else if (action.equals("warn")) {
            String tagName = data.getString(0);
            String message = data.getString(1);
            LogManager.w(tagName, message);
            callbackContext.success();
            return true;
        }else if(action.equals("verbose")){
            String tagName = data.getString(0);
            String message = data.getString(1);
            LogManager.v(tagName, message);
            callbackContext.success();
            return true;
        } 
        else {
            return false;
        }
    }
}
