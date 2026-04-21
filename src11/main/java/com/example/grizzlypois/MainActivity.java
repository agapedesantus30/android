package com.example.grizzlypois;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Grizzly POI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                Log.v(TAG, "First floor button clicked... ");
            }
        });

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbGrizzly1 = findViewById(R.id.rbGrizzly);
                RadioButton rbLibrary2 = findViewById(R.id.rbLibrary);
                RadioButton rbAthletic3 = findViewById(R.id.rbAthletic);
                ImageView imageView = findViewById(R.id.updateImage);
                switch (checkedId) {
                    case R.id.rbGrizzly:
                        imageView.setImageResource(R.drawable.grizzly);
                        Log.v(TAG, "Grizzly Statue button clicked... ");
                        break;
                    case R.id.rbLibrary:
                        imageView.setImageResource(R.drawable.library);
                        Log.v(TAG, "Library button clicked... ");
                        break;
                    case R.id.rbAthletic:
                        imageView.setImageResource(R.drawable.athletics_complex);
                        Log.v(TAG, "Athletic Complex button clicked... ");
                        break;
                    default:
                        break;
                }
            }

        });

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}