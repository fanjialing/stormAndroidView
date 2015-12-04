package com.laanto.it.app.manager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.util.Log;  

/**
 * 
 * Log 管理类
 * 
 * 提供对日志的统一管理
 * 
 * 对android 的 Log 类进行封装
 * 
 * 达到配置后方便使用的目的
 * 
 * @author fanjialing
 *
 * @date  2015-11-23
 *
 */
public class LogManager  
{  
    private static final String TAG = "BaoFengLogManager";  

	
    private LogManager()  
    {  
        /* cannot be instantiated */  
        throw new UnsupportedOperationException("cannot be instantiated");  
    }  
    /**是否开启debug模式*/
    public static boolean isDebug = true;
    /**是否开启异常日志打印*/
    public static boolean isError = true;
    /**是否开启info打印*/
    public static boolean isInfo = true;


  
    public static void i(String msg)  
    {  
        if (isInfo)  
            Log.i(TAG, msg);  
    }  
  
    public static void d(String msg)  
    {  
        if (isDebug)  
            Log.d(TAG, msg);  
    }  
  
    public static void e(String msg)  
    {  
        if (isError)  
            Log.e(TAG, msg);  
    }  
  
    public static void v(String msg)  
    {  
        if (isDebug)  
            Log.v(TAG, msg);  
    }  
    public static void w(String msg)  
    {  
        if (isDebug)  
            Log.v(TAG, msg);  
    }  
    public static void i(String tag, String msg)  
    {  
        if (isInfo)  
            Log.i(tag, msg);  
    }  
  
    public static void d(String tag, String msg)  
    {  
        if (isDebug)  
            Log.i(tag, msg);  
    }  
  
    public static void e(String tag, String msg)  
    {  
        if (isError)  
            Log.e(tag, msg);  
    }  
  
    public static void v(String tag, String msg)  
    {  
        if (isDebug)  
            Log.i(tag, msg);  
    }  
    public static void w(String tag, String msg)  
    {  
        if (isDebug)  
            Log.w(tag, msg);  
    } 
    
    public static void e(String tag,String msg,Throwable aThrowable)  
    {  
        if (isError)  
            Log.e(tag, msg,aThrowable);  
    }  
  
    
    
    
    /**
     * 将异常堆栈转换为字符串
     * 
     * @param aThrowable
     * 
     * @return String
     * 
     */
	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}
}  
