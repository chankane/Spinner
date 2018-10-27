package com.chankane.chocolate.brawny.spinner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class MainView extends View {

    private Paint paint;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private Circle[] centers;
    private Circle[] circles;

    // 描画するラインの太さ
    private float StrokeWidth = 10.0f;

    /*// タッチイベントを処理するためのインタフェース
    private GestureDetector gestureDetector;*/

    private void init() {
        circles = new Stage().read();
        centers = new Circle[2];
        int i = 0;
        for (Circle e: circles) {
            if (e.isCenter()) {
                centers[i++] = e;
            }
        }
    }

    public MainView(Context context) {
        super(context);
        init();
        paint = new Paint();
        /*gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            // フリックイベント
            @Override
            public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
                startX = event1.getX();
                startY = event1.getY();
                endX = event2.getX();
                endY = event2.getY();
                Log.d("tag", "onFling: ");
                invalidate();
                return false;
            }
        });*/
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("tag", "onTouchEvent: ");
        return gestureDetector.onTouchEvent(event);
    }*/

    public void set(float startX, float startY, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        Circle tmp = findNearCenter(startX, startY);
        tmp.setColor(Color.WHITE - tmp.getColor());
        invalidate();
    }

    /*@Override
    protected void onDraw(Canvas canvas) {
        paint.setStrokeWidth(StrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        for (Circle e : circles) {
            if (e.isCenter()) {
                paint.setStyle(Paint.Style.FILL);
            } else {
                paint.setStyle(Paint.Style.STROKE);
            }
            paint.setColor(e.getColor());
            canvas.drawCircle((float) (CENTER.x + RADIUS * e.getX()), (float) (CENTER.y + RADIUS * e.getY()), RADIUS, paint);
        }
        paint.setColor(Color.WHITE);
        canvas.drawCircle((float) (CENTER.x + RADIUS * centers[0].getX()), (float) (CENTER.y + RADIUS * centers[0].getY()), RADIUS, paint);
        canvas.drawCircle((float) (CENTER.x + RADIUS * centers[1].getX()), (float) (CENTER.y + RADIUS * centers[1].getY()), RADIUS, paint);
        canvas.drawLine(startX, startY, endX, endY, paint);
    }*/
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStrokeWidth(StrokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        for (Circle e : circles) {
            if (e.isCenter()) {
                paint.setStyle(Paint.Style.FILL);
            } else {
                paint.setStyle(Paint.Style.STROKE);
            }
            paint.setColor(e.getColor());
            canvas.drawCircle((float)e.getX(), (float)e.getY(), Circle.RADIUS, paint);
        }
        paint.setColor(Color.WHITE);
        canvas.drawCircle((float)centers[0].getX(), (float)centers[0].getY(), Circle.RADIUS, paint);
        canvas.drawCircle((float)centers[1].getX(), (float)centers[1].getY(), Circle.RADIUS, paint);
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    private Circle findNearCenter(float x, float y) {
        //(Math.sqrt(3) + 1) * RADIUS;
        Log.d(",.", String.valueOf(centers[0].getY()));
        if(calcSqrDistance(x, y, centers[0].getX(), centers[0].getY()) < calcSqrDistance(x, y, centers[1].getX(), centers[1].getY())) {
            return centers[0];
        }else {
            return centers[1];
        }
    }

    static double calcSqrDistance(double x0, double y0, double x1, double y1) {
        return (x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1);
    }
}
