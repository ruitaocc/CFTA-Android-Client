package com.ClassTransaction.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

import com.ClassTransaction.activity.R;
import com.ClassTransaction.client.exception.ClientException;
import com.ClassTransaction.commons.ClientAction;
import com.ClassTransaction.commons.Request;
import com.ClassTransaction.commons.Response;
import com.ClassTransaction.commons.ServerAction;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public  class Classt_ransactionActivity extends Activity {
    //** Called when the activity is first created. *//*
	Button login;
	EditText name;
	Button getusers;
	Button getcourses;
	static EditText content;
	EditText password;
	EditText ip;
	
	private User user;
	public static   Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {

			switch(msg.what){
			case 1:
			{
				Bundle data = msg.getData();
				String str = content.getText().toString()+"\n";
				content.setText(str+data.getString("user"));
				break;
			}
			case 2:
			{
				Bundle data = msg.getData();
				String str = content.getText().toString()+"\n";
				content.setText(str+data.getString("course"));
				break;
			}
			case 101:{
				content.setText("");
				break;
			}
			default :
			break;
		}
		super.handleMessage(msg);
	}
    	
    };;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.test);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
				detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().
				detectLeakedSqlLiteObjects().penaltyDeath().build());
        user = new User();
        login = (Button) this.findViewById(R.id.login);
        name = (EditText) this.findViewById(R.id.name);
        password = (EditText) this.findViewById(R.id.password);
        ip = (EditText) this.findViewById(R.id.ip);
        
       
        content = (EditText) this.findViewById(R.id.content);
        getusers = (Button) this.findViewById(R.id.getusers);
        getusers.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
	 			try {
	 				Request request = new Request("com.classtransaction.server.ServerResponse","com.class_transaction.client.ClientResponse");
	 				request.setParameter("type", "getusers");
	 				System.out.println("senting");
	 				PrintStream ps = new PrintStream(user.getSocket().getOutputStream(),true,"gb-2312");
	 				
	 				ps.println(request.toXML());
	 				System.out.println("sented");
	 			} catch (IOException e) {
					e.printStackTrace();
				}
			}});
        
        
        getcourses = (Button) this.findViewById(R.id.getcourses);
        getcourses.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
	 			try {
	 				//获取课程表请求
	 				Request request = new Request("com.classtransaction.server.ServerResponse","com.class_transaction.client.ClientResponse");
	 				request.setParameter("type", "getcourses");
	 				System.out.println("senting");
	 				//使用printstream可以指定编码格式
	 				PrintStream ps = new PrintStream(user.getSocket().getOutputStream(),true,"gb-2312");
	 				//使用mxl数据格式 方便解析
	 				ps.println(request.toXML());
	 				System.out.println("sented");
	 			} catch (IOException e) {
					e.printStackTrace();
				}
			}});
        login.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				login();
			}});
        
        
       
    }
    public void login(){
        
         //����������������� + �ͻ����ܷ��ص����� 
         //Request request = new Request("LoginFrame","LoginFrame");
         
         if (name.getText().toString().equals("")||password.getText().toString().equals("")) {
 			return;
 		}
 		//����User�ĸ�������
 		setUser();
 		//YCP连接
 		this.user.setSocket(createSocket(ip.getText().toString(), 12000));
 		if(this.user.getSocket().getPort()==12000){
 			System.out.println(12000+"created\n");
 		}
 		//�����߳�
 		ClientThread thread = new ClientThread(this.user);
 		thread.start();
 		//this.setVisible(false);
 		
 		
    }
    private Socket createSocket(String address, int port) {
		try {
			//����Socket
			System.out.println(address+"  123"+port);
			return new Socket(address, port);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    private void setUser() {
    	String strname = name.getText().toString();
        String strpassword = password.getText().toString();
		this.user.setUSER_NAME(strname);
		this.user.setPASS_WORD(strpassword);
	}
    
	
}