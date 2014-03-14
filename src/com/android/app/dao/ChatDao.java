package com.android.app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.app.sqlite.SqliteHelper;


public class ChatDao {
	// CRUD操作

	private SqliteHelper sqLiteHelper;

	public ChatDao(Context context) {
		this.sqLiteHelper = new SqliteHelper(context);
	}

	// 读操作
	public String execQuery(final String strSQL) {
		try {
			SQLiteDatabase db = this.sqLiteHelper.getReadableDatabase();
			System.out.println("SQL>" + strSQL);
			Cursor cursor = db.rawQuery(strSQL, null);
			cursor.moveToFirst();
			StringBuffer buffer = new StringBuffer();
			while (!cursor.isAfterLast()) {
				buffer.append(cursor.getInt(0) + "-" + cursor.getString(3)
						+ "-" + cursor.getString(4).substring(10, 19) + "-"
						+ cursor.getInt(1) + "-" + cursor.getInt(2) + "-"
						+ cursor.getInt(5) + "-" + cursor.getInt(6) + "#");
				cursor.moveToNext();
			}
			db.close();
			// sToast.makeText(context, text, duration)
			return buffer.deleteCharAt(buffer.length() - 1).toString();
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	// 写操作
	public boolean execOther(final String strSQL) {
		try {
			SQLiteDatabase db = this.sqLiteHelper.getWritableDatabase();
			System.out.println("SQL>" + strSQL);
			db.execSQL(strSQL);
			db.close();
			return true;
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return false;
		}

	}
}



