package com.chankane.chocolate.brawny.spinner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

class MainView extends View {

    private Paint paint;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private Circle[] centers;
    private Circle[] circles;

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
    }

    public void set(float startX, float startY, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        Circle tmp = findNearCenter(startX, startY);
        rotR(tmp);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final float StrokeWidth = 10.0f;

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
        //canvas.drawCircle((float)centers[0].getX(), (float)centers[0].getY(), Circle.RADIUS, paint);
        //canvas.drawCircle((float)centers[1].getX(), (float)centers[1].getY(), Circle.RADIUS, paint);
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    private Circle findNearCenter(float x, float y) {
        if(calcSqrDistance(x, y, centers[0].getX(), centers[0].getY()) < calcSqrDistance(x, y, centers[1].getX(), centers[1].getY())) {
            return centers[0];
        }else {
            return centers[1];
        }
    }

    private void rotR(Circle center) {
        final int deg = 60;
        for (Circle e: circles) {
            if (e == center) { continue; }
            if (calcSqrDistance(center.getX(), center.getY(), e.getX(), e.getY()) <= 16 * Circle.RADIUS * Circle.RADIUS) {
                e.setX(e.getX() - center.getX());
                e.setY(e.getY() - center.getY());
                double x = e.getX();
                double y = e.getY();
                e.setX(x * Math.cos(Math.toRadians(deg)) - y * Math.sin(Math.toRadians(deg)));
                e.setY(x * Math.sin(Math.toRadians(deg)) + y * Math.cos(Math.toRadians(deg)));
                e.setX(e.getX() + center.getX());
                e.setY(e.getY() + center.getY());
            }
        }
    }

    private void rotL(Circle center) {
        final int deg = 60;
        for (Circle e: circles) {
            if (e == center) { continue; }
            if (calcSqrDistance(center.getX(), center.getY(), e.getX(), e.getY()) <= 16 * Circle.RADIUS * Circle.RADIUS) {
                e.setX(e.getX() - center.getX());
                e.setY(e.getY() - center.getY());
                double x = e.getX();
                double y = e.getY();
                e.setX(x * Math.cos(Math.toRadians(deg)) - y * Math.sin(Math.toRadians(deg)));
                e.setY(x * Math.sin(Math.toRadians(deg)) + y * Math.cos(Math.toRadians(deg)));
                e.setX(e.getX() + center.getX());
                e.setY(e.getY() + center.getY());
            }
        }
    }

    static double calcSqrDistance(double x0, double y0, double x1, double y1) {
        return (x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1);
    }
}
