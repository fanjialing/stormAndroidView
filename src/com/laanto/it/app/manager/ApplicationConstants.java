package com.laanto.it.app.manager;

/**
 * 
 * Application 相关配置
 *
 * @author fanjialing
 *
 * @date  2015-11-23
 *
 */
public class ApplicationConstants {
	//android asset defult url
	public static final String ANDROID_ASSET = "file:///android_asset/";
	//add www defult url
	public static final String ANDROID_ASSET_WWW = "file:///android_asset/www/";
	//main url
	public static final String ANDROID_ASSET_WWW_MAIN = "file:///android_asset/www/main.html";
	
	public static final String ANDROID_ASSET_WWW_DEFAULT = "file:///android_asset/www/default.html";

	
	/**********          Node  start  **************/
	public static final String NODE_MAIN = ".MainActivity";

	public static final String NODE_TWO = ".NodeActivity";
	
	public static final String NODE_LOGIN = ".LoginActivity";

	public static final String NODE_REG = ".RegActivity";
	
	public static final String NODE_FORGOT_PWD = ".ForgotPwdActivity";

	public static final String NODE_SWWITCH = ".NodeSwitchingActivity";

	public static final String NODE_REMOTE = ".RemoteView";
	
	public static final String NODE_ACCRED_ITATION = ".AccreditationActivity";

	
	/**********          Node  end   **************/

	
	
	/*******  WEBVIEW  ERROR  STATUS   start***********/
	
	// -10  无此协议
	public static final int ERROR_NO_SUCH_AGREEMENT = -10;
	// -6 无法连接到服务器
	public static final int ERROR_UNABLE_TO_CONNECT_TO_SERVER = -6;
	// -8服务器连接超时
	public static final int ERROR_SERVER_CONNECTION_TIMEOUT = -8;
	
	/*******  WEBVIEW  ERROR  STATUS   end***********/
	
	

	public static final String PATH_INTENT_URL = "url";
	public static final String PATH_INTENT_DATA = "data";
	public static final String PATH_INTENT_INFO = "info";

	
	
	
	/*****************guide page  start ****************************/
	public static final String GUIDE_1 = "file:///android_asset/www/guide1.html";
	public static final String GUIDE_2 = "file:///android_asset/www/guide2.html";
	public static final String GUIDE_3 = "file:///android_asset/www/guide3.html";
	/*****************guide page  end ****************************/


	/*************** config  key start **********************/
	public static final String LOG_DEBUG = "LogDebug";
	public static final String LOG_INFO = "LogInfo";
	public static final String LOG_ERROR = "LogError";
	public static final String GET_CURR_VERSION_URL = "GetCurrVersionUrl";

	/*************** config  key end**********************/
	
	
}
