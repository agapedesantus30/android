package com.example.sharedpref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    EditText name, email;
    SharedPreferences sharedPreferences;
    static final String shpref = "shpref";
    static final String nameKey = "nameKEY";
    static final String emailKey = "emailKEY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.editText);
        email = findViewById(R.id.editText2);

        sharedPreferences = getSharedPreferences(shpref, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(nameKey)) {
            name.setText(sharedPreferences.getString(nameKey, ""));
        }
        if (sharedPreferences.contains(emailKey)) {
            email.setText(sharedPreferences.getString(emailKey, ""));
        }

    }

    public void save(View v) {
        String n= name.getText().toString();
        String e= email.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(nameKey,n);
        editor.putString(emailKey,e);
        editor.apply();
    }

    public void clear(View v) {
        name.setText("");
        email.setText("");
    }

    public void retrive(View v) {
        sharedPreferences = getSharedPreferences(shpref, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(nameKey)) {
                    name.setText(sharedPreferences.getString(nameKey, ""));
            }
            if (sharedPreferences.contains(emailKey)) {
                email.setText(sharedPreferences.getString(emailKey, ""));
            }
    }
}
