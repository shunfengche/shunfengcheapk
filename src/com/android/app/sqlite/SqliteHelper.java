package com.android.app.sqlite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

@SuppressWarnings("unused")
public class SqliteHelper extends SQLiteOpenHelper {
	// 璁剧疆鍙傛暟甯搁噺
	private static final String DATABASE_NAME = "shunfengche";// 鏁版嵁搴撳悕绉�
	private static final String TABLE_NAME_CHAT = "chat";// 鑱婂ぉ琛ㄧ殑鍚嶇О
	private static final int VERSION = 1;

	public SqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public SqliteHelper(Context context, String name) {
		super(context, name, null, VERSION);

	}


	public SqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {


				String strSQL = "create table "
						+ TABLE_NAME_CHAT
						+ "(id integer primary key autoincrement,fromid integer,toid integer,content text,time date,flag integer,freeze integer)";				
		db.execSQL(strSQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
