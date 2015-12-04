//package com.laanto.it.app.view;
//import java.util.LinkedHashMap;
//
//import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
//import com.laanto.it.app.handler.SwitchingHandler;
//import com.laanto.it.app.view.frament.MainFragment;
//import com.laanto.it.app.view.frament.LoginFragment;
//import com.laanto.it.app.view.frament.RegFragment;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//public class FramentTest extends FragmentActivity  {
//	private static final String TAG = "FramentTest";
//    public int optionSelected = 0;
//    public int flag = 0;
//
//    public MainFragment mainFragment; 
//    public LoginFragment loginFragment;
//    public RegFragment regFragment;
//
//    public SwitchingHandler switchingHandler = new SwitchingHandler(FramentTest.this);
//    public static LinkedHashMap<Integer,Object> mapframent = new LinkedHashMap<Integer,Object>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//    	super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        
//        //Add first fragment
//        mainFragment = new MainFragment();
//        loginFragment = new LoginFragment();
//        regFragment = new RegFragment();
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.add(R.id.fragment_place, loginFragment);
//        fragmentTransaction.add(R.id.fragment_place, regFragment);
//        fragmentTransaction.add(R.id.fragment_place, mainFragment);
//        mapframent.put(3, regFragment);
//        mapframent.put(2, loginFragment);
//        mapframent.put(1, mainFragment);
//
//        
//        fragmentTransaction.commit();
//    }
//    
//    
//    public void switchingView(int options){
//    	Message message = Message.obtain();
//    	Bundle bundleData = new Bundle(); 
//    	bundleData.putInt("options", options);
//    	message.setData(bundleData);
//		switchingHandler.sendMessage(message);
//
//    }
//
//    
//    public void switchingLoginFrament(){
//    	flag =2;
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.hide(mainFragment);
//        fragmentTransaction.show(loginFragment);
//	    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//	    fragmentTransaction.setCustomAnimations(R.anim.fragment_left_enter, R.anim.fragment_pop_left_enter);
//        fragmentTransaction.commitAllowingStateLoss();
//    }
//    
//    
//    
//    public void switchingRegFrament(){
//    	flag =3;
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.hide(loginFragment);
//        fragmentTransaction.show(regFragment);
//	    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//	    fragmentTransaction.setCustomAnimations(R.anim.fragment_left_enter, R.anim.fragment_pop_left_enter);
//        fragmentTransaction.commitAllowingStateLoss();
//    }
//    
//    
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK
//				&& event.getAction() == KeyEvent.ACTION_DOWN) {
//		     Log.i(TAG, flag+"");
//		 	FragmentManager fm = getFragmentManager();
//			FragmentTransaction fragmentTransaction = fm.beginTransaction();
//		     	switch (flag) {
//				case 1:
//					finish();
//					break;
//				case 2:
//					fragmentTransaction.hide(loginFragment);
//					fragmentTransaction.show(mainFragment);
//				    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//				    fragmentTransaction.setCustomAnimations(R.anim.fragment_left_exit, R.anim.fragment_pop_left_exit);
//				    fragmentTransaction.commitAllowingStateLoss();
//				    flag --;
//					break;
//				case 3:
//					fragmentTransaction.hide(regFragment);
//					fragmentTransaction.show(loginFragment);
//				    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//				    fragmentTransaction.setCustomAnimations(R.anim.fragment_left_exit, R.anim.fragment_pop_left_exit);
//				    fragmentTransaction.commitAllowingStateLoss();
//				     flag --;
//					break;
//				default:
//					break;
//				}
//		     
//		     
//			}
//			return true;
//		}
//}
