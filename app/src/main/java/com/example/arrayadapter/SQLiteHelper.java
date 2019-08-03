package com.example.arrayadapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
 private static final String DATABASE_NAME = "database.db";
 public static final String DATABASE_TABLE = "biodata";
 private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (name TEXT NOT NULL, id TEXT NOT NULL, gender TEXT NOT NULL, birthdate TEXT NOT NULL, program TEXT NOT NULL);";
 private static final String DATABASE_INSERT = "INSERT INTO biodata (name, id, gender, birthdate, program) VALUES ('Panji Setya Nur Prawira', '1412160034', 'Laki-laki', '05-11-1996', 'Teknik Informatika');";
 private static final int DATABASE_VERSION = 1;

 public SQLiteHelper(Context context) {
  super(context, DATABASE_NAME, null, DATABASE_VERSION);
 }

 @Override
 public void onCreate(SQLiteDatabase db) {
  db.execSQL(DATABASE_CREATE);
  db.execSQL(DATABASE_INSERT);
 }

 @Override
 public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

 }
}
