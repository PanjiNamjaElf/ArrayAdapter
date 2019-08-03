package com.example.arrayadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
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

 EditText etName, etID;
 RadioGroup rgGender;
 RadioButton rbGender;
 TextView tvBirthdate;
 Button bDatePicker, bSave;
 Spinner spProgram;
 ArrayList<Person> pList = new ArrayList<>();
 ListView lvData;
 Toast msgToast;

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

  // Create the Person objects
  // Person john = new Person("John","12-20-1998","Male");

  // Add the person objects to an ArrayList
  // ArrayList<Person> peopleList = new ArrayList<>();
  // peopleList.add(john);

  // PersonListAdapter adapter = new PersonListAdapter(this, R.layout.adapter_view_layout, peopleList);
  // lvData.setAdapter(adapter);

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
   Person pData = new Person(Name, Id, Gender, Birthdate, Program);

   pList.add(pData);

   PersonListAdapter pAdapter = new PersonListAdapter(this, R.layout.adapter_view_layout, pList);
   lvData.setAdapter(pAdapter);

   ((EditText) findViewById(R.id.etName)).getText().clear();
   ((EditText) findViewById(R.id.etID)).getText().clear();

   rgGender.clearCheck();
   tvBirthdate.setText(null);
   spProgram.setSelection(0);

   displayToast("Berhasil menambahkan data");
  }
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
