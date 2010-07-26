package com.lihex.mybill.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHelperAccountType extends DBHelper {

	public static final String[] COLS = new String[] { "_id", "name", "blance",
			"remark", "type" };
	
	private SQLiteDatabase db;
	
	public DBHelperAccountType(SQLiteDatabase db) {
		super(db);
		this.db=db;
		
	}

	public void insert(String name, float blance, String remark, String type) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("blance", blance);
		values.put("remark", remark);
		values.put("type", type);
		db.insert(DBHelperFactory.DB_TABLE_ACOUNTTYPE, null, values);
	}

	public void update(int id, String name, float blance, String remark,
			String type) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("blance", blance);
		values.put("remark", remark);
		values.put("type", type);
		db.update(DBHelperFactory.DB_TABLE_ACOUNTTYPE, values, "_id = " + id, null);
	}

	public void delete(int id) {
		db.delete(DBHelperFactory.DB_TABLE_ACOUNTTYPE, "_id=" + id, null);
	}

	public Cursor fetchAll() {
		Cursor c = null;
		c = this.db.query(DBHelperFactory.DB_TABLE_ACOUNTTYPE, COLS, null, null, null, null,
				null);
		return c;
	}

	public Cursor fetchAllByTypeField(String type) {
		Cursor c = null;
		c = this.db.query(DBHelperFactory.DB_TABLE_ACOUNTTYPE, COLS, "type = '" + type + "'",
				null, null, null, null);
		return c;
	}
	
	public Cursor fetchOne(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
