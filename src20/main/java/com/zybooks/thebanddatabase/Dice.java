package com.zybooks.thebanddatabase;

public class Dice {
    private int mId;
    private String mName;
    private String mCalculation;

    public Dice() {}

    public Dice(int id, String name, String calculation) {
        mId = id;
        mName = name;
        mCalculation = calculation;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getCalculation() {
        return mCalculation;
    }

    public void setCalculation(String calculation) {
        this.mCalculation = calculation;
    }
}
