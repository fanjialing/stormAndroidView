package com.laanto.it.app.manager;

import org.apache.cordova.CordovaPreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.android.a.a.b;
import com.laanto.it.app.dialog.SweetAlertDialog;
import com.laanto.it.app.service.UpdateService;
import com.laanto.it.app.util.HttpUtils;
import com.laanto.it.app.util.HttpUtils.CallBack;
import com.laanto.it.app.view.R;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * APP版本检测更新类 
 *  
 * @author fanjialing
 *
 * @date 2015年12月2日 上午10:59:18
 *
 */
public class ApplicationVersionCheck {

	public static String TAG = "ApplicationVersionCheck";
	/******* down APP address *******/
	// public static final String downUrl =
	// "http://gdown.baidu.com/data/wisegame/bd47bd249440eb5f/shenmiaotaowang2.apk";

	public Context context;

	private static ApplicationVersionCheck instance;

	private AppInfo appInfo;

	public static ApplicationVersionCheck getInstance(Context context) {
		if (instance == null) {
			instance = new ApplicationVersionCheck(context);
		}
		return instance;
	}

	public ApplicationVersionCheck(Context context) {
		this.context = context;
		appInfo = getAppInfo();
	}

	public void checkIsUpdate(final Handler versionHandler,
			CordovaPreferences cordovaPreferences) {
		// String
		// html="<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p>"
		// +"<p><a href=\"http://www.dreamdu.com/xhtml/\">超链接HTML入门</a>学习HTML!</p><p><font color=\"#aabb00\">颜色1"
		// +"</p><p><font color=\"#00bbaa\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>"
		// +
		// "下面是网络图片</p><img src=\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\"/></body></html>";
		final String appUrl = cordovaPreferences.getString(
				ApplicationConstants.GET_CURR_VERSION_URL, null);

		try {

			HttpUtils.doPostAsyn(appUrl, "version=" + appInfo.getVersionName(),
					new CallBack() {

						@Override
						public void onRequestComplete(String result) {
							try {
								JSONObject versionInfo = new JSONObject(result);
								String version = (String) versionInfo
										.get("version");
								if (!appInfo.getVersionName().equals(version)) {
									LogManager.i(TAG, "true");
									UpdateInfo updateInfo = new UpdateInfo();
									updateInfo.setUrl((String) versionInfo
											.get("url"));
									updateInfo.setVersion(version);
//									updateInfo
//											.setDescription((String) versionInfo
//													.get("description"));

									Message msg = Message.obtain();
									Bundle b = new Bundle();
									b.putSerializable("updateinfo", updateInfo);
									msg.setData(b);
									versionHandler.sendMessage(msg);
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取app 初始信息
	 * 
	 * @return
	 */
	public AppInfo getAppInfo() {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			String packageInfoString = info.packageName;
			String appName = info.applicationInfo.loadLabel(manager).toString();
			Drawable drawable = info.applicationInfo.loadIcon(manager);
			AppInfo appInfo = new AppInfo();
			appInfo.setVersionName(version);
			appInfo.setPackageName(packageInfoString);
			appInfo.setAppName(appName);
			appInfo.setDrawable(drawable);
			return appInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对app版本进行更新
	 * 
	 * @param updateInfo
	 */
	public void updateApp(final UpdateInfo updateInfo) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("<html>");
		buffer.append("<p>最新版本:2.2.2</p>");
		buffer.append("<p>版本大小:10MB</p>");
		buffer.append("<p>更新内容</p>");
		buffer.append("<p>xxxxxxxxxxxxxxxxxx</p>");
		buffer.append("<p>xxxxxxxxxxxxxxxxxx</p>");

		buffer.append("</html>");

		SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context,
				SweetAlertDialog.CUSTOM_IMAGE_TYPE);

		sweetAlertDialog.setTitleText("发现新版本");
		sweetAlertDialog.setContentText(buffer.toString());
		// sweetAlertDialog.setContentText("最新版本:\t2.1.2\t\t\n版本大小:\t10MB\t\t\t\n 更新内容: 此次更新了");

		sweetAlertDialog.setCancelText("以后再说");
		sweetAlertDialog.setConfirmText("立即更新");
		sweetAlertDialog.showCancelButton(true);

		sweetAlertDialog
				.setCancelClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								sDialog.dismiss();
							}
						})
				.setConfirmClickListener(
						new SweetAlertDialog.OnSweetClickListener() {
							@Override
							public void onClick(SweetAlertDialog sDialog) {
								/***** update service *******/
								Intent intent = new Intent(context,
										UpdateService.class);
								intent.putExtra("appName", appInfo.getAppName());
								intent.putExtra("downUrl", updateInfo.getUrl());
								context.startService(intent);
								sDialog.dismiss();

							}
						}).show();
	}
}
