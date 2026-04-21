package edu.ggc.lutz.dicenotation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SQLite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);


        //Create references
        final EditText notation = findViewById(R.id.editNotation);
        final Button insertBtn = findViewById(R.id.writeBtn);
        final Button readBtn = findViewById(R.id.readBtn);

        //create onClickListener
        insertBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Read values from EditText
                String notationStr = notation.getText().toString();

                //Write in the database
                SQLiteDatabase db;
                Cursor c;

                //Open connection to DB
                db = openOrCreateDatabase("NOTATION", MODE_PRIVATE, null);

                //Create Notation table or Open its connection
                db.execSQL("CREATE TABLE IF NOT EXISTS NOTATION(Dice VARCHAR);");

                //Create ContentValues
                ContentValues values = new ContentValues();
                values.put("Dice", notationStr);

                //Insert in the database
                db.insert("NOTATION", null, values);

                Toast.makeText(getApplicationContext(),
                        "Record inserted successfully", Toast.LENGTH_LONG).show();

            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Write in the database
                SQLiteDatabase db;
                Cursor c;

                //Open connection to DB
                db = openOrCreateDatabase("NOTATION", MODE_PRIVATE, null);

                //now read from the database
                c = db.rawQuery("Select * from NOTATION", null);

                //move cursor to first position
                c.moveToFirst();

                String notationName;
                while(c.isAfterLast() == false)
                {
                    notationName = c.getString(c.getColumnIndex("Dice"));
                    Toast.makeText(getApplicationContext(), notationName, Toast.LENGTH_LONG).show();
                    c.moveToNext();

                }

            }
        });

    }
}
