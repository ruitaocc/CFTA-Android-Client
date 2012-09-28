package com.ClassTransaction.commons;

import java.util.HashMap;
import java.util.Map;

import com.ClassTransaction.util.XStreamUtil;



/**
 * request object,表示客户端向服务器的一次请求
 * 
 *@author ruitaocc@gmail.com
 */
public class Request {

	//参数
	private Map<String, Object> parameters;
	
	//服务器处理类
	private String serverActionClass;
	
	private String clientActionClass;

	public Request(String serverActionClass, String clientActionClass) {
		this.serverActionClass = serverActionClass;
		this.parameters = new HashMap<String, Object>();
		this.clientActionClass = clientActionClass;
	}
	
	public Map<String, Object> getParameters() {
		return this.parameters;
	}
	
	public void setParameter(String key, Object value) {
		this.parameters.put(key, value);
	}
	
	public Object getParameter(String key) {
		return this.parameters.get(key);
	}

	public String getServerActionClass() {
		return serverActionClass;
	}

	public void setServerActionClass(String serverActionClass) {
		this.serverActionClass = serverActionClass;
	}

	public String getClientActionClass() {
		return clientActionClass;
	}

	public void setClientActionClass(String clientActionClass) {
		this.clientActionClass = clientActionClass;
	}

	public String toXML() {
		return XStreamUtil.toXML(this);
	}
}
