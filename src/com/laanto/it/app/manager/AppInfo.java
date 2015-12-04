package com.laanto.it.app.manager;

import android.graphics.drawable.Drawable;

/**
 * app 信息
 * 
 * @author fanjialing
 *
 * @date 2015年12月2日 上午10:59:59
 *
 */
public class AppInfo {
		
	// 版本名称
	private String versionName;
	//应用名称
	private String appName;
	//包名称
	private String packageName;
	//应用图标
	private Drawable drawable;
	
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Drawable getDrawable() {
		return drawable;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
	
	
	
	
}
