package com.ClassTransaction.commons;

import java.net.Socket;

/**
 * 服务器处理请求的接口
 * 
 *@author ruitaocc@gmail.com
 */
public interface ServerAction {

	void execute(Request request, Response response, Socket socket);
}
