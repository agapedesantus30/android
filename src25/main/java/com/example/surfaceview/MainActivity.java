package com.example.surfaceview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textview = findViewById(R.id.text);
//        textview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //makes app restart
//                Intent i = getBaseContext().getPackageManager().
//                getLaunchIntentForPackage(getBaseContext().getPackageName());
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//                finish();
//            }
//        });
    }
}
