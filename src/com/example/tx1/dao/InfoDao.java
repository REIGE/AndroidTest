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
		// ����getReadableDatabase����������ʼ�����ݿ�Ĵ���
		SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();

		// ʹ��map��װ�Ķ����������ֵ
		ContentValues values = new ContentValues();
		values.put("id", bean.id);
		values.put("password", bean.password);
		values.put("email", bean.email);
		values.put("phone", bean.phone);

		// table���� nullColumnHack����Ϊ�գ���ʾ���һ������ values����һ�е�ֵ
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
		// columns��ѯ������ selection��ѯ���� selectionArgs����ռλ���Ĳ���ֵ
		// groupBy��ʲô�ֶη��� having��������� orderBy��ʲô�ֶη���
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
