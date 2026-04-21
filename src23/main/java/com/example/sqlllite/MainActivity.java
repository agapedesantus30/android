package com.example.sqlllite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private EditText editText;
    private Button button;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

       editText = findViewById(R.id.editTextName);
       button = findViewById(R.id.button);
       listView = findViewById(R.id.listView);

       showUser();

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               addUser();
           }
       });
    }

    private void showUser() {
        Cursor cursor = db.readStoredCalculation();

        if (cursor.moveToFirst()) {
            String[] users = new String[cursor.getCount()];
            int i = 0;
            do {
                users[i] = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_NAME));
                i++;
            } while (cursor.moveToNext());
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, users);
            listView.setAdapter(adapter);
        }
    }

    private void addUser() {
        String name = editText.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please Enter a name", Toast.LENGTH_LONG).show();
            return;
        }

        if (db.addUser(name)) {
            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User not Added", Toast.LENGTH_LONG).show();
        }
    }
}
