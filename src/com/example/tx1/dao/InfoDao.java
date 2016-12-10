package com.example.tx1.dao;

import java.util.ArrayList;

import com.example.tx1.MySqliteOpenHelper;
import com.example.tx1.bean.InfoBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class InfoDao {

	private MySqliteOpenHelper mySqliteOpenHelper;

	public InfoDao(Context context) {
		mySqliteOpenHelper = new MySqliteOpenHelper(context);
	}

	public boolean add(InfoBean bean) {
		// 调用getReadableDatabase方法，来初始化数据库的创建
		SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();

		// 使用map封装的对象，用来存放值
		ContentValues values = new ContentValues();
		values.put("id", bean.id);
		values.put("password", bean.password);
		values.put("email", bean.email);
		values.put("phone", bean.phone);

		// table表名 nullColumnHack可以为空，表示添加一个空行 values数据一行的值
		long result = db.insert("info", null, values);
		db.close();
		if (result != -1) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<InfoBean> query(String name) {
		ArrayList<InfoBean> list = new ArrayList<InfoBean>();

		SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
		// columns查询的列名 selection查询条件 selectionArgs条件占位符的参数值
		// groupBy按什么字段分组 having分组的条件 orderBy按什么字段分组
		// Cursor cursor = db.query("info", null, "id=?", new String[] {name},
		// null, null, null);
		Cursor cursor;
		if (name != null) {
			cursor = db.query("info", null, "id=?", new String[] { name }, null, null, null);
		} else {
			cursor = db.query("info", null, null, null, null, null, null);
		}
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				InfoBean bean = new InfoBean();
				bean.id = cursor.getString(0);
				bean.password = cursor.getString(1);
				bean.email = cursor.getString(2);
				bean.phone = cursor.getString(3);

				list.add(bean);
			}
			cursor.close();
		}

		db.close();
		return list;
	}
}
