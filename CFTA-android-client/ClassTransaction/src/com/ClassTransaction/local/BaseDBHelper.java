package com.ClassTransaction.local;

import java.util.Collection;

/**
 * dao基类
 * @author Tyscj
 *
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ClassTransaction.util.DataUtil;


public class BaseDBHelper extends SQLiteOpenHelper{
	
	public BaseDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	//根据参数的SQL, 存放结果的集合对象, 和具体的数据库映射对象返回一个集合
	public Collection<?> getDatas(String sql, Collection<Object> result, Class<?> clazz) {
		//读数据库
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor;
		cursor = db.rawQuery(sql,null);
		//对cursor进行封装并返回集合
		Collection tmp = DataUtil.getDatas(result, cursor, clazz);
		cursor.close();
		db.close();
		return tmp;
	}
}
