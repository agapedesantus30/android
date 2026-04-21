package com.example.surfaceview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BounceSurfaceView extends SurfaceView
        implements SurfaceHolder.Callback {

    private AnimationThread mAnimThread;

    public BounceSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mAnimThread = new AnimationThread(holder);
        mAnimThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mAnimThread.stopThread();
    }

    // when touched A new ball is spawned, if the current ball is killed
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();

            float ballX = mAnimThread.mBall.getX();
            float ballY = mAnimThread.mBall.getY();
            float ballRad = mAnimThread.mBall.getRad();

            if ((touchX > (ballX - ballRad) && touchX < (ballX + ballRad))
                    && (touchY > (ballY - ballRad) && touchY < (ballY + ballRad))) {
                mAnimThread.mBall.reset();
            }
        }
        return true;
    }
}