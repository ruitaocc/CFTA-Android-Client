package com.ClassTransaction.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;

import com.ClassTransaction.activity.LoginActivity;
import com.ClassTransaction.activity.R;
import com.ClassTransaction.client.ClientThread;
import com.ClassTransaction.client.User;
import com.ClassTransaction.commons.Request;
import com.ClassTransaction.util.ApplicationContext;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Message;
import android.os.StrictMode;
import android.widget.Toast;

public class WebLogin {

	//public static String mSess;
	
	Context mContext;
	boolean logined = false;
	public  boolean logIn(Context context, String account, String password) {

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
				detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().
				detectLeakedSqlLiteObjects().penaltyDeath().build());
		//空值检查     后期需进行格式检查 防范sql注入攻击
		if (account.toString().equals("")||password.toString().equals("")) {
			Toast.makeText(context, context.getString(R.string.loginfail), 500);
			return false;
		}
		this.mContext = context;
		ApplicationContext.setUser(account, password);

		new LoginTask().execute(account, password);

		return true;


	}

	private  class LoginTask  extends AsyncTask<Object, Object, User>{
		protected void onPostExecute(User user) {
			// TODO Auto-generated method stub
			//get the session
			try {
				if(user != null){
					if(user.getSocket() == null){
						Toast.makeText(mContext, "连接失败", 1000).show();
					}
					else if (user.getSocket().getPort()!=12000){
						Toast.makeText(mContext, "连接失败", 1000).show();
					}
				}
				
			}catch(Exception e){
				Toast.makeText(mContext, "error", 1000).show();
			}
		}
		protected User doInBackground(Object... params) {
			try {
				String account = (String)params[0];
				String password = (String)params[1];
				//ApplicationContext.setUser(account, password);
				SharedPreferences preferences = mContext.getSharedPreferences("settings", Activity.MODE_PRIVATE);
				ApplicationContext.gUser.setSocket(
						createSocket(preferences.getString("IP", mContext.getResources().getString(R.string.defaultIP))
						, 12000));
				//启动线程
				ApplicationContext.gClientThread= new ClientThread(ApplicationContext.gUser);
				ApplicationContext.gClientThread.start();
				//联机成功检测账号密码
				try {
	 				Request request = new Request("com.classtransaction.server.ServerResponse","com.ClassTransaction.client.ClientResponse");
	 				request.setParameter("type", "finduser");
	 				request.setParameter("account", account);
	 				request.setParameter("password", password);
	 				System.out.println("senting");
	 				PrintStream ps = new PrintStream(ApplicationContext.gUser.getSocket().getOutputStream(),true,"gb-2312");
	 				
	 				ps.println(request.toXML());
	 				System.out.println("sented");
	 			} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				Toast.makeText(mContext, "登录失败", 1000).show();
				e.printStackTrace();
			}
			return ApplicationContext.gUser;
		}
	}




	private Socket createSocket(String address, int port) {

		//创建Socket
		Socket socket = new Socket();
		try {
			//InetAddress temp_InetAddr =InetAddress.getByName(address);
		
			 //socket = new Socket(temp_InetAddr,12000);
		
		
			SocketAddress serverAddr = new InetSocketAddress(address,port); //获取sockaddress对象
			socket.connect(serverAddr,10000);
			
			return socket;
		} catch (UnknownHostException e) {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message message = new Message();  
					message.what = LoginActivity.LOGIN_UNCONNECT;
					 LoginActivity.mHandler.sendMessage(message);
				}
			}, 10);	
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Message message = new Message();  
					message.what = LoginActivity.LOGIN_UNCONNECT;
					LoginActivity.mHandler.sendMessage(message);
				}
			}, 10);	
		}
		return socket;
	}
}

