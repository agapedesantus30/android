package com.example.agape.palindrapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public static final String PALINDROME = "Palindrome";
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button check = findViewById(R.id.checkButton);
         edit = findViewById(R.id.textEditor);
        final TextView tete = findViewById(R.id.trueOrFalse);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = edit.getText().toString();

                tete.setText((Palindrome.check(s)) ? "Is a Palindrome" :
                "Not a Palindrome");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(PALINDROME,"Pausing... ");
//        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        String s = edit.getText().toString();
//        editor.putString("input",s);
//        editor.apply();

        try {
            FileOutputStream fileOutputStream = openFileOutput("data.txt",MODE_PRIVATE);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            String s = edit.getText().toString();
            printWriter.println(s);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(PALINDROME,"Resuming... ");
//        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
//        String sa = prefs.getString("edit", "");
//        edit.setText(sa);

        try {
            FileInputStream fileInputStream = openFileInput("data.txt");
            Scanner scanner = new Scanner(fileInputStream);
            StringBuilder stringBuilder = new StringBuilder();

            while (scanner.hasNext())
                stringBuilder.append(scanner.nextLine());
            String s = stringBuilder.toString();
            scanner.close();
            edit.setText(s);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
