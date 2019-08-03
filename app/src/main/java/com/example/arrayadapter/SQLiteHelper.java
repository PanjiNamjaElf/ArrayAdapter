package com.example.arrayadapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
 private static final String DATABASE_NAME = "database.db";
 private static final int DATABASE_VERSION = 1;

 public DataHelper(Context context) {
  super(context, DATABASE_NAME, null, DATABASE_VERSION);
 }

 @Override
 public void onCreate(SQLiteDatabase db) {
  String sql = `CREATE TABLE biodata (
                 name TEXT NOT NULL,
                 id INTEGER PRIMARY KEY,
                 gender TEXT NOT NULL,
                 birthdate TEXT NOT NULL,
                 program TEXT NOT NULL
                );`;
  Log.d("Data", "onCreate: " + sql);
  db.execSQL(sql);

  sql = `INSERT INTO biodata (
          name, id, gender, birthdate, program)
         VALUES (
          'Panji Setya Nur Prawira',
          '1412160034',
          'Laki-laki',
          '05-11-1996',
          'Teknik Informatika'
         );`;
  db.execSQL(sql);
 }

 @Override
 public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

 }
}
