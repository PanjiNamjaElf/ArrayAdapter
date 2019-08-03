package com.example.arrayadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Main extends AppCompatActivity {
 private static final String TAG = "Main";
 private DatePickerDialog datePickerDialog;
 private SimpleDateFormat dateFormatter;
 private int RadioID;
 private long BackPressedTime;
 private String[] dbList;
 protected Cursor cursor;


 EditText etName, etID;
 RadioGroup rgGender;
 RadioButton rbGender;
 TextView tvBirthdate;
 Button bDatePicker, bSave;
 ArrayList<Person> pList = new ArrayList<>();
 Spinner spProgram;
 ListView lvData;
 Toast msgToast;
 SQLiteHelper DB;
 SQLiteDatabase sqlDB;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main);

  etName      = (EditText) findViewById(R.id.etName);
  etID        = (EditText) findViewById(R.id.etID);
  rgGender    = (RadioGroup) findViewById(R.id.rgGender);
  tvBirthdate = (TextView) findViewById(R.id.tvBirthdate);
  bDatePicker = (Button) findViewById(R.id.bDatePicker);
  spProgram   = (Spinner) findViewById(R.id.spProgram);
  bSave       = (Button) findViewById(R.id.bSave);
  lvData      = (ListView) findViewById(R.id.lvData);

  dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

  DB = new SQLiteHelper(this);

  bDatePicker.setOnClickListener(new View.OnClickListener() {
   public void onClick(View view) {
    displayDatePicker();
   }
  });

  bSave.setOnClickListener(new View.OnClickListener() {
   public void onClick(View v) {
    saveData();
   }
  });

  refreshDB();
 }

 public void displayDatePicker() {
  Calendar newCalendar = Calendar.getInstance();

  datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    Calendar newDate = Calendar.getInstance();
    newDate.set(year, monthOfYear, dayOfMonth);

    tvBirthdate.setText(dateFormatter.format(newDate.getTime()));
   }
  }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

  datePickerDialog.show();
 }

 public void saveData() {
  String Name      = etName.getText().toString(),
         Id        = etID.getText().toString(),
         Gender    = null,
         Birthdate = tvBirthdate.getText().toString(),
         Program   = String.valueOf(spProgram.getSelectedItem());

  if (rgGender.getCheckedRadioButtonId() > 0) {
   RadioID  = rgGender.getCheckedRadioButtonId();
   rbGender = (RadioButton) rgGender.findViewById(RadioID);
   Gender   = (String) rbGender.getText();
  }

  if (pList.contains(Name)) {
   displayToast("Item sudah berada di array");
  }

  else if ((Name == null || Name.trim().equals(""))
        || (Id == null || Id.trim().equals(""))
        || (Gender == null || Gender.trim().equals(""))
        || (Birthdate == null || Birthdate.trim().equals(""))
        || (Program == null || Program.trim().equals(""))) {

   displayToast("Data yang dimasukkan tidak boleh kosong");
  }

  else {
   // Person pData = new Person(Name, Id, Gender, Birthdate, Program);

   // pList.add(pData);

   // PersonListAdapter pAdapter = new PersonListAdapter(this, R.layout.adapter_view_layout, pList);
   // lvData.setAdapter(pAdapter);

   sqlDB = DB.getWritableDatabase();
   sqlDB.execSQL("INSERT INTO " + DB.DATABASE_TABLE + "(name, id, gender, birthdate, program) VALUES ('" + Name + "', '" + Id + "', '" + Gender + "', '" + Birthdate + "', '" + Program + "');");

   ((EditText) findViewById(R.id.etName)).getText().clear();
   ((EditText) findViewById(R.id.etID)).getText().clear();

   rgGender.clearCheck();
   tvBirthdate.setText(null);
   spProgram.setSelection(0);

   displayToast("Berhasil menambahkan data");

   refreshDB();
  }
 }

 public void refreshDB() {
  String Name, Id, Gender, Birthdate, Program;

  sqlDB = DB.getReadableDatabase();

  cursor = sqlDB.rawQuery("SELECT * FROM " + DB.DATABASE_TABLE, null);

  pList.clear();

  if (cursor.moveToFirst()) {
   while (!cursor.isAfterLast()) {
    // String name = cursor.getString(cursor.getColumnIndex(countyname));

    Name      = cursor.getString(0).toString();
    Id        = cursor.getString(1).toString();
    Gender    = cursor.getString(2).toString();
    Birthdate = cursor.getString(3).toString();
    Program   = cursor.getString(4).toString();

    Person pData = new Person(Name, Id, Gender, Birthdate, Program);

    pList.add(pData);

    cursor.moveToNext();
   }
  }

  // cursor.moveToFirst();

  /* Name      = cursor.getString(0).toString();
  Id        = cursor.getString(1).toString();
  Gender    = cursor.getString(2).toString();
  Birthdate = cursor.getString(3).toString();
  Program   = cursor.getString(4).toString(); */

  PersonListAdapter pAdapter = new PersonListAdapter(this, R.layout.adapter_view_layout, pList);
  lvData.setAdapter(pAdapter);
 }

 public void onBackPressed() {
  if (BackPressedTime + 2000 > System.currentTimeMillis()) {
   msgToast.cancel();
   super.onBackPressed();
   return;
  }

  else {
   msgToast = Toast.makeText(getBaseContext(), "Tekan tombol kembali lagi untuk keluar", Toast.LENGTH_LONG);
   msgToast.show();
  }

  BackPressedTime = System.currentTimeMillis();
 }

 public void displayToast(String message) {
  Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
 }
}
