package com.laanto.it.app.manager;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * 
 * Application管理类 提供对Activity的添加到堆栈 退出等一系列的管理
 * 
 * @author fanjialing
 *
 * @date  2015-11-23
 *
 */
public class ApplicationManager {
	private static String TAG = "ApplicationManager";

	private static Stack<Activity> activityStack;
	private static ApplicationManager instance;

	private ApplicationManager() {
	}

	/**
	 * 单一实例
	 */
	public static ApplicationManager getAppManager() {
		if (instance == null) {
			instance = new ApplicationManager();
		}
		return instance;
	}
	
	public  int size(){
		if (activityStack != null) {
			return activityStack.size();
		}
		return 0;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		if(activityStack!=null){
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void exitApp(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			android.os.Process.killProcess(android.os.Process.myPid());
		} catch (Exception e) {
		}finally {
			LogManager.d(TAG, "***********exitApp**************");
			System.exit(0);
		}
	}
}