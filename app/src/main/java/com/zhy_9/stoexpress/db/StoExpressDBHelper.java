package com.zhy_9.stoexpress.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoExpressDBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "sto_express.db";
	private static final int DATABASE_VERSION = 1;

	public StoExpressDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// db.execSQL("create table if not exists sto_info " +
		// "(_id integer primary key autoincrement, " +
		// "courier_id text, " +
		// "courier_name text, " +
		// "courier_phone text, " +
		// "equip_id text, " +
		// "company_name text) ");
		db.execSQL("create table if not exists scan_record "
				+ "(_id integer primary key autoincrement, "
				+ "order_number text, " + "scan_status text, "
				+ "courier_name text, " + "date text, " + "time text, "
				+ "scan_type text, " + "is_upload text, " +
						"year text, " + "UNIQUE(time))");
		db.execSQL("CREATE TABLE IF NOT EXISTS problem_type "
				+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "problem_no VARCHAR, " + "problem_type VARCHAR, "
				+ "type VARCHAR, " + "operflag VARCHAR, "
				+ "last_update VARCHAR, " + "UNIQUE(problem_no))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("ALTER TABLE sto_info ADD COLUMN other STRING");
		db.execSQL("ALTER TABLE scan_record ADD COLUMN other STRING");
		db.execSQL("ALTER TABLE problem_type ADD COLUMN other STRING");
	}

}
