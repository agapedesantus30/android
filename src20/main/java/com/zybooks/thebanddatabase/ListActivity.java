package com.zybooks.thebanddatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ListActivity extends AppCompatActivity implements ListFragment.OnDiceSelectedListener {

    private static String KEY_DICE_ID = "diceId";
    private int mDiceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        FloatingActionButton fab = findViewById(R.id.fabPhone);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Please select a notation", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        FloatingActionButton fab2 = findViewById(R.id.fabTablet);
//        fab2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Please select a notation", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        mDiceId = -1;

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment_container);

        if (fragment == null) {
            fragment = new ListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment_container, fragment)
                    .commit();
        }

        // Replace DetailsFragment if state saved when going from portrait to landscape
        if (savedInstanceState != null && savedInstanceState.getInt(KEY_DICE_ID) != 0
                && getResources().getBoolean(R.bool.twoPanes)) {
            mDiceId = savedInstanceState.getInt(KEY_DICE_ID);
            Fragment diceFragment = DetailsFragment.newInstance(mDiceId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, diceFragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save state when something is selected
        if (mDiceId != -1) {
            savedInstanceState.putInt(KEY_DICE_ID, mDiceId);
        }
    }

    @Override
    public void onDiceSelected(int diceId) {

        mDiceId = diceId;

        if (findViewById(R.id.details_fragment_container) == null) {
            // Must be in portrait, so start activity
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_DICE_ID, diceId);
            startActivity(intent);
        } else {
            // Replace previous fragment (if one exists) with a new fragment
            Fragment diceFragment = DetailsFragment.newInstance(diceId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, diceFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Determine which menu option was chosen
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent notation = new Intent(this, DialogBox.class);
                this.startActivity(notation);
                openDialog();
                return true;

            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                this.startActivity(aboutIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openDialog() {
        DialogBox exampleDialog = new DialogBox();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

//    @Override
//    public void applyTexts(String username, String notation) {
//        enterNotation.setText(notation);
//    }
}
