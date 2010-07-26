package com.lihex.mybill.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lihex.mybill.R;

public class DBHelperFactory {
	public static final int DB_TYPE_ACCOUNT = 0;
	public static final int DB_TYPE_USAGE = 1;

	private SQLiteDatabase db;
	private DBOpenHelper mdbHelper;

	private Context mContext;
	private static DBHelperFactory mInstance;

	public static final int DB_VERSION = 3;
	public static final String DB_NAME = "mybill_db";
	public static final String DB_TABLE_ACOUNTTYPE = "tb_acount_type";
	public static final String DB_TABLE_USAGE = "tb_usage";

	private DBHelperFactory(Context context) {
		mContext = context;
		mdbHelper = new DBOpenHelper(mContext, DB_NAME, DB_VERSION);
		db = mdbHelper.getWritableDatabase();
	}

	/**
	 * 获得一个工厂实例
	 * 
	 * @param context
	 * @return
	 */
	public static DBHelperFactory getInstance(Context context) {
		if (mInstance != null)
			return mInstance;
		else {
			mInstance = new DBHelperFactory(context);
			return mInstance;
		}

	}

	/**
	 * 根据类型ID，获得一个DBHelper
	 * 
	 * @param type
	 * @return
	 */
	public DBHelper getDBHelperByType(int type) {
		DBHelper dbHelper = null;
		switch (type) {
		case DB_TYPE_ACCOUNT:
			dbHelper = new DBHelperAccountType(db);
			break;
		case DB_TYPE_USAGE:
			dbHelper =new DBHelperUsage(db);
			break;
		}
		return dbHelper;
	}

	private static class DBOpenHelper extends SQLiteOpenHelper {

		private static final String DB_CREATE_ACCOUNT_TABLE = "CREATE TABLE "
				+ DBHelperFactory.DB_TABLE_ACOUNTTYPE
				+ "  (_id INTEGER PRIMARY KEY,name TEXT UNIQUE NOT NULL,blance REAL,remark TEXT,type TEXT NOT NULL)";
		private static final String DB_CREATE_USAGE_TABLE = "CREATE TABLE "
				+ DBHelperFactory.DB_TABLE_USAGE
				+ " (_id INTEGER PRIMARY KEY , parent_id INTEGER   , name TEXT UNIQUE NOT NULL , type TEXT NOT NULL)";
		private Context mcContext;

		public DBOpenHelper(Context context, String name, int version) {
			super(context, name, null, version);
			mcContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				/* 生成账户类型表，并初始化 */
				db.execSQL(DB_CREATE_ACCOUNT_TABLE);
				String[] types = mcContext.getResources().getStringArray(
						R.array.acount_type);
				for (int i = 0; i < types.length; i++) {
					db.execSQL("INSERT INTO "
							+ DBHelperFactory.DB_TABLE_ACOUNTTYPE
							+ " (name,blance,remark,type) VALUES ('" + types[i]
							+ "',0,'" + types[i] + "','" + types[i] + "');");
				}
				
				/* 生成使用类型表，并初始化 */
				db.execSQL(DB_CREATE_USAGE_TABLE);
				String[] types2 = mcContext.getResources().getStringArray(
						R.array.usage_type);
				for (int i = 0; i < types2.length; i++) {
					db.execSQL("INSERT INTO "
							+ DBHelperFactory.DB_TABLE_USAGE
							+ " (parent_id,name,type) VALUES ( -1, '" + types2[i]
							+ "','" + types2[i] + "');");
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
