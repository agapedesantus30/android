package edu.ggc.lutz.dicenotation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SharedPreference extends AppCompatActivity {

    EditText calc;
    SharedPreferences sharedPreferences;
    static final String shpref = "shpref";
    private static final String calcKEY = "nameKEY";
    static final String calcKey = calcKEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        calc = findViewById(R.id.editText);

        sharedPreferences = getSharedPreferences(shpref, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(calcKey)) {
            calc.setText(sharedPreferences.getString(calcKey, ""));
        }
    }

    public void write(View v) {
        String n= calc.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(calcKey,n);
        editor.apply();
    }

    public void clear(View v) {
        calc.setText("");
    }

    public void read(View v) {
        sharedPreferences = getSharedPreferences(shpref, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(calcKey)) {
            calc.setText(sharedPreferences.getString(calcKey, ""));
        }
    }
}
