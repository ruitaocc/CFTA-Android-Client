package com.ClassTransaction.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


import com.ClassTransaction.commons.ClientAction;
import com.ClassTransaction.commons.Response;
import com.ClassTransaction.util.XStreamUtil;

/**
 * 
 * 客户端引擎线程, 处理客户端接收服务器响应
 * 
 */
public class ClientThread extends Thread {

	private User user;
	
	private String line;
	
	private Map<String, ClientAction> actions = new HashMap<String, ClientAction>();
	
	public ClientThread(User user) {
		this.user = user;
	}
	
	public void run() {
		try {
			 InputStream is = this.user.getSocket().getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"gb-2312"));
			while ((this.line = br.readLine()) != null) {
				Response response = getResponse(this.line);
				//�õ��ͻ��˵Ĵ�����
				System.out.println(response.getActionClass());
				ClientAction action = getClientAction(response.getActionClass());
				//ִ�пͻ��˴�����
				action.execute(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//�õ���������Ӧ�еĿͻ��˴�����
	private ClientAction   getClientAction(String className) {
		try {
			if (actions.get(className) == null) {
				ClientAction action = (ClientAction)Class.forName(className).newInstance();
				actions.put(className, action);
			}
			return actions.get(className);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//����������Ӧ��xmlת����Response����
	private Response getResponse(String xml) {
		return (Response)XStreamUtil.fromXML(xml);
	}
}
