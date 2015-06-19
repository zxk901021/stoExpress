package com.zhy_9.stoexpress.db;

import java.util.ArrayList;
import java.util.List;

import com.zhy_9.stoexpress.model.ScanRecord;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLManager {

	public static SQLiteDatabase db;
	private static SQLManager instance = null;
	private Context context;
	public static StoExpressDBHelper dbHelper;
	public static String SCAN_RECORD_TABLE = "scan_record";

	public static SQLManager getInstance(Context context) {
		if (instance == null) {
			instance = new SQLManager(context);
		}
		return instance;
	}

	public static void connDB() {
		if (db == null) {
			db = dbHelper.getWritableDatabase();
		}
	}

	public SQLManager(Context context) {
		this.context = context;
		dbHelper = new StoExpressDBHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public void addScanRecord(ScanRecord record) {
		connDB();
		db.beginTransaction();
		try {
			db.execSQL(
					"insert into " + SCAN_RECORD_TABLE
							+ "values (null, ?, ?, ?)",
					new String[] { record.getExpressId(),
							record.getExpressStatus(), record.getCourier() });
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
		closeDB();
	}
	
	public void deleteScanRecord(String orderNum){
		connDB();
		db.beginTransaction();
		
		try {
			db.execSQL("delete from " + SCAN_RECORD_TABLE + " where order_number = ?", new String[]{orderNum});
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			db.endTransaction();
		}
		closeDB();
	}
	
	public List<ScanRecord> queryScanRecord (){
		connDB();
		List<ScanRecord> list = new ArrayList<ScanRecord>();
		Cursor c = queryTheCursor(SCAN_RECORD_TABLE);
		
		while (c.moveToNext()) {
			String orderNum = c.getString(c.getColumnIndex("order_number"));
			String status = c.getString(c.getColumnIndex("scan_status"));
			String courier = c.getString(c.getColumnIndex("courier_name"));
			ScanRecord record = new ScanRecord(orderNum, status, courier);
			list.add(record);
		}
		c.close();
		closeDB();
		return list;
	}
	
	public Cursor queryTheCursor(String tableName){
		Cursor c = db.rawQuery("select * from " + tableName, null);
		return c;
	}

	public void closeDB(){
		if (db != null) {
			db.close();
			db = null;
		}
	}
	
}
