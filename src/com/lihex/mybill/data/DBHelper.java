package com.lihex.mybill.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper {
private SQLiteDatabase db;
private DBOpenHelper mdbHelper;
public static final int DB_VERSION=3;
public static final String DB_NAME="mybill_db";
public static final String DB_TABLE_ACOUNTTYPE="tb_acount_type";
public static final String []COLS=new String[]{
	"_id","name","blance","remark"
};

public DBHelper(Context context){
	mdbHelper=new DBOpenHelper(context, DB_NAME, DB_VERSION);
}

public void establishDb(){
	if(this.db==null){
		this.db=this.mdbHelper.getWritableDatabase();
	}
}
public void cleanUp(){
	if(this.db!=null){
		db.close();
		db=null;
	}
}

public void insert(String name ,float blance,String remark){
	ContentValues values=new ContentValues();
	values.put("name", name);
	values.put("blance",blance);
	values.put("remark", remark);
	db.insert(DB_TABLE_ACOUNTTYPE, null, values);
}
public void update(){
	
}
public void delete(int id){
	db.delete(DB_TABLE_ACOUNTTYPE, "_id="+id, null);
}
public void fetchOne(){
	
}
public Cursor fetchAll(){
	Cursor c=null;
	c=this.db.query(DB_TABLE_ACOUNTTYPE, COLS, null, null, null, null, null);
	return c;
}
private static class DBOpenHelper extends SQLiteOpenHelper{
	
	private static final String DB_CREATE="CREATE TABLE "+DBHelper.DB_TABLE_ACOUNTTYPE+
	"  (_id INTEGER PRIMARY KEY,name TEXT UNIQUE NOT NULL,blance REAL,remark TEXT)";
	
	public DBOpenHelper(Context context, String name,
			int version) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(DB_CREATE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS"+DBHelper.DB_NAME);
		this.onCreate(db);
		
	}
	
}

}
