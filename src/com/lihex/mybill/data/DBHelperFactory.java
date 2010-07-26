package com.lihex.mybill.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lihex.mybill.R;


public class DBHelperFactory {
	public static final int DB_TYPE_ACCOUNT_TYPE=0;
	
	private SQLiteDatabase db;
	private DBOpenHelper mdbHelper;
	
	private Context mContext;
	private static DBHelperFactory mInstance;
	
	public static final int DB_VERSION = 3;
	public static final String DB_NAME = "mybill_db";
	public static final String DB_TABLE_ACOUNTTYPE="tb_acount_type";
	public static final String DB_TABLE_USAGE="tb_usage";
	
	
	private DBHelperFactory(Context context){
		mContext=context;
		mdbHelper=new DBOpenHelper(mContext, DB_NAME, DB_VERSION);
		db=mdbHelper.getWritableDatabase();
	}
	
	/**
	 * 获得一个工厂实例
	 * @param context
	 * @return
	 */
	public static DBHelperFactory getInstance(Context context){
		if(mInstance!=null)return mInstance;
		else{
			mInstance=new DBHelperFactory(context);
			return mInstance;
		}
		
	}
	
	/**
	 * 根据类型ID，获得一个DBHelper
	 * @param type
	 * @return
	 */
	public  DBHelper getDBHelperByType(int type){	
		DBHelper dbHelper=null;
		switch (type) {
		case DB_TYPE_ACCOUNT_TYPE:
			dbHelper=new DBHelperAccountType(db);
			break;
		}
		return dbHelper;
	}

	
	
	private static class DBOpenHelper extends SQLiteOpenHelper {

		private static final String DB_CREATE = "CREATE TABLE "
				+ DBHelperFactory.DB_TABLE_ACOUNTTYPE
				+ "  (_id INTEGER PRIMARY KEY,name TEXT UNIQUE NOT NULL,blance REAL,remark TEXT,type TEXT NOT NULL)";

		private Context mcContext;

		public DBOpenHelper(Context context, String name, int version) {
			super(context, name, null, version);
			mcContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DB_CREATE);
				final String[] types = mcContext.getResources().getStringArray(
						R.array.acount_type);
				for (int i = 0; i < types.length; i++) {
					db.execSQL("INSERT INTO " + DBHelperFactory.DB_TABLE_ACOUNTTYPE
							+ " (name,blance,remark,type) VALUES ('" + types[i]
							+ "',0,'" + types[i] + "','" + types[i] + "');");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS" + DBHelperFactory.DB_NAME);
			this.onCreate(db);
		}

	}

}
