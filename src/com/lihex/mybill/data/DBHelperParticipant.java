package com.lihex.mybill.data;

import javax.xml.parsers.DocumentBuilderFactory;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class DBHelperParticipant extends DBHelper {
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	public static final String[] COLS = new String[] { "_id", "type_id", "name",
		"remark"};
	private SQLiteDatabase db;
	public DBHelperParticipant(SQLiteDatabase db) {
		super(db);
		this.db=db;
	}

	@Override
	public void delete(int id) {
		db.delete(DBHelperFactory.DB_TABLE_PARTICIPANT, "_id = "+id, null);
		

	}
	public int update(int id,int type_id,String name ,String remark){
		ContentValues values=new ContentValues();
		values.put("type_id", type_id);
		values.put("name", name);
		values.put("remark", remark);
		return db.update(DBHelperFactory.DB_TABLE_PARTICIPANT, values, "_id = "+id, null);
	}
	public int update(int id ,Bundle b){
		ContentValues values=new ContentValues();
		values.put("type_id", b.getInt("type_id"));
		values.put("name", b.getString("name"));
		values.put("remark", b.getString("remark"));
		return db.update(DBHelperFactory.DB_TABLE_PARTICIPANT, values, "_id = "+id, null);
	}

	@Override
	public Cursor fetchAll() {
		// TODO Auto-generated method stub
		return null;
	}
	public Cursor fetchAllByType(int typeId){
		Cursor c =db.query(DBHelperFactory.DB_TABLE_PARTICIPANT, COLS, "type_id = "+typeId, null,null, null, null);		
		return c;
	}

	@Override
	public Cursor fetchOne(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(ContentValues values) {
		// TODO Auto-generated method stub

	}
	public int insert(int type_id ,String name,String remark){
		ContentValues values=new ContentValues();
		values.put("type_id", type_id);
		values.put("name", name);
		values.put("remark", remark);
	
		return (int) db.insert(DBHelperFactory.DB_TABLE_PARTICIPANT, null, values);
	}
	public int insert(Bundle b){
		ContentValues values=new ContentValues();
		values.put("type_id", b.getInt("type_id"));
		values.put("name", b.getString("name"));
		values.put("remark", b.getString("remark"));
		return (int) db.insert(DBHelperFactory.DB_TABLE_PARTICIPANT, null, values);
	}

}
