package com.ClassTransaction.util;

/**
 * 检测网络状态
 * @author Tyscj
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class PingNetworkUtil{
	
	public static boolean PingNetwork(Context context){
		NetworkInfo localNetworkInfo = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
	    if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable())){
	    	if(localNetworkInfo.isConnected()){
	    		return true;
	    	}
	    	else{
	    		Toast.makeText(context, "���������", 1000).show();
	    		return false;
	    	}
	    }
	    else{
	    	Toast.makeText(context, "�����쳣", 1000).show();
	    	return false;
	    }
	}
}