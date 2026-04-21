package edu.ggc.lutz.a10sep2019navup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ChooserActivity extends AppCompatActivity {

    private SeekBar seekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seekbar = findViewById(R.id.sbGrayScale);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                for (int i = 0; i < 10; i++) {
                    int j = i * 2;
                    Log.v("NavUp", "i=" + i);
                }
                Intent intent = getIntent();
                intent.putExtra("prog", seekbar.getProgress());
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
