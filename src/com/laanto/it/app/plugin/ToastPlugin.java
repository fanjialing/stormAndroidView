package com.laanto.it.app.plugin;


import android.util.Log;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import com.laanto.it.app.manager.ToastManager;

/**
 * 提供JS端调用 吐司
 * 
 *
 * @author fanjialing
 *
 * @date  2015-11-23
 *
 */
public class ToastPlugin extends CordovaPlugin {

    public static final String  ACTION = "toast";

    public String TAG = "ToastPlugin";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals(ACTION)) {
            Toast.makeText(cordova.getActivity(), args.getString(0), Toast.LENGTH_SHORT).show();
            ToastManager.show(cordova.getActivity(), args.getString(0),Toast.LENGTH_SHORT);
        }
        
        
        return true;
    }
}
