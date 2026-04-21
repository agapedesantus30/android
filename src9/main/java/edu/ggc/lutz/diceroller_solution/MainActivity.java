package edu.ggc.lutz.diceroller_solution;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements RollLengthDialogFragment.OnRollLengthSelectedListener {

    private static String TAG = "Dice";
    public static final int MAX_DICE = 3;

    private int mVisibleDice;
    private Dice[] mDice;
    private ImageView[] mDiceImageViews;
    private Menu mMenu;
    private CountDownTimer mTimer;
    private int mTimerLength = 2000;
    private int currentDice;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an array of Dice
        mDice = new Dice[MAX_DICE];
        for (int i = 0; i < MAX_DICE; i++) {
            mDice[i] = new Dice(i + 1);
        }

        // Create an array of ImageViews
        mDiceImageViews = new ImageView[MAX_DICE];
        mDiceImageViews[0] = findViewById(R.id.dice1);
        mDiceImageViews[1] = findViewById(R.id.dice2);
        mDiceImageViews[2] = findViewById(R.id.dice3);

        // All dice are initially visible
        mVisibleDice = MAX_DICE;

        showDice();

        for (ImageView iv : mDiceImageViews)
            registerForContextMenu(iv);

        mDetector = new GestureDetectorCompat(this, new DiceGestureListener());


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

        for (int i = 0; i < mDiceImageViews.length; i++)
            if (mDiceImageViews[i] == v)
                currentDice = i;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_one:
                mDice[currentDice].addOne();
                showDice();
                return true;
            case R.id.subtract_one:
                mDice[currentDice].subtractOne();
                showDice();
                return true;

            case R.id.four:
                ImageView imageView = findViewById(R.id.dice1);
                imageView.setImageResource(R.drawable.four_one);
                return true;

            case R.id.ten:
                ImageView imageView1 = findViewById(R.id.dice3);
                imageView1.setImageResource(R.drawable.ten_ten);
                return true;

            case R.id.roll:
                rollDice(currentDice);
                showDice();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void showDice() {
        // Display only the number of dice visible
        for (int i = 0; i < mVisibleDice; i++) {
            Drawable diceDrawable;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                diceDrawable = getResources().getDrawable(mDice[i].getImageId(),
                        getApplicationContext().getTheme());
            } else {
                diceDrawable = getResources().getDrawable(mDice[i].getImageId());
            }

            mDiceImageViews[i].setImageDrawable(diceDrawable);
            mDiceImageViews[i].setContentDescription(Integer.toString(mDice[i].getNumber()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Determine which menu option was chosen
        switch (item.getItemId()) {
            case R.id.action_stop:
                mTimer.cancel();
                item.setVisible(false);
                return true;

            case R.id.action_roll:
                rollDice();
                return true;

            case R.id.action_one:
                changeDiceVisibility(1);
                showDice();
                return true;

            case R.id.action_two:
                changeDiceVisibility(2);
                showDice();
                return true;

            case R.id.action_three:
                Intent thirdIntent = new Intent(this, MainActivity.class);
                this.startActivity(thirdIntent);
                changeDiceVisibility(3);
                showDice();
                return true;

            case R.id.action_four:
                Intent fourIntent = new Intent(this, FourActivity.class);
                this.startActivity(fourIntent);
                return true;

            case R.id.action_five:
                Intent fiveIntent = new Intent(this, FiveActivity.class);
                this.startActivity(fiveIntent);
                return true;

            case R.id.action_colors:
                Intent intent = new Intent(this, ColorActivity.class);
                this.startActivity(intent);
                return true;

            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                this.startActivity(aboutIntent);
                return true;

            // Action item added to app bar
            case R.id.action_roll_length:
                FragmentManager manager = getFragmentManager();
                RollLengthDialogFragment dialog = new RollLengthDialogFragment();
                dialog.show(manager, "rollLengthDialog");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeDiceVisibility(int numVisible) {
        mVisibleDice = numVisible;

        // Make dice visible
        for (int i = 0; i < numVisible; i++) {
            mDiceImageViews[i].setVisibility(View.VISIBLE);
        }

        // Hide remaining dice
        for (int i = numVisible; i < MAX_DICE; i++) {
            mDiceImageViews[i].setVisibility(View.GONE);
        }
    }

    // overload for 0, 1 args
    private void rollDice() { rollDice(0, mVisibleDice); }
    private void rollDice(int single) { rollDice(single, single+1); }


    private void rollDice(final int start, final int end) {
        mMenu.findItem(R.id.action_stop).setVisible(true);
        mMenu.findItem(R.id.action_roll).setVisible(false);
        mMenu.findItem(R.id.action_roll_length).setVisible(false);

        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new CountDownTimer(mTimerLength, 100) {
            public void onTick(long millisUntilFinished) {
                for (int i = start; i < end; i++) {
                    mDice[i].roll();
                }
                showDice();
            }

            public void onFinish() {
                mMenu.findItem(R.id.action_stop).setVisible(false);
                mMenu.findItem(R.id.action_roll).setVisible(true);
                mMenu.findItem(R.id.action_roll_length).setVisible(true);
            }
        }.start();
    }

    @Override
    public void onRollLengthClick(int which) {
        // Convert to milliseconds
        mTimerLength = 1000 * (which + 1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class DiceGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            rollDice();
            return true;
        }
    }

}

