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
        float cross = Vector2.crossProduct(new Vector2(endX - startX, endY - startY), new Vector2(tmp.position.x - startX, tmp.position.y - startY));
        if(cross > 0) {
            rotR(tmp);
        }else {
            rotL(tmp);
        }
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
            canvas.drawCircle(e.position.x, e.position.y, Circle.RADIUS, paint);
        }
        paint.setColor(Color.WHITE);
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    private Circle findNearCenter(float x, float y) {
        if(Vector2.calcSqrDistance(x, y, centers[0].position.x, centers[0].position.y) < Vector2.calcSqrDistance(x, y, centers[1].position.x, centers[1].position.y)) {
            return centers[0];
        }else {
            return centers[1];
        }
    }

    private void rotR(Circle center) {
        final int deg = 60;
        for (Circle e: circles) {
            if (e == center) { continue; }
            if (Vector2.calcSqrDistance(center.position.x, center.position.y, e.position.x, e.position.y) <= 16 * Circle.RADIUS * Circle.RADIUS) {
                e.position.x -= center.position.x;
                e.position.y -= center.position.y;
                float x = e.position.x;
                float y = e.position.y;
                e.position.x = x * (float)Math.cos(Math.toRadians(deg)) - y * (float)Math.sin(Math.toRadians(deg));
                e.position.y = x * (float)Math.sin(Math.toRadians(deg)) + y * (float)Math.cos(Math.toRadians(deg));
                e.position.x += center.position.x;
                e.position.y += center.position.y;
            }
        }
    }

    private void rotL(Circle center) {
        final int deg = -60;
        for (Circle e: circles) {
            if (e == center) { continue; }
            if (Vector2.calcSqrDistance(center.position.x, center.position.y, e.position.x, e.position.y) <= 16 * Circle.RADIUS * Circle.RADIUS) {
                e.position.x -= center.position.x;
                e.position.y -= center.position.y;
                float x = e.position.x;
                float y = e.position.y;
                e.position.x = x * (float)Math.cos(Math.toRadians(deg)) - y * (float)Math.sin(Math.toRadians(deg));
                e.position.y = x * (float)Math.sin(Math.toRadians(deg)) + y * (float)Math.cos(Math.toRadians(deg));
                e.position.x += center.position.x;
                e.position.y += center.position.y;
            }
        }
    }
}
