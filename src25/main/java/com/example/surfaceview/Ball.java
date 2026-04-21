package com.example.surfaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class Ball extends MainActivity{

    private Paint mPaint;
    private Point mCenter;
    private Point mVelocity;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private int ball;

    Random random = new Random();
    // OBJ 1 A radius is selected randomly at construction time, between 50 and 150
    private int RADIUS =  random.nextInt(101) +50;

    public Ball(int surfaceWidth, int surfaceHeight) {
        mSurfaceWidth = surfaceWidth;
        mSurfaceHeight = surfaceHeight;

        //OBJ 4 A random spawn location is picked somewhere on the screen
        mCenter = new Point();
        mCenter.set(random.nextInt(surfaceWidth), random.nextInt(surfaceHeight));

        //OBJ 2 A color is picked randomly, where r,g,b are each picked randomly from 0 .. 200
        ball = Color.argb(255, random.nextInt(201), random.nextInt(201),
                random.nextInt(201));
        mPaint = new Paint();
        mPaint.setColor(ball);

        // OBJ 3 A velocity is picked randomly, between -10 and 10
        mVelocity = new Point();
        mVelocity.set(random.nextInt(21)-10, random.nextInt(21)-10);

    }

    public void move(double elapsedTime) {

        // mVelocity around (500, 500) is ideal
        mCenter.x += mVelocity.x * elapsedTime;
        mCenter.y += mVelocity.y * elapsedTime;

        // Add velocity to ball's center
        mCenter.offset(mVelocity.x, mVelocity.y);

        // Check for top and bottom collisions
        if (mCenter.y > mSurfaceHeight - RADIUS) {
            mCenter.y = mSurfaceHeight - RADIUS;
            mVelocity.y *= -1;
        }
        else if (mCenter.y < RADIUS) {
            mCenter.y = RADIUS;
            mVelocity.y *= -1;
        }

        // Check for right and left collisions
        if (mCenter.x > mSurfaceWidth - RADIUS) {
            mCenter.x = mSurfaceWidth - RADIUS;
            mVelocity.x *= -1;
        }
        else if (mCenter.x < RADIUS) {
            mCenter.x = RADIUS;
            mVelocity.x *= -1;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawCircle(mCenter.x, mCenter.y, RADIUS, mPaint);
    }

    // helps in restarting the app
    public float getX() { return mCenter.x; }
    public float getY() { return mCenter.y; }
    public int getRad() { return RADIUS; }

    // method to help onTouch
    public void reset() {
        mCenter = new Point(random.nextInt(mSurfaceWidth), random.nextInt(mSurfaceHeight));
        mVelocity = new Point(random.nextInt(21) - 10, random.nextInt(21) - 10);

        mPaint = new Paint();
        ball = Color.argb(255, random.nextInt(201),
                random.nextInt(201), random.nextInt(201));
        mPaint.setColor(ball);

        RADIUS = random.nextInt(101) + 50;
    }
}
