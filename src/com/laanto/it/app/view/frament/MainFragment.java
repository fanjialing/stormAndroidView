package com.laanto.it.app.view.frament;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;
import com.laanto.it.app.manager.ApplicationConstants;
import com.laanto.it.app.view.R;
import com.laanto.it.app.view.R.id;
import com.laanto.it.app.view.R.layout;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class MainFragment extends Fragment {
	private CordovaWebView cordovaWebView;
	private SystemWebView systemWebView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		ConfigXmlParser parser = new ConfigXmlParser();
 		parser.parse(getActivity());
	    View rootView = inflater.inflate(R.layout.fragment_layout_main, container, false);
		systemWebView = (SystemWebView)rootView. findViewById(R.id.cordovaWebView);
 		SystemWebViewEngine systemWebViewEngine = new SystemWebViewEngine(
 				systemWebView);
 		cordovaWebView = new CordovaWebViewImpl(systemWebViewEngine);
 		cordovaWebView.init(new FragmentCordovaInterfaceImpl(getActivity()),
 				parser.getPluginEntries(), parser.getPreferences());// 初始化
 		cordovaWebView.loadUrl(ApplicationConstants.ANDROID_ASSET_WWW+"main.html");
    	
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
