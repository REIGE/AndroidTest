package com.example.tx1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

	public MySqliteOpenHelper(Context context) {
		super(context, "info.db", null, 1);

	}

	// ���ݿ��һ�δ�����ʱ�򱻵��ã��ʺ�����ṹ�ĳ�ʼ��
	// ��Ҫִ��sql��䣺SQLiteDatabase db ��������ִ��sql���
	@Override
	public void onCreate(SQLiteDatabase db) {

		// ͨ��SQLiteDatabaseִ��һ���������sql���
		db.execSQL("create table info (id varchar(8) primary key,password varchar(8),email varchar(20),phone varchar(11))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
