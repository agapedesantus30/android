package com.zybooks.thebanddatabase;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class DiceDatabase {

    private static DiceDatabase sDiceDatabase;
    private List<Dice> mDices;

    public static DiceDatabase get(Context context) {
        if (sDiceDatabase == null) {
            sDiceDatabase = new DiceDatabase(context);
        }
        return sDiceDatabase;
    }

    // start looing from here
    private DiceDatabase(Context context) {
        mDices = new ArrayList<>();
        Resources res = context.getResources();
        String[] dices = res.getStringArray(R.array.Dices);
        String[] calculation = res.getStringArray(R.array.calculation);
        for (int i = 0; i < dices.length; i++) {
            mDices.add(new Dice(i + 1, dices[i], calculation[i]));
        }
    }

    public List<Dice> getDices() {
        return mDices;
    }

    public Dice getDice(int diceId) {
        for (Dice dice : mDices) {
            if (dice.getId() == diceId) {
                return dice;
            }
        }
        return null;
    }
}
