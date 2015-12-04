package com.laanto.it.app.view.frament;

import org.apache.cordova.CordovaInterfaceImpl;

import android.app.Activity;

public class FragmentCordovaInterfaceImpl extends CordovaInterfaceImpl {
	private Activity mActivity;

	public FragmentCordovaInterfaceImpl(Activity activity) {
		super(activity);
		mActivity = activity;
	}

	@Override
	public Activity getActivity() {
		return mActivity;
	}
}
