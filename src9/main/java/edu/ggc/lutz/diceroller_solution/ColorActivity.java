package edu.ggc.lutz.diceroller_solution;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class ColorActivity extends AppCompatActivity {

    ImageView ivUpdate;
    SeekBar sb_red, sb_green, sb_Blue;
    int red, green, blue;

    private ImageView diceOne;
    private ImageView diceTwo;
    private ImageView diceThree;
//    private ImageView diceFour;
//    private ImageView diceFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);



        red = 255;
        green = 255;
        blue = 255;

        diceOne = findViewById(R.id.dice1);
        diceTwo = findViewById(R.id.dice2);
        diceThree = findViewById(R.id.dice3);

        ivUpdate = findViewById(R.id.iv_update);
        sb_red = findViewById(R.id.sb_Red);
        sb_Blue = findViewById(R.id.sb_Blue);
        sb_green = findViewById(R.id.sb_Green);

        sb_red.setMax(red);
        sb_red.setProgress(red);

        sb_Blue.setMax(blue);
        sb_Blue.setProgress(blue);

        sb_green.setMax(green);
        sb_green.setProgress(green);

        final Spinner spinner = findViewById(R.id.spinner);

        ivUpdate.setBackgroundColor(Color.argb(255, red, green, blue));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        red = 255; green = 0; blue = 0;
                        break;
                    case 1:
                        red = 0; green = 255; blue = 0;
                        break;
                    case 2:
                        red = 0; green = 0; blue = 255;
                        break;
                    case 3:
                        red = 0; green = 255; blue = 255;
                        break;
                    case 4:
                        red = 255; green = 255; blue = 0;
                        break;
                    case 5:
                        red = 255; green = 0; blue = 255;
                        break;
                }
                int color = Color.rgb(red, green, blue);
                ivUpdate.setBackgroundColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sb_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                red = i;
                ivUpdate.setBackgroundColor(Color.argb(255, red, green, blue));


//                diceOne.setBackgroundColor(Color.argb(255, red, green, blue));
//                diceTwo.setBackgroundColor(Color.argb(255, red, green, blue));
//                diceThree.setBackgroundColor(Color.argb(255, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sb_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                green = i;
                ivUpdate.setBackgroundColor(Color.argb(255, red, green, blue));

//                diceOne.setBackgroundColor(Color.argb(255, red, green, blue));
//                diceTwo.setBackgroundColor(Color.argb(255, red, green, blue));
//                diceThree.setBackgroundColor(Color.argb(255, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sb_Blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                blue = i;
                ivUpdate.setBackgroundColor(Color.argb(255, red, green, blue));

//                diceOne.setBackgroundColor(Color.argb(255, red, green, blue));
//                diceTwo.setBackgroundColor(Color.argb(255, red, green, blue));
//                diceThree.setBackgroundColor(Color.argb(255, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // Displaying the attributes attached to standard radio button
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final TextView tvRed = findViewById(R.id.tvRed);
        final TextView tvGreen = findViewById(R.id.tvGreen);
        final TextView tvBlue = findViewById(R.id.tvBlue);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton1 = findViewById(R.id.rbCustom);
                sb_red.setVisibility(radioButton1.getVisibility());
                sb_green.setVisibility(radioButton1.getVisibility());
                sb_Blue.setVisibility(radioButton1.getVisibility());
                tvRed.setVisibility(radioButton1.getVisibility());
                tvGreen.setVisibility(radioButton1.getVisibility());
                tvBlue.setVisibility(radioButton1.getVisibility());
            }
        });

    }

}
