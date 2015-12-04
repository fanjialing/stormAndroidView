package com.laanto.it.app.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import com.laanto.it.app.manager.ApplicationConstants;
import com.laanto.it.app.manager.ApplicationException;
import com.laanto.it.app.manager.ApplicationManager;
import com.laanto.it.app.manager.LogManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

/**
 * app引导页
 * 
 * @author fanjialing
 *
 */
public class GuidePageActivity extends BaseActivity {
	private String TAG = "GuidePageActivity";

	private CordovaWebView cordovaWebView;
	private SystemWebView systemWebView;
	public IndexViewPager vpArticle;
	public MyPagerAdapter myAdapter;
	public static List<View> mListViews = new ArrayList<View>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public void addView(List<View> viewList, String url) {
		ConfigXmlParser parser = new ConfigXmlParser();
		parser.parse(this);
		systemWebView = new SystemWebView(this);
		SystemWebViewEngine systemWebViewEngine = new SystemWebViewEngine(
				systemWebView);
		cordovaWebView = new CordovaWebViewImpl(systemWebViewEngine);
		cordovaWebView.init(new MyCordovaInterfaceImpl(this),
				parser.getPluginEntries(), parser.getPreferences());// 初始化
		cordovaWebView.loadUrl(url);
		viewList.add(systemWebView);
	}

	public class MyPagerAdapter extends PagerAdapter {
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			LogManager.d(TAG, "destroyItem");
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
			LogManager.d(TAG, "finishUpdate");
		}

		@Override
		public int getCount() {
			LogManager.d(TAG, mListViews.size() + "");
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			LogManager.d(TAG, "instantiateItem");
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			LogManager.d("k", "isViewFromObject");
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			LogManager.d(TAG, "restoreState");
		}

		@Override
		public Parcelable saveState() {
			LogManager.d(TAG, "saveState");
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			LogManager.d(TAG, "startUpdate");
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			vpArticle.setCurrentItem(vpArticle.getCurrentItem() - 1);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void switchingView(String url) {
		// switchingHandler.sendEmptyMessageDelayed(mapViews.get(url),0);
	}

	/**
	 * * 设置ViewPager的滑动速度
	 * 
	 * */
	private void setViewPagerScrollSpeed() {
		try {
			Field mScroller = null;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					vpArticle.getContext());
			mScroller.set(vpArticle, scroller);
		} catch (NoSuchFieldException e) {

		} catch (IllegalArgumentException e) {

		} catch (IllegalAccessException e) {

		}
	}

	@Override
	protected void initData() {

		addView(mListViews, ApplicationConstants.GUIDE_1);
		addView(mListViews, ApplicationConstants.GUIDE_2);
		addView(mListViews, ApplicationConstants.GUIDE_3);
		myAdapter = new MyPagerAdapter();
		vpArticle.setAdapter(myAdapter);
	}

	@Override
	protected void initRecourse(CordovaPreferences cordovaPreferences) {
		vpArticle = (IndexViewPager) findViewById(R.id.viewpager);
		setViewPagerScrollSpeed();
		// vpArticle.setPageTransformer(true, new AccordionTransformer());

	}

	@Override
	public void onDestroy() {
		cordovaWebView.stopLoading();
		cordovaWebView.handleStop();
		cordovaWebView.handleDestroy();
		super.onDestroy();
	}
	
	@Override
	protected View initView() {
 	      Thread.setDefaultUncaughtExceptionHandler(ApplicationException
	  			.getAppExceptionHandler());
	        ApplicationManager.getAppManager().addActivity(this); 
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return inflater.inflate(R.layout.guidepage_layout, null);
	}
}
