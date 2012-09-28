package com.ClassTransaction.client;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Message;

import com.ClassTransaction.activity.HomeActivity;
import com.ClassTransaction.activity.LoginActivity;
import com.ClassTransaction.commons.ClientAction;
import com.ClassTransaction.commons.Response;
import com.ClassTransaction.util.ApplicationContext;
import com.ClassTransaction.util.XStreamUtil;

public class ClientResponse implements ClientAction {

	public void execute(Response response) {
		String type =(String)response.getData("type");
		System.out.println(type);
		
		//clear
		/*Message mesg = new Message();
		mesg.what=101;
		Classt_ransactionActivity.handler.sendMessage(mesg);*/
		
		if(type.equals("getusers")){
			List<User> users = (List<User>) response.getData("users");
			for(User user:users){
				System.out.println(user.toString());
				Bundle data = new Bundle();
				data.putString("user", user.toString());
				Message mes = new Message();
				mes.setData(data);
				mes.what=1;
				Classt_ransactionActivity.handler.sendMessage(mes);
			}
		}
		if(type.equals("getcourses")){
			//清空课表
			Message mes = new Message();
			mes.what=HomeActivity.CLEAN_SCHEDULE;
			HomeActivity.handler.sendMessage(mes);
			//重置课表
			List<Course> courses = (List<Course>) response.getData("courses");
			for(Course course:courses){
				Bundle data = new Bundle();
				data.putString("course", XStreamUtil.toXML(course));
				Message mesg = new Message();
				mesg.setData(data);
				mesg.what=HomeActivity.UPDATE_SCHEDULE;
				HomeActivity.handler.sendMessage(mesg);
			}
		}
		if(type.equals("finduser")){
			User user= (User) response.getData("user");
			
			if(user!=null&&!user.getUSER_NAME().equals("")){
				ApplicationContext.gUser.setUSER_NAME(user.getUSER_NAME());
				ApplicationContext.gUser.setPASS_WORD(user.getPASS_WORD());
				
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message message = new Message();  
						message.what = LoginActivity.LOGIN_SUCCESS;
						LoginActivity.mHandler.sendMessage(message);
					}
				}, 10);	
				
			}else if(user==null){
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message message = new Message();  
						message.what = LoginActivity.LOGIN_FAILURE;
						LoginActivity.mHandler.sendMessage(message);
					}
				}, 10);	
			}
			
		}
		
		
	}

}
