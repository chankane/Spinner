package com.chankane.chocolate.brawny.spinner;

import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    private GestureDetector gestureDetector;
    MainView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view = new MainView(this));
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            // フリックイベント
            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
                view.set(event1.getX(), event1.getY(), event2.getX(), event2.getY());
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}
