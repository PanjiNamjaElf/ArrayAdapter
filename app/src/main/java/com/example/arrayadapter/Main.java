package com.example.arrayadapter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main extends AppCompatActivity {
 private long BackPressedTime;

 EditText etName, etID, etAddress;
 Button bSave;
 ArrayList<String> addArray = new ArrayList<String>();
 ListView lvData;
 Toast msgToast;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.main);

  etName    = (EditText) findViewById(R.id.etName);
  etID      = (EditText) findViewById(R.id.etID);
  etAddress = (EditText) findViewById(R.id.etAddress);
  bSave     = (Button) findViewById(R.id.bSave);
  lvData    = (ListView) findViewById(R.id.lvData);

  bSave.setOnClickListener(new View.OnClickListener() {
   public void onClick(View v) {
    String Name    = etName.getText().toString(),
           ID      = etID.getText().toString(),
           Address = etAddress.getText().toString();

    if (addArray.contains(Name)) {
     displayToast("Item sudah berada di array");
    }

    else if ((Name == null || Name.trim().equals(""))
          || (ID == null || ID.trim().equals("")
          || (Address == null || Address.trim().equals("")) {

     displayToast("Data yang dimasukkan tidak boleh kosong");
    }
   }
  });
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
