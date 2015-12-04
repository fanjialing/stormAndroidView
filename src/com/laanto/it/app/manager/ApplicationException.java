package com.laanto.it.app.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.laanto.it.app.dialog.SweetAlertDialog;
import com.laanto.it.app.service.UpdateService;
import com.laanto.it.app.view.MainActivity;
import com.laanto.it.app.view.SplashActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

/**
 * 
 *  Application 全局异常崩溃捕获
 *  
 *  用于捕获APP崩溃后的异常信息,并提供友好界面与用户选择
 *  
 *  并且可以将异常日志发往服务器端便于研究异常原因
 *  
 * @author fanjialing
 *
 * @date  2015-11-23
 *
 */
public class ApplicationException extends Exception implements UncaughtExceptionHandler {

	private static final long serialVersionUID = -6262909398048670705L;
	
	private String TAG = "ApplicationException";

	private String message;

	private Thread.UncaughtExceptionHandler mDefaultHandler;
	
	// 用来存储设备信息和异常信息
	private Map<String, String> infos = new HashMap<String, String>();

	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

	private ApplicationException() {
		super();
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	public ApplicationException(String message, Exception excp) {
		super(message, excp);
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取APP异常崩溃处理对象
	 * 
	 * @param context
	 * @return
	 */
	public static ApplicationException getAppExceptionHandler() {
		return new ApplicationException();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		}

	}

	/**
	 * 自定义异常处理
	 * 
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}

		final Activity activity = ApplicationManager.getAppManager().currentActivity();

		if (activity == null) {
			return false;
		}
		String logs = getStackTrace(ex);
		LogManager.e(TAG, logs);
		
		collectDeviceInfo(activity);
		saveCrashInfo2File(activity,ex);
		
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(activity, "程序异常", Toast.LENGTH_SHORT).show();
				/*
				new AlertDialog.Builder(activity).setTitle("提示")
						.setCancelable(false).setMessage("发生异常情况,系统面临结束")
						.setNegativeButton("重启应用", new OnClickListener(){

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							    Intent intent = new Intent(activity,SplashActivity.class);  
						        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
						        activity.startActivity(intent);  
								ApplicationManager.getAppManager().exitApp(activity);
						        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前 
							}
							 
						})
						.setNeutralButton("结束程序", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, 
									int which) {
								  ApplicationManager.getAppManager().exitApp(activity);
						
							}
						}).create().show();
			*/
				
				StringBuffer buffer = new StringBuffer();
				buffer.append("<html>");
				buffer.append("<p>应用程序发生未知异常</p>");
				buffer.append("<p>应用程序即将崩溃</p>");

				buffer.append("</html>");

				SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity,
						SweetAlertDialog.CUSTOM_IMAGE_TYPE);
				
				sweetAlertDialog.setTitleText("程序发生异常");
				sweetAlertDialog.setContentText(buffer.toString());
				// sweetAlertDialog.setContentText("最新版本:\t2.1.2\t\t\n版本大小:\t10MB\t\t\t\n 更新内容: 此次更新了");

				sweetAlertDialog.setCancelText("关闭应用");
				sweetAlertDialog.setConfirmText("重启应用");
				sweetAlertDialog.showCancelButton(true);
				

				
				sweetAlertDialog
						.setCancelClickListener(
								new SweetAlertDialog.OnSweetClickListener() {
									@Override
									public void onClick(SweetAlertDialog sDialog) {
//										sDialog.dismiss();
										  ApplicationManager.getAppManager().exitApp(activity);
									}
								})
						.setConfirmClickListener(
								new SweetAlertDialog.OnSweetClickListener() {
									@Override
									public void onClick(SweetAlertDialog sDialog) {
										    Intent intent = new Intent(activity,SplashActivity.class);  
									        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
									        activity.startActivity(intent);  
											ApplicationManager.getAppManager().exitApp(activity);
									        android.os.Process.killProcess(android.os.Process.myPid()); 
									}
								}).show();
				Looper.loop();
			}
		}.start();

		return true;
	}
	
	
	
	/**
	 * 收集设备参数信息
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			LogManager.e(TAG, "收集设备信息时:an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				LogManager.d(TAG, field.getName() + " : " + field.get(null));
			} catch (Exception e) {
				LogManager.e(TAG, "收集设备信息时:an error occured when collect crash info", e);
			}
		}
	}
	
    /** 
     * 保存错误信息到文件中 
     *  
     * @param ex 
     * @return  返回文件名称,便于将文件传送到服务器 
     */  
    private String saveCrashInfo2File(Context ctx,Throwable ex) {  
          
        StringBuffer sb = new StringBuffer();  
        for (Map.Entry<String, String> entry : infos.entrySet()) {  
            String key = entry.getKey();  
            String value = entry.getValue();  
            sb.append(key + "=" + value + "\n");  
        }  
          
        Writer writer = new StringWriter();  
        PrintWriter printWriter = new PrintWriter(writer);  
        ex.printStackTrace(printWriter);  
        Throwable cause = ex.getCause();  
        while (cause != null) {  
            cause.printStackTrace(printWriter);  
            cause = cause.getCause();  
        }  
        printWriter.close();  
        String result = writer.toString();  
        sb.append(result);  
        try {  
            long timestamp = System.currentTimeMillis();  
            String time = formatter.format(new Date());  
            String fileName = "crash-" + time + "-" + timestamp + ".log";  
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { 
            	//sdk 外部存储
//                String path = "/sdcard/crash/"; 
                //内部存储路径
                String path = ctx.getFilesDir()+"/crash/";
                File dir = new File(path);  
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
                FileOutputStream fos = new FileOutputStream(path + fileName);  
                fos.write(sb.toString().getBytes());  
                fos.close();  
            }  
            return fileName;  
        } catch (Exception e) {  
            LogManager.e(TAG, "写入文件:an error occured while writing file...", e);  
        }  
        return null;  
    }  
	/**
	 * 将异常堆栈转换为字符串
	 * 
	 * @param aThrowable
	 *            异常
	 * @return String
	 */
	public String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}
}
