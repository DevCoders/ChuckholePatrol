package com.project.chuckholepatrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DatabaseConnector {
	
	private static final String DB_NAME = "Chuckholes";
	private SQLiteDatabase database;
	private DatabaseOpenHelper dbOpenHelper;
	   
	public DatabaseConnector(Context context) {
		dbOpenHelper = new DatabaseOpenHelper(context, DB_NAME, null, 1);
		boolean table_exists = dbOpenHelper.isTableExists("data");
		if(!table_exists)
		{
			open();
			String createQuery = "CREATE TABLE data (_id integer primary key autoincrement, lat, lng, time, x, y, z);";                 
	        database.execSQL(createQuery);
	        close();
		}
	}
	
	   public void open() throws SQLException 
	   {
	      //open database in reading/writing mode
	      database = dbOpenHelper.getWritableDatabase();
	   } 

	   public void close() 
	   {
	      if (database != null)
	         database.close();
	   }	   
	   
	   public void insertContact(String[] data) 
			   {
			      ContentValues newCon = new ContentValues();
			      newCon.put("lat", data[0]);
			      newCon.put("lng", data[1]);
			      newCon.put("time", data[2]);
			      newCon.put("x", data[3]);
			      newCon.put("y", data[4]);
			      newCon.put("z", data[5]);

			      open();
			      database.insert("data", null, newCon);
			      close();
			   }

			
			 /*  public void updateContact(long id, String name, String cap,String code) 
			   {
			      ContentValues editCon = new ContentValues();
			      editCon.put("name", name);
			      editCon.put("cap", cap);
			      editCon.put("code", code);

			      open();
			      database.update("country", editCon, "_id=" + id, null);
			      close();
			   }*/

			  
			   public Cursor getAllData() 
			   {
			      return database.query("data", new String[] {"_id", "lat", "lng", "time", "x", "y", "z"}, 
			         null, null, null, null, "time");
			   }

			/*   public Cursor getOneContact(long id) 
			   {
			      return database.query("country", null, "_id=" + id, null, null, null, null);
			   }*/
			   
			  public void deleteData() 
			   {
			      open(); 
			      database.execSQL("DROP TABLE IF EXISTS data");
			      close();
			   }
}
