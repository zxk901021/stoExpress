package com.zhy_9.stoexpress.db;

import java.util.ArrayList;
import java.util.List;

import com.zhy_9.stoexpress.model.CountRecords;
import com.zhy_9.stoexpress.model.ProblemType;
import com.zhy_9.stoexpress.model.Record;
import com.zhy_9.stoexpress.model.RecordCounts;
import com.zhy_9.stoexpress.model.ScanRecord;
import com.zhy_9.stoexpress.model.StoInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLManager {

	private static SQLiteDatabase db;
	private static StoExpressDBHelper dbHelper;
	private static String SCAN_RECORD_TABLE = "scan_record";
	private static String PROBLEM_TYPE_TABLE = "problem_type";
	private static String STO_INFO_TABLE = "sto_info";

	public static void connDB() {
		if (db == null) {
			db = dbHelper.getWritableDatabase();
		}
	}

	public SQLManager(Context context) {
		super();
		dbHelper = new StoExpressDBHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public void addScanRecord(ScanRecord record) {
		connDB();
		db.beginTransaction();
		try {
			db.execSQL(
					"insert into " + SCAN_RECORD_TABLE
							+ " values (null, ?, ?, ?, ?, ?, ?, ?, ?)",
					new Object[] { record.getExpressId(),
							record.getExpressStatus(), record.getCourier(),
							record.getDate(), record.getTime(),
							record.getScanType(), record.getIsUpload(),
							record.getYear() });
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		closeDB();
	}

	public void updateScanRecord(int id) {
		connDB();
		db.beginTransaction();
		try {
			db.execSQL("update " + SCAN_RECORD_TABLE
					+ " set is_upload = 1 where _id = ?", new Object[] { id });
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		closeDB();
	}

	public void addStoInfo(StoInfo info) {
		connDB();
		try {
			db.execSQL(
					"insert into " + STO_INFO_TABLE
							+ " values (null, ?, ?, ?, ?, ?, ?, ?, ?)",
					new String[] { info.getCourierId(), info.getCourierName(),
							info.getCompanyName(), info.getBranchNumber(),
							info.getPassword(), info.getLastUpdate(),
							info.getOperflag(), info.getBelongPost() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeDB();
	}

	public void addProblemType(ProblemType type) {
		connDB();
		db.beginTransaction();
		try {
			db.execSQL(
					"INSERT INTO " + PROBLEM_TYPE_TABLE
							+ " VALUES (null, ?, ?, ?, ?, ?)",
					new String[] { type.getProblemNo(), type.getProblemType(),
							type.getType(), type.getOperflag(),
							type.getLastUpdate() });
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		closeDB();
	}

	public void deleteStoInfo(String employeeNo) {
		connDB();
		try {
			db.execSQL("delete from " + STO_INFO_TABLE
					+ " where employee_no = ?", new String[] { employeeNo });
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeDB();
	}

	public void deleteScanRecord(String orderNum) {
		connDB();
		db.beginTransaction();

		try {
			db.execSQL("delete from " + SCAN_RECORD_TABLE
					+ " where order_number = ?", new String[] { orderNum });
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		closeDB();
	}

	public List<Record> queryScanRecordUnUpload(String scanType) {
		connDB();
		List<Record> list = new ArrayList<Record>();
		Cursor c = db.rawQuery("select distinct date from " + SCAN_RECORD_TABLE
				+ " where scan_type = ? and is_upload = ? order by date DESC",
				new String[] { scanType, "0" });

		while (c.moveToNext()) {
			Record record = new Record();
			String date = c.getString(c.getColumnIndex("date"));
			record.setDate(date);
			List<ScanRecord> scan = new ArrayList<ScanRecord>();

			Cursor cursor = db.rawQuery("select * from " + SCAN_RECORD_TABLE
					+ " where scan_type = ? and date = ?", new String[] {
					scanType, date });
			while (cursor.moveToNext()) {
				String orderNum = cursor.getString(cursor
						.getColumnIndex("order_number"));
				String status = cursor.getString(cursor
						.getColumnIndex("scan_status"));
				String courier = cursor.getString(cursor
						.getColumnIndex("courier_name"));
				String time = cursor.getString(cursor.getColumnIndex("time"));
				String isUpload = cursor.getString(cursor
						.getColumnIndex("is_upload"));
				int id = cursor.getInt(cursor.getColumnIndex("_id"));
				ScanRecord scanRecord = new ScanRecord(orderNum, status,
						courier, date, time, isUpload, id);
				scan.add(scanRecord);

			}
			cursor.close();
			record.setInfo(scan);
			list.add(record);
		}
		c.close();
		closeDB();
		return list;
	}

	public List<CountRecords> queryRecord(String scanType) {
		connDB();
		List<CountRecords> countRecords = new ArrayList<CountRecords>();

		Cursor c = db.rawQuery("select distinct year from " + SCAN_RECORD_TABLE
				+ " where scan_type = ? order by year DESC",
				new String[] { scanType });
		while (c.moveToNext()) {
			CountRecords records = new CountRecords();
			// Map<String, String> set = new HashMap<String, String>();
			String year = c.getString(c.getColumnIndex("year"));
			records.setYear(year);
			Cursor cursor = db
					.rawQuery(
							"select distinct strftime('%m',date) from "
									+ SCAN_RECORD_TABLE
									+ " where scan_type = ? and year = ? order by strftime('%m',date) DESC",
							new String[] { scanType, year });
			List<RecordCounts> recordCounts = new ArrayList<RecordCounts>();
			while (cursor.moveToNext()) {
				
				RecordCounts counts = new RecordCounts();
				String date = cursor.getString(0);
				counts.setMonth(date);
				Log.e("date", date);
				Cursor curs = db
						.rawQuery(
								"select count(_id) from "
										+ SCAN_RECORD_TABLE
										+ " where year = ? and scan_type = ? and strftime('%m',date) = ?",
								new String[] { year, scanType, date });
				// String date = c.getString(c.getColumnIndex("date"));

				if (curs.moveToFirst()) {
					int count = curs.getInt(0);
					counts.setCounts(count + "");
					Log.e("count", count + "");
				}
				curs.close();
				// Cursor cur = db.rawQuery("select * from " + SCAN_RECORD_TABLE
				// + "where scan_type = ? and date = ? order by DESC",
				// new String[] { scanType, date });
				// cur.close();
				recordCounts.add(counts);
				
			}
			records.setList(recordCounts);
			countRecords.add(records);
			cursor.close();
		}
		c.close();
		closeDB();
		return countRecords;
	}

	// public List<Map<String, String>> queryRecord(String scanType) {
	// connDB();
	// List<Map<String, String>> map = new ArrayList<Map<String, String>>();
	//
	// Cursor c = db.rawQuery("select distinct year from " + SCAN_RECORD_TABLE
	// + " where scan_type = ? order by year DESC",
	// new String[] { scanType });
	// while (c.moveToNext()) {
	// Map<String, String> set = new HashMap<String, String>();
	// String year = c.getString(c.getColumnIndex("year"));
	// set.put("year", year);
	// Cursor cursor = db
	// .rawQuery(
	// "select distinct strftime('%m',date) from "
	// + SCAN_RECORD_TABLE
	// + " where scan_type = ? and year = ? order by strftime('%m',date) DESC",
	// new String[] { scanType, year });
	// while (cursor.moveToNext()) {
	// String date = cursor.getString(0);
	// Log.e("date", date);
	// Cursor curs = db
	// .rawQuery(
	// "select count(_id) from "
	// + SCAN_RECORD_TABLE
	// + " where year = ? and scan_type = ? and strftime('%m',date) = ?",
	// new String[] { year, scanType, date });
	// // String date = c.getString(c.getColumnIndex("date"));
	//
	// if (curs.moveToFirst()) {
	// int count = curs.getInt(0);
	// Log.e("count", count + "");
	// }
	// curs.close();
	// set.put("month", date);
	// // Cursor cur = db.rawQuery("select * from " + SCAN_RECORD_TABLE
	// // + "where scan_type = ? and date = ? order by DESC",
	// // new String[] { scanType, date });
	// // cur.close();
	//
	// }
	// map.add(set);
	// cursor.close();
	// }
	// c.close();
	// closeDB();
	// return map;
	// }

	public List<Record> queryScanRecord(String scanType) {
		connDB();
		List<Record> list = new ArrayList<Record>();
		Cursor c = db.rawQuery("select distinct date from " + SCAN_RECORD_TABLE
				+ " where scan_type = ? order by date DESC",
				new String[] { scanType });

		while (c.moveToNext()) {
			Record record = new Record();
			String date = c.getString(c.getColumnIndex("date"));
			record.setDate(date);
			List<ScanRecord> scan = new ArrayList<ScanRecord>();

			Cursor cursor = db.rawQuery("select * from " + SCAN_RECORD_TABLE
					+ " where scan_type = ? and date = ?", new String[] {
					scanType, date });
			while (cursor.moveToNext()) {
				String orderNum = cursor.getString(cursor
						.getColumnIndex("order_number"));
				String status = cursor.getString(cursor
						.getColumnIndex("scan_status"));
				String courier = cursor.getString(cursor
						.getColumnIndex("courier_name"));
				String time = cursor.getString(cursor.getColumnIndex("time"));
				String isUpload = cursor.getString(cursor
						.getColumnIndex("is_upload"));
				int id = cursor.getInt(cursor.getColumnIndex("_id"));
				ScanRecord scanRecord = new ScanRecord(orderNum, status,
						courier, date, time, isUpload, id);
				scan.add(scanRecord);

			}
			cursor.close();
			record.setInfo(scan);
			list.add(record);
		}
		c.close();
		closeDB();
		return list;
	}

	public boolean queryUpload() {
		connDB();
		boolean flag;
		Cursor c = db.rawQuery("select * from " + SCAN_RECORD_TABLE
				+ " where is_upload = ?", new String[] { "1" });
		if (c == null) {
			flag = false;
		} else {
			flag = true;
		}
		c.close();
		closeDB();
		return flag;
	}

	public List<ProblemType> queryProblemType() {
		connDB();
		List<ProblemType> list = new ArrayList<ProblemType>();
		Cursor c = queryTheCursor(PROBLEM_TYPE_TABLE);
		while (c.moveToNext()) {
			String problemType = c.getString(c.getColumnIndex("problem_type"));
			String type = c.getString(c.getColumnIndex("type"));
			ProblemType pro = new ProblemType();
			pro.setProblemType(problemType);
			pro.setType(type);
			list.add(pro);

		}
		c.close();
		closeDB();
		return list;

	}

	public Cursor queryTheCursor(String tableName) {
		Cursor c = db.rawQuery("select * from " + tableName, null);
		return c;
	}

	public void closeDB() {
		if (db != null) {
			db.close();
			db = null;
		}
	}

}
