package com.example.agape.westhallmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "WestHallApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button firstFloor = findViewById(R.id.first_floor);
        firstFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirstFloorActivity.class);
                startActivity(intent);
                Log.v(TAG, "First floor button clicked... ");
            }
        });

        Button secondFloor = findViewById(R.id.second_floor);
        secondFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondFloorActivity.class);
                startActivity(intent);
                Log.v(TAG, "Second floor button clicked... ");
            }
        });

        Button thirdFloor = findViewById(R.id.third_floor);
        thirdFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdFloorActivity.class);
                startActivity(intent);
                Log.v(TAG, "Third floor button clicked... ");
            }
        });

        Button campusMap = findViewById(R.id.campus_map);
        campusMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CampusMapActivity.class);
                startActivity(intent);
                Log.v(TAG, "Campus floor map button clicked... ");
            }
        });

        Button about = findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                Log.v(TAG, "About button clicked... ");
            }
        });
    }
}