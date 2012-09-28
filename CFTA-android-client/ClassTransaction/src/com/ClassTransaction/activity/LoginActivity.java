package com.ClassTransaction.activity;

import java.io.IOException;
import com.ClassTransaction.Service.WebLogin;
import com.ClassTransaction.util.ApplicationContext;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LoginActivity extends Activity {
	
	private EditText mAccount;
	private EditText mPassword;
	private Button mLogin;
	private Button mOffLine;
	private CheckBox mKeepPassword;
	private CheckBox mAutoLogin;
	private ImageView mIPSetting;
	public static final int LOGIN_SUCCESS = 111;
	public static final int LOGIN_FAILURE = 112;
	public static final int LOGIN_UNCONNECT = 113;
	public static Handler mHandler;
	
	ProgressDialog mPd;
	//sharepreference storing the ip
	private SharedPreferences mPreferences;
	private SharedPreferences.Editor mEditor;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
				detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().
				detectLeakedSqlLiteObjects().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		//添加界面切换效果，注意只有Android的2.0(SdkVersion版本号为5)以后的版本才支持  
		int version = Integer.valueOf(android.os.Build.VERSION.SDK);
		if(version  >= 5) {     
		     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		}
		
		InitialWidget();
		AddListener();
	}

	private void InitialWidget() {

		this.mAccount = (EditText) findViewById(R.id.account_edit);
		this.mPassword = (EditText) findViewById(R.id.password_edit);
		this.mKeepPassword = (CheckBox)findViewById(R.id.checkBox_keepword);
		this.mAutoLogin = (CheckBox)findViewById(R.id.checkBox_autosign);
		
		this.mLogin = (Button) findViewById(R.id.login_btn);
		this.mOffLine = (Button) findViewById(R.id.offline_btn);
		
		this.mIPSetting = (ImageView)findViewById(R.id.ip_setting);
	
		this.mPreferences = getSharedPreferences("settings", Activity.MODE_PRIVATE);
		this.mEditor = this.mPreferences.edit();
		
		this.mAccount.setText(this.mPreferences.getString("account", ""));
		this.mPassword.setText(this.mPreferences.getString("password", ""));
		this.mKeepPassword.setChecked(this.mPreferences.getBoolean("KeepPassword", false));
		this.mAutoLogin.setChecked(this.mPreferences.getBoolean("AutoLogin", false));
	}

	private void AddListener() {
		this.mLogin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPd = new ProgressDialog(LoginActivity.this);
				mPd.setTitle("Sign In ...");
				mPd.setMessage("Sign In ...");
				mPd.setCancelable(false);
				mPd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				mPd.setIndeterminate(false);
				mPd.show();
				new WebLogin().logIn(LoginActivity.this, mAccount.getText()
						.toString(), mPassword.getText().toString());
			}
		});

		this.mOffLine.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						HomeActivity.class);
				intent.putExtra("MODE", HomeActivity.OFFLINE_MODE);
				startActivity(intent);
				//LoginActivity.this.finish();
			}
		});
		
		this.mAutoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					mKeepPassword.setChecked(true);
				}
			}
		});
		
		this.mKeepPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked == false && mAutoLogin.isChecked()){
					mAutoLogin.setChecked(false);
				}
			}
		});

		//handler some request, like go to the requested page
		mHandler = new Handler(){

			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				mPd.dismiss();
				switch(msg.what){
				case LoginActivity.LOGIN_SUCCESS:
					Toast.makeText(LoginActivity.this,getString(R.string.login_success), 800).show();
					Intent intent = new Intent(LoginActivity.this,
							HomeActivity.class);
					intent.putExtra("MODE", HomeActivity.LOGIN_MODE);
					startActivity(intent);
					LoginActivity.this.finish();
					break;
				case LoginActivity.LOGIN_FAILURE:
					Toast.makeText(LoginActivity.this,getString(R.string.account_error), 800).show();
					try {
						ApplicationContext.gUser.getSocket().close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ApplicationContext.gClientThread.interrupt();
					break;
				case LoginActivity.LOGIN_UNCONNECT:
					Toast.makeText(LoginActivity.this, getString(R.string.network_anomaly), 800).show();
					
					break;


				default:
					break;
				}
			}
		};
		
		final Builder builder = new AlertDialog.Builder(this);
		this.mIPSetting.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				builder.setIcon(R.drawable.icon_cloud);
				builder.setTitle("IP地址设置");
				final LinearLayout ipSettingForm = (LinearLayout)getLayoutInflater().inflate(R.layout.ip_setting, null);
				builder.setView(ipSettingForm);
				//设置确按钮并设置监听器
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
				        EditText ipEditT = (EditText) ipSettingForm.findViewById(R.id.ipEditText);
				        mEditor.putString("IP", ipEditT.getText().toString());
				        mEditor.commit();
					}
				});
				//取消按钮
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//取消，不做任何操作
					}
				});
				//创建并显示对话框
				builder.create().show();
			}
		});
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		//if necessary, keep the login infomation
		if(this.mAutoLogin.isChecked()){
			mEditor.putBoolean("AutoLogin", true);
		}
		else{
			mEditor.putBoolean("AutoLogin", false);
		}
		
		if(this.mKeepPassword.isChecked()){
			mEditor.putString("account", mAccount.getText().toString());
			mEditor.putString("password", mPassword.getText().toString());
			mEditor.putBoolean("KeepPassword", true);
		}
		else{
			mEditor.putString("account", "");
			mEditor.putString("password", "");
			mEditor.putBoolean("KeepPassword", false);
		}
		mEditor.commit();
	}

}