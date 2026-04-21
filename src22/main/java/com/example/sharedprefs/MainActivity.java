package com.example.sharedprefs;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText enterName, studentNumber, phoneNumber;
    SharedPreferences sharedPreferences;
    static final String shpref = "shpref";
    static final String nameKey = "nameKEY";
    static final String studentKey = "studentKey";
    static final String phoneKey = "phoneKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterName = findViewById(R.id.editText);
        studentNumber = findViewById(R.id.editText2);
        phoneNumber = findViewById(R.id.editText3);

        sharedPreferences = getSharedPreferences(shpref, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(nameKey)) {
            enterName.setText(sharedPreferences.getString(nameKey, ""));
        }
        if (sharedPreferences.contains(studentKey)) {
            studentNumber.setText(sharedPreferences.getString(studentKey, ""));
        }
        if (sharedPreferences.contains(phoneKey)) {
            phoneNumber.setText(sharedPreferences.getString(phoneKey, ""));
        }

    }

    public void save(View v) {
        String n= enterName.getText().toString();
        String s= studentNumber.getText().toString();
        String p= phoneNumber.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(nameKey,n);
        editor.putString(studentKey,s);
        editor.putString(phoneKey,p);
        editor.apply();
    }

    public void clear(View v) {
        enterName.setText("");
        studentNumber.setText("");
        phoneNumber.setText("");
    }

    public void retrive(View v) {
        sharedPreferences = getSharedPreferences(shpref, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(nameKey)) {
            enterName.setText(sharedPreferences.getString(nameKey, ""));
        }
        if (sharedPreferences.contains(studentKey)) {
            studentNumber.setText(sharedPreferences.getString(studentKey, ""));
        }
        if (sharedPreferences.contains(studentKey)) {
            phoneNumber.setText(sharedPreferences.getString(phoneKey, ""));
        }
    }
}
