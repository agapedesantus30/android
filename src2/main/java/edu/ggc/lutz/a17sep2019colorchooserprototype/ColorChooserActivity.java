package edu.ggc.lutz.a17sep2019colorchooserprototype;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ColorChooserActivity extends AppCompatActivity {

    private Spinner spinner;
    private int r,g,b,a;

    private SeekBar sbRed, sbGreen, sbBlue, sbAlpha;
    private RadioButton rbStandard, rbCustom; // not using anymore!
    private RadioGroup group;
    private TextView tvR, tvG, tvB, tvA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_chooser);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sbRed = findViewById(R.id.sbR);
        sbGreen = findViewById(R.id.sbG);
        sbBlue = findViewById(R.id.sbB);
        sbAlpha = findViewById(R.id.sbA);
        rbStandard = findViewById(R.id.rbStandard);
        rbCustom  = findViewById(R.id.rbCustom);
        tvR = findViewById(R.id.tvR);
        tvG = findViewById(R.id.tvG);
        tvB = findViewById(R.id.tvB);
        tvA = findViewById(R.id.tvA);


        SeekBar.OnSeekBarChangeListener grizzlyColorChangeListener =
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean bool) {
                        if (seekBar.equals(sbRed))
                            r = seekBar.getProgress();
                        else if (seekBar.equals(sbGreen))
                            g = seekBar.getProgress();
                        else if (seekBar.equals(sbBlue))
                            b = seekBar.getProgress();
                        else { // must be Alpha!
                            a = seekBar.getProgress();
                        }
                    }

                    @Override public void onStartTrackingTouch(SeekBar seekBar) { }
                    @Override public void onStopTrackingTouch(SeekBar seekBar) { }
                };

        sbRed.setOnSeekBarChangeListener(grizzlyColorChangeListener);
        sbGreen.setOnSeekBarChangeListener(grizzlyColorChangeListener);
        sbBlue.setOnSeekBarChangeListener(grizzlyColorChangeListener);
        sbAlpha.setOnSeekBarChangeListener(grizzlyColorChangeListener);

        group = findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                if (checked == R.id.rbStandard) {
                    spinner.setVisibility(View.VISIBLE);
                    spinner.setSelection(0); // red!
                    r = 255; g = 0; b = 0; a = 255;
                    tvR.setVisibility(View.INVISIBLE);
                    sbRed.setVisibility(View.INVISIBLE);
                    tvG.setVisibility(View.INVISIBLE);
                    sbGreen.setVisibility(View.INVISIBLE);
                    tvB.setVisibility(View.INVISIBLE);
                    sbBlue.setVisibility(View.INVISIBLE);
                    tvA.setVisibility(View.INVISIBLE);
                    sbAlpha.setVisibility(View.INVISIBLE);
                } else {
                    spinner.setVisibility(View.INVISIBLE);
                    r = sbRed.getProgress();
                    g = sbGreen.getProgress();
                    b = sbBlue.getProgress();
                    a = sbAlpha.getProgress();
                    tvR.setVisibility(View.VISIBLE);
                    sbRed.setVisibility(View.VISIBLE);
                    tvG.setVisibility(View.VISIBLE);
                    sbGreen.setVisibility(View.VISIBLE);
                    tvB.setVisibility(View.VISIBLE);
                    sbBlue.setVisibility(View.VISIBLE);
                    tvA.setVisibility(View.VISIBLE);
                    sbAlpha.setVisibility(View.VISIBLE);
                }
            }
        });

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v("PROTO", "VALUE = " + i);
                switch (i) {
                    case 0:
                        r = 255; g = 0; b = 0; a = 255;
                        break;
                    case 1:
                        r = 0; g = 255; b = 0; a = 255;
                        break;
                    case 2:
                        r = 0; g = 0; b = 255; a = 255;
                        break;
                    case 3:
                        r = 0; g = 255; b = 255; a = 255;
                        break;
                    case 4:
                        r = 255; g = 0; b = 255; a = 255;
                        break;
                    case 5:
                        r = 255; g = 255; b = 0; a = 255;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent = getIntent();
                int col = Color.argb(a, r, g, b);
                intent.putExtra("color", col);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
