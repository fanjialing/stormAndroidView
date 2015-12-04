package com.laanto.it.app.handler;


import com.laanto.it.app.view.NodeActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author fanjialing
 *
 */
public class GoBackHandler extends Handler{
	
	private String TAG = "GoBackHandler";
	
	private NodeActivity context;
	
	public GoBackHandler(NodeActivity context){ 
		this.context = context;
	}
	
	/* (non-Javadoc)
	 * @see android.os.Handler#handleMessage(android.os.Message)
	 */
	@Override
	public void handleMessage(Message msg) {
		
		context.setExitSwichLayout();
	}
	
}
