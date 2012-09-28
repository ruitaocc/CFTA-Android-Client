package com.ClassTransaction.util;

import com.ClassTransaction.client.ClientThread;
import com.ClassTransaction.client.User;

public class ApplicationContext {
	//assume now is fourth week
	public static int gWeekOfTerm = 4;
	
	public static User gUser;
	public static ClientThread gClientThread;
	
	public static void setUser(String account, String password) {
		ApplicationContext.gUser.setUSER_NAME(account);
		ApplicationContext.gUser.setPASS_WORD(password);
	}

	
	static {
		gUser = new User();
		gClientThread = new ClientThread(gUser);
	}
}
