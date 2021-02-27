package com.company.mysqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class MyDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "mydatabase";

    private static final String TABLE_NAME = "Student";

    public MyDatabase(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (StudentID VARCHAR(15) PRIMARY KEY, "
                + " Name TEXT(100)," + " Tel TEXT(20));");
        Log.d("Create Table","Create table Successfully");

    }
    public long insertData(String studentId, String name, String tel){
        try{
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            ContentValues val = new ContentValues();
            val.put("StudentId",studentId);
            val.put("Name",name);
            val.put("Tel",tel);
            long rows = db.insert(TABLE_NAME,null,val);
            db.close();
            return rows;

        }catch(Exception e){
            return -1;
        }
    }
    public ArrayList<HashMap<String, String>>SelectAllData(){
        try {
            ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            SQLiteDatabase db;
            db = this.getReadableDatabase();

            String strSQL = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(strSQL,null);

            if (cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        map = new HashMap<String, String>();
                        map.put("StudentId",cursor.getString(0));
                        map.put("Name",cursor.getString(1));
                        map.put("Tel",cursor.getString(2));
                        MyArrList.add(map);
                    }while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            return MyArrList;
        }catch(Exception e){
            return null;
        }
    }
    public long DeleteData(String StdId){
        try{
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            long rows = db.delete(TABLE_NAME, "StudentID =?",
                    new String[] { String.valueOf(StdId) } );
            db.close();
            return rows;

        }catch(Exception e){
            return -1;
        }
    }
    public long UpdateData(String StdId, String name, String tel){
        try{
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            String where = "StudentID =?";
            String[] whereArgs = new String[] {String.valueOf(StdId)};
            ContentValues Val = new ContentValues();

            Val.put("Name",name);
            Val.put("Tel",tel);
            long rows = db.update(TABLE_NAME, Val, where, whereArgs);
            db.close();
            return rows;

        }catch(Exception e){
            return -1;
        }
    }
    // Search
    public ArrayList<HashMap<String, String>> SearchData(String keyword) {
        try {
            String tag_name = "Name",
                    tag_id = "StudentID",
                    tag_tel = "Tel";
            ArrayList<HashMap<String, String>> MyArrList = new ArrayList();
            HashMap<String, String> map ;
            SQLiteDatabase db;
            db = this.getReadableDatabase(); // Read Data
            String strSQL = "SELECT * FROM " + TABLE_NAME
                    + " WHERE " +tag_name+ " Like " +
                    "'%"+keyword+"%'"
                    + " OR " +tag_id+ " Like " +
                    "'%"+keyword+"%'" +
                    " OR " +tag_tel+ " Like " +
                    "'%"+keyword+"%'";
            Cursor cursor = db.rawQuery(strSQL, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        map = new HashMap<String, String>();
                        map.put("StudentID", cursor.getString(0));
                        map.put("Name", cursor.getString(1));
                        map.put("Tel", cursor.getString(2));
                        MyArrList.add(map);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
            return MyArrList;

        } catch (Exception e) {
            return null;
        }
    }
        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
