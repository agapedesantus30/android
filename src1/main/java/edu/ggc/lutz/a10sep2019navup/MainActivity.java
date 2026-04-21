package edu.ggc.lutz.a10sep2019navup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 999;
    public static final String NAV_UP = "NavUp";
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.btnLaunch);
        result = findViewById(R.id.tvResult);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooserActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Log.i(NAV_UP, "we're back!");
            int progress = data.getExtras().getInt("prog");
            int color = Color.argb(255, progress, progress, progress);
            result.setText("" + progress);
            result.setBackgroundColor(color);

            Log.i(NAV_UP, "progress returned " + progress);
        }
    }
}
