package com.ClassTransaction.util;


import com.ClassTransaction.client.Classt_ransactionActivity;
import com.ClassTransaction.client.ClientResponse;
import com.ClassTransaction.client.Course;
import com.ClassTransaction.client.User;
import com.ClassTransaction.commons.Request;
import com.ClassTransaction.commons.Response;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * XStream工具类, 用于xml与对象之间的转换
 * 
 * @author ruitaocc@gmail.com
 * 
 */
public class XStreamUtil {

	private static XStream xstream = new XStream(new DomDriver());
	static {
		 xstream.alias("com.class_transaction.commons.Request", Request.class);
	     xstream.alias("com.classtransaction.commons.Response", Response.class);
	     xstream.alias("com.class_transaction.client.Classt_ransactionActivity", Classt_ransactionActivity.class);
	     xstream.alias("com.classtransaction.model.User", User.class);
	     
	     
	     xstream.alias("com.class_transaction.client.ClientResponse", ClientResponse.class);
	     xstream.alias("com.classtransaction.model.Course", Course.class);
	     
	     
	     
	     
	}
	/**
	 * translate xml to object
	 * @param xml
	 * @return
	 */
	public static Object fromXML(String xml) {
       
		return xstream.fromXML(xml);
	}
	
	/**
	 * translate object to xml
	 * @param obj
	 * @return
	 */
	public static String toXML(Object obj) {
		String xml = xstream.toXML(obj);
		//去掉换行
		String a = xml.replaceAll("\n", "");
		String s = a.replaceAll("\r", "");
		return s;
	}

}
