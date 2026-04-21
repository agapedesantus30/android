package com.example.onemoretime;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Create references
        final EditText name = (EditText) findViewById(R.id.nameTxt);

        final Button insertBtn = (Button) findViewById(R.id.insertbtn);
        final Button readbtn = (Button) findViewById(R.id.readbtn);

        //create onclicklistener
        insertBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Read values from textboxes
                String nameStr = name.getText().toString();

                //Write in the database
                SQLiteDatabase db;
                Cursor c;

                //Open connection to DB
                db = openOrCreateDatabase("STUDENTS", MODE_PRIVATE, null);


                //Create Students table or Open its connection
                db.execSQL("CREATE TABLE IF NOT EXISTS STUDENTS(College VARCHAR, Name VARCHAR, Mobile VARCHAR);");

                //Create ContentValues
                ContentValues values = new ContentValues();
                values.put("Name", nameStr);

                //Insert in the database
                db.insert("STUDENTS", null, values);

                Toast.makeText(getApplicationContext(),
                        "Record inserted successfully", Toast.LENGTH_SHORT).show();

            }
        });

        readbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Write in the database
                SQLiteDatabase db;
                Cursor c;

                //Open connection to DB
                db = openOrCreateDatabase("STUDENTS", MODE_PRIVATE, null);

                //now read from the database
                c = db.rawQuery("Select * from STUDENTS", null);

                //move cursor to first position
                c.moveToFirst();

                String studentName;
                while(c.isAfterLast() == false)
                {
                    studentName = c.getString(c.getColumnIndex("Name"));
                    Toast.makeText(getApplicationContext(), studentName, Toast.LENGTH_SHORT).show();
                    c.moveToNext();

                }

            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
