package com.lihex.mybill.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DBHelper {
	
	public DBHelper(SQLiteDatabase db){
		
	}
	
	public abstract void delete(int id);

	public abstract Cursor fetchOne(int id);
	
	public abstract Cursor fetchAll();
}

