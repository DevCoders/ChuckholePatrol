package com.project.chuckholepatrol;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	public DatabaseOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE data (_id integer primary key autoincrement, lat, lng, time, x, y, z);";                 
        db.execSQL(createQuery);	
        
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
		//boolean table_exists = isTableExists(tableName, false);
		//if(!table_exists)
		//{
		///	onCreate(db);
		//}
	}

	public boolean isTableExists(String tableName) {
	    SQLiteDatabase db;
	        
	    db = getReadableDatabase();	      

	    Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ tableName +"'", null);
	    if(cursor!=null) {
	        if(cursor.getCount()>0) {
	                            cursor.close();
	                            db.close();
	            return true;
	        }
	                    cursor.close();
	                    db.close();
	    }
	    return false;
	}
}
