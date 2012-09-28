package com.ClassTransaction.commons;

/**
 * 连接对象
 * 
 * @author ruitaocc@gmail.com
 */
public class Connection {

	//���ӵ�ַ
	private String address;
	
	//���Ӷ˿�
	private int port;
	
	public Connection(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
