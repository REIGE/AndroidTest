package com.example.tx1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

	public MySqliteOpenHelper(Context context) {
		super(context, "info.db", null, 1);

	}

	// 数据库第一次创建的时候被调用，适合做表结构的初始化
	// 需要执行sql语句：SQLiteDatabase db 可以用来执行sql语句
	@Override
	public void onCreate(SQLiteDatabase db) {

		// 通过SQLiteDatabase执行一个创建表的sql语句
		db.execSQL("create table info (id varchar(8) primary key,password varchar(8),email varchar(20),phone varchar(11))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
