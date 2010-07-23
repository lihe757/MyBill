package com.lihex.mybill.data;

import com.lihex.mybill.R;

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
	"_id","name","blance","remark","type"
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

public void insert(String name ,float blance,String remark,String type){
	ContentValues values=new ContentValues();
	values.put("name", name);
	values.put("blance",blance);
	values.put("remark", remark);
	values.put("type", type);
	db.insert(DB_TABLE_ACOUNTTYPE, null, values);
}
public void update(int id,String name ,float blance,String remark,String type){
	ContentValues values=new ContentValues();
	values.put("name", name);
	values.put("blance",blance);
	values.put("remark", remark);
	values.put("type", type);
	db.update(DB_TABLE_ACOUNTTYPE, values, "_id = "+id, null);
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
public Cursor fetchAllByTypeField(String type){
	Cursor c=null;
	c=this.db.query(DB_TABLE_ACOUNTTYPE, COLS, "type = '"+type+"'", null, null, null, null);
	return c;
}
private static class DBOpenHelper extends SQLiteOpenHelper{
	
	private static final String DB_CREATE="CREATE TABLE "+DBHelper.DB_TABLE_ACOUNTTYPE+
	"  (_id INTEGER PRIMARY KEY,name TEXT UNIQUE NOT NULL,blance REAL,remark TEXT,type TEXT NOT NULL)";
	
	private Context mcContext;
	
	
	public DBOpenHelper(Context context, String name,
			int version) {
		super(context, name, null, version);
		mcContext=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(DB_CREATE);
			final String[] types=mcContext.getResources().getStringArray(R.array.acount_type);
			for(int i=0;i<types.length;i++){
				db.execSQL("INSERT INTO "+DBHelper.DB_TABLE_ACOUNTTYPE+
						" (name,blance,remark,type) VALUES ('"+types[i]+"',0,'"+types[i]+"','"+types[i]+"');");
			}
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
