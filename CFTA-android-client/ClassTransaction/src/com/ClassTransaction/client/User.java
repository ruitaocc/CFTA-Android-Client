package com.ClassTransaction.client;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

import com.ClassTransaction.commons.ClientAction;
import com.ClassTransaction.commons.Response;


/**
 * common user model
 * @author Administrator
 *
 */

public class User{
	//ID+用户名+密码+学号+性别+出生年月+宿舍+籍贯+手机+短号+邮箱+是否删除
	//ID+USER_NAME+PASS_WORD+SNUMBER+GENDER+BIRTHDAY+ADDRESS+NATIVEPLACE+PHONE+SHORTPHONE+EMAIL+IS_DELETE;
	//删除时执行逻辑删除
	
	private String ID;
	//用户名称
	private String USER_NAME;
	
	//密码
	private String PASS_WORD;
	//学号
	private String SNUMBER;
	//性别
	private String GENDER;
	//出生日期
	private String BIRTHDAY;
	//宿舍
	private String ADDRESS;
	//籍贯
	private String NATIVE_PLACE;
	//手机
	private String PHONE;
	//短号
	private String SHORTPHONE;
	//邮箱
	private String EMAIL;
	
	//是否被删除, 0没有被删除, 1为已经删除
	private String IS_DELETE;

	
	
	
	
	
	//socket
	private Socket socket;
	
	
	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSERNAME) {
		USER_NAME = uSERNAME;
	}

	public String getPASS_WORD() {
		return PASS_WORD;
	}

	public void setPASS_WORD(String pASSWORD) {
		PASS_WORD = pASSWORD;
	}

	public String getSNUMBER() {
		return SNUMBER;
	}

	public void setSNUMBER(String sNUMBER) {
		SNUMBER = sNUMBER;
	}

	public String getGENDER() {
		return GENDER;
	}

	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public String getBIRTHDAY() {
		return BIRTHDAY;
	}

	public void setBIRTHDAY(String bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public String getNATIVE_PLACE() {
		return NATIVE_PLACE;
	}

	public void setNATIVE_PLACE(String nATIVEPLACE) {
		NATIVE_PLACE = nATIVEPLACE;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public String getSHORTPHONE() {
		return SHORTPHONE;
	}

	public void setSHORTPHONE(String sHORTPHONE) {
		SHORTPHONE = sHORTPHONE;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getIS_DELETE() {
		return IS_DELETE;
	}

	public void setIS_DELETE(String iSDELETE) {
		IS_DELETE = iSDELETE;
	}

	@Override
	public String toString() {
		return "User [ADDRESS=" + ADDRESS + ", BIRTHDAY=" + BIRTHDAY
				+ ", EMAIL=" + EMAIL + ", GENDER=" + GENDER + ", IS_DELETE="
				+ IS_DELETE + ", NATIVE_PLACE=" + NATIVE_PLACE + ", PASS_WORD="
				+ PASS_WORD + ", PHONE=" + PHONE + ", SHORTPHONE=" + SHORTPHONE
				+ ", SNUMBER=" + SNUMBER + ", USER_NAME=" + USER_NAME + "]";
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}


	

	

	
	
}
