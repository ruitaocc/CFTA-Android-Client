package com.ClassTransaction.activity;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.ClassTransaction.client.Classt_ransactionActivity;
import com.ClassTransaction.client.Course;
import com.ClassTransaction.client.User;
import com.ClassTransaction.commons.Request;
import com.ClassTransaction.local.CourseDBHelper;
import com.ClassTransaction.util.ApplicationContext;
import com.ClassTransaction.util.XStreamUtil;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class HomeActivity extends Activity {
	public static final int UPDATE_SCHEDULE = 141;
	public static final int CLEAN_SCHEDULE = 142;
	private static GridView mCalendarTable;
	// login mode
	public static int LOGIN_MODE = 0;
	public static int OFFLINE_MODE = 1;
	private static int MODE;

	private static Context mContext;
	// database interaction
	private static CourseDBHelper mCdHelper;
	//const string value of a week
	private static String dayInWeek[] = null;
	//schedule table
	static String[][] data_str = new String[13][8];
	static ArrayList<HashMap<String, String>> mData;
	static //时间解析时用到
	boolean isInThisWeek = false;
	static int dateOfWeek = -1;
	static int startTimeOfCourse = -1;
	static int lastTimeOfCourse = -1;
	//handler for ui
	public static Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case UPDATE_SCHEDULE:
			{
				
				Bundle data = msg.getData();
				Course course = new Course();
				String courseXml = data.getString("course");
				course = (Course) XStreamUtil.fromXML(courseXml);
				mCdHelper.save(course);
				System.out.println(course.toString());

				String courseTime = course.getCOURSETIME();

				courseTimePaser(courseTime);

				System.out.println(courseTime);
				System.out.println(isInThisWeek);
				System.out.println(dateOfWeek);
				System.out.println(startTimeOfCourse);
				System.out.println(lastTimeOfCourse);

				if(!isInThisWeek)break;
				for(int i = startTimeOfCourse;i<=lastTimeOfCourse;i++){
					System.out.println(dateOfWeek);  
					System.out.println(i);
					data_str[i][dateOfWeek] = course.getCOURSENAME();
					
				}
				InitialData();

				break;
			}
			case CLEAN_SCHEDULE:
			{
				for(int i = 1;i<13;i++)
					for(int j = 1 ;j<8;j++)
						data_str[i][j] ="";
				SQLiteDatabase db = mCdHelper.getReadableDatabase();
				mCdHelper.onUpgrade(db,1,1);
				db.close();
				break;
			}
			default :
				break;
			}
			super.handleMessage(msg);
		}
	};


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		mContext = HomeActivity.this;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			this.MODE = bundle.getInt("MODE");
		}

		if(this.mCdHelper == null){
			this.mCdHelper = new CourseDBHelper(this, "CLASSTRANSACTION.db3",
					null, 1);
		}
		
		//添加界面切换效果，注意只有Android的2.0(SdkVersion版本号为5)以后的版本才支持  
		int version = Integer.valueOf(android.os.Build.VERSION.SDK);
		if(version  >= 5) {     
		     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		}
		
		dayInWeek = new String[]{getString(R.string.mon),getString(R.string.tue),
				getString(R.string.wed),getString(R.string.thu),getString(R.string.fri),
				getString(R.string.sat),getString(R.string.sun)};

		data_str[0][0] = "第四周";
		data_str[0][1] = getString(R.string.mon);
		data_str[0][2] = getString(R.string.tue);
		data_str[0][3] = getString(R.string.wed);
		data_str[0][4] = getString(R.string.thu);
		data_str[0][5] = getString(R.string.fri);
		data_str[0][6] = getString(R.string.sat);
		data_str[0][7] = getString(R.string.sun);
		data_str[1][0] = getString(R.string.section1);;
		data_str[2][0] = getString(R.string.section2);
		data_str[3][0] = getString(R.string.section3);
		data_str[4][0] = getString(R.string.section4);
		data_str[5][0] = getString(R.string.section5);
		data_str[6][0] = getString(R.string.section6);
		data_str[7][0] = getString(R.string.section7);
		data_str[8][0] = getString(R.string.section8);
		data_str[9][0] = getString(R.string.section9);
		data_str[10][0] = getString(R.string.section10);
		data_str[11][0] = getString(R.string.section11);
		data_str[12][0] = getString(R.string.section12);

		InitialWidget();
		
		getDataFromDB();
		
		InitialData();
	}

	private void InitialWidget() {
		this.mCalendarTable = (GridView) findViewById(R.id.calendar_table);
	}

	private void getDataFromDB(){
		List<Course> courses = mCdHelper.findCourses();
		for(Course course:courses){
			String courseTime = course.getCOURSETIME();
			courseTimePaser(courseTime);
			if(!isInThisWeek) continue;
			for(int i = startTimeOfCourse;i<=lastTimeOfCourse;i++){
				data_str[i][dateOfWeek] = course.getCOURSENAME();
			}
		}
	}
	
	private static void InitialData() {
		mData = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 8; j++) {
				HashMap<String, String> item = new HashMap<String, String>();
				item.put("content", data_str[i][j]);
				mData.add(item);
			}
		}
		mCalendarTable.setAdapter(new SimpleAdapter(mContext,
				mData, R.layout.calendar_item, new String[] { "content" },
				new int[] { R.id.calendar_content }));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflator = new MenuInflater(this);
		inflator.inflate(R.menu.home_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.update_schedule:
			try {
				Request request = new Request("com.classtransaction.server.ServerResponse","com.ClassTransaction.client.ClientResponse");
				request.setParameter("type", "getcourses");
				System.out.println("senting");
				PrintStream ps = new PrintStream(ApplicationContext.gUser.getSocket().getOutputStream(),true,"gb-2312");
				ps.println(request.toXML());
				System.out.println("sented");
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	//课程时间解析，
	public static void courseTimePaser(String courseTime){
		//wripe blank of two side
		courseTime = courseTime.trim();
		//分成前后两节
		String subStr[] = new String[2];
		subStr=courseTime.split("\\{");
		//

		for(int i = 0 ;i<7;i++){
			if(subStr[0].contains(dayInWeek[i])){
				dateOfWeek = i+1;
				break;
			}
		}




		//paser if this course active in this week

		int startWeek = -1 ,endWeek = -1;
		//扫描起始周 模式匹配
		for(int i = 22;i>0;i--){
			if(subStr[1].contains(i+"-")){
				startWeek = i;
				break;
			}
		}
		//扫描结束周 模式匹配
		for(int i = 22;i>0;i--){
			if(subStr[1].contains("-"+i)){
				endWeek = i;

				break;
			}
		}
		//判定课程是否当前周活跃
		if(ApplicationContext.gWeekOfTerm>=startWeek&&ApplicationContext.gWeekOfTerm<=endWeek){
			isInThisWeek = true;
		}else isInThisWeek = false;

		//扫描课程在当天的第几节开始 持续多少节课
		//扫描起始周 模式匹配
		for(int i = 12;i>0;i--){
			if(subStr[0].contains("第"+i)){

				startTimeOfCourse = i;
				break;
			}
		}
		//扫描结束周 模式匹配
		for(int i = 12;i>0;i--){
			if(subStr[0].contains(i+"节")){
				lastTimeOfCourse = i;
				break;
			}
		}
	}


}