package com.lihex.mybill.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class DBHelperUsage extends DBHelper {



	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}


	private SQLiteDatabase db;
	public static final String[] COLS = new String[] { "_id", "parent_id", "name", "type" };
	public DBHelperUsage(SQLiteDatabase db) {
		super(db);
		this.db=db;
	}

	@Override
	public void insert(ContentValues values) {
		
		db.insert(DBHelperFactory.DB_TABLE_USAGE, null, values);
		
	}
	public void insert(Bundle b) {
		ContentValues values=new ContentValues();
		values.put("parent_id", b.getInt("parent_id"));
		values.put("name", b.getString("name"));
		values.put("type", b.getString("type"));
		db.insert(DBHelperFactory.DB_TABLE_USAGE, null, values);
	}
	public void update(int id,Bundle b){
		ContentValues values=new ContentValues();
		values.put("parent_id", b.getInt("parent_id"));
		values.put("name", b.getString("name"));
		values.put("type", b.getString("type"));
		db.update(DBHelperFactory.DB_TABLE_USAGE, values, "_id = "+id, null);
	}
	
	@Override
	public void delete(int id) {
		db.delete(DBHelperFactory.DB_TABLE_USAGE, "_id = "+id, null);

	}

	@Override
	public Cursor fetchAll() {
		Cursor cursor=db.query(DBHelperFactory.DB_TABLE_USAGE, COLS, null, null, null, null, null);
		return cursor;
	}

	@Override
	public Cursor fetchOne(int id) {
		Cursor cursor=db.query(DBHelperFactory.DB_TABLE_USAGE, COLS, "_id = "+id, null, null, null, null);
		cursor.moveToFirst();
		return cursor;
	}
	public Cursor fetchFirstLevel(String type){
		Cursor c=db.query(DBHelperFactory.DB_TABLE_USAGE, COLS, " parent_id = -1 AND type = '"+type+"'", null, null, null, null);
	
		return c;
	}
	public Cursor fetchFirstLevelIgnore(String type,int[] igIds){
		Cursor c=null;
		if(igIds.length!=0){
			StringBuffer sb=new StringBuffer(" parent_id = -1 AND type = '"+type+"' AND _id NOT IN ("+igIds[0]);
			for(int i=1;i<igIds.length;i++){
				sb.append(","+igIds[i]);
			}
			sb.append(")");
			c=db.query(DBHelperFactory.DB_TABLE_USAGE, COLS, sb.toString(), null, null, null, null);
			Log.i("DBHelperUsage ",sb.toString());
		}else{
			c=fetchFirstLevel(type);
		}
		return c;
	
		
		
	}
	
	public Cursor fetchSecendLevel(int pId){
		Cursor c=db.query(DBHelperFactory.DB_TABLE_USAGE, COLS, " parent_id = "+pId, null, null, null, null);
		return c;
	}

	
	public void insert(int pId,String name ,String type){
		ContentValues values=new ContentValues();
		values.put("parent_id", pId);
		values.put("name", name);
		values.put("type", type);
		db.insert(DBHelperFactory.DB_TABLE_USAGE, null, values);
	}

}
