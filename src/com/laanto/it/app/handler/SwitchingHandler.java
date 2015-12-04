//package com.laanto.it.app.handler;
//
//import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
//import com.laanto.it.app.view.FramentTest;
//import com.laanto.it.app.view.R;
//import com.laanto.it.app.view.GuidePageActivity;
//import com.laanto.it.app.view.frament.LoginFragment;
//
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//public class SwitchingHandler extends Handler{
//	private String TAG = "SwitchingHandler";
//	
////	private TestActivity context;
//	private FramentTest context;
//
//	public SwitchingHandler(FramentTest context){ 
//		this.context = context;
//	}
//	
//	/* (non-Javadoc)
//	 * @see android.os.Handler#handleMessage(android.os.Message)
//	 */
//	@Override
//	public void handleMessage(Message msg) {
////		context.addView(TestActivity.mListViews, "file:///android_asset/www/login.html");
////		context.myAdapter.notifyDataSetChanged();
////		context.vpArticle.setCurrentItem(msg.what);
//		
////        Fragment secondFragment = new SlidingListFragmentRight();
////        FragmentManager fm = context.getFragmentManager();
////        FragmentTransaction fragmentTransaction = fm.beginTransaction();
////        FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(context, fragmentTransaction, context.mFirstFragment, secondFragment, R.id.fragment_place);
////        fragmentTransactionExtended.addTransition(context.optionSelected);
////        fragmentTransactionExtended.commit();
//		
//		Bundle bundleData = msg.getData();
//		int options = bundleData.getInt("options", 0);
//	
//		
//		switch (options) {
//		case 1:
//			break;
//		case 2:
//			Log.i(TAG, "------------");      
//			FragmentManager fm = context.getFragmentManager();
//			FragmentTransaction fragmentTransaction = fm.beginTransaction();
//			 fragmentTransaction.hide(context.loginFragment);
//		     fragmentTransaction.show(context.mainFragment);
//		     FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(context, fragmentTransaction, context.loginFragment, context.mainFragment, R.id.fragment_place);
//		     fragmentTransactionExtended.addTransition(context.optionSelected);
//		      fragmentTransaction.commitAllowingStateLoss();
//
//			break;	
//		case 3:
//		break;
//		default:
//			break;
//		}
//		
//
//     
////      fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
////      fragmentTransaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_in);
//     
//
//	}
//}
