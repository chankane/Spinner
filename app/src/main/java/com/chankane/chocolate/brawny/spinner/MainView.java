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
    boolean playable = false;
    boolean solve;
    boolean clear = false;

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
        Log.d("playable", String.valueOf(playable));
        if(!playable) {
            return;
        }
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

    private boolean isClear() {
        for (Circle e: circles) {
            if (e.getColor() == centers[0].getColor()) {
                if(Vector2.calcSqrDistance(e.position.x, e.position.y, centers[0].position.x, centers[0].position.y) > 9 * Circle.RADIUS * Circle.RADIUS) {
                    return false;
                }
            }
        }
        for (Circle e: circles) {
            if (e.getColor() == centers[1].getColor()) {
                if(Vector2.calcSqrDistance(e.position.x, e.position.y, centers[1].position.x, centers[1].position.y) > 9 * Circle.RADIUS * Circle.RADIUS) {
                    return false;
                }
            }
        }
        return true;
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
        if(solve) {
            paint.setTextSize(130);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawText("Can you solve?", 100, 880, paint);
            solve = false;
        }
        if(clear) {
            paint.setTextSize(110);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawText("Congratulations :-)", 100, 880, paint);
            solve = false;
        }
    }

    void shuffle() {
        playable = false;
        new Thread(new Runnable() {
            final int move = 4;
            int old;
            @Override
            public void run() {
                for(int i=0; i<move; i++) {
                    int rand = (int)(Math.random() * 4);
                    if(rand == 0 && old == 1){
                        i--;
                        continue;
                    }
                    if(rand == 1 && old == 0){
                        i--;
                        continue;
                    }
                    if(rand == 2 && old == 3){
                        i--;
                        continue;
                    }
                    if(rand == 3 && old == 2){
                        i--;
                        continue;
                    }
                    old = rand;
                    switch (rand) {
                        case 0:
                            rotR(centers[0]);
                            break;
                        case 1:
                            rotL(centers[0]);
                            break;
                        case 2:
                            rotR(centers[1]);
                            break;
                        case 3:
                            rotL(centers[1]);
                            break;
                    }
                    try{
                        Thread.sleep(12 * 10);
                    }catch(InterruptedException e){}
                }
                MainView.this.solve = true;
                invalidate();
            }
        }).start();
    }

    private Circle findNearCenter(float x, float y) {
        if(Vector2.calcSqrDistance(x, y, centers[0].position.x, centers[0].position.y) < Vector2.calcSqrDistance(x, y, centers[1].position.x, centers[1].position.y)) {
            return centers[0];
        }else {
            return centers[1];
        }
    }

    private void rotR(final Circle center) {
        new Thread(new Runnable() {
            final int deg = 60;
            final int div = 5;
            @Override
            public void run() {
                for(int i=0; i<deg; i+=div) {
                    for (Circle e : circles) {
                        if (e == center) {
                            continue;
                        }
                        if (Vector2.calcSqrDistance(center.position.x, center.position.y, e.position.x, e.position.y) <= 16 * Circle.RADIUS * Circle.RADIUS) {
                            e.position.x -= center.position.x;
                            e.position.y -= center.position.y;
                            float x = e.position.x;
                            float y = e.position.y;
                            e.position.x = x * (float) Math.cos(Math.toRadians(div)) - y * (float) Math.sin(Math.toRadians(div));
                            e.position.y = x * (float) Math.sin(Math.toRadians(div)) + y * (float) Math.cos(Math.toRadians(div));
                            e.position.x += center.position.x;
                            e.position.y += center.position.y;
                        }
                    }
                    invalidate();
                    try{
                        Thread.sleep(6);
                    }catch(InterruptedException e){}
                }
                playable = true;
                try{
                    Thread.sleep(6);
                }catch(InterruptedException e){}
                if(isClear()){
                    clear = true;
                    invalidate();
                    playable = false;
                }
            }
        }).start();
    }

    private void rotL(final Circle center) {
        new Thread(new Runnable() {
            final int deg = 60;
            final int div = 5;
            @Override
            public void run() {
                for(int i=0; i<deg; i+=div) {
                    for (Circle e : circles) {
                        if (e == center) {
                            continue;
                        }
                        if (Vector2.calcSqrDistance(center.position.x, center.position.y, e.position.x, e.position.y) <= 16 * Circle.RADIUS * Circle.RADIUS) {
                            e.position.x -= center.position.x;
                            e.position.y -= center.position.y;
                            float x = e.position.x;
                            float y = e.position.y;
                            e.position.x = x * (float) Math.cos(Math.toRadians(-div)) - y * (float) Math.sin(Math.toRadians(-div));
                            e.position.y = x * (float) Math.sin(Math.toRadians(-div)) + y * (float) Math.cos(Math.toRadians(-div));
                            e.position.x += center.position.x;
                            e.position.y += center.position.y;
                        }
                    }
                    invalidate();
                    try{
                        Thread.sleep(6);
                    }catch(InterruptedException e){}
                }
                playable = true;
                try{
                    Thread.sleep(6);
                }catch(InterruptedException e){}
                if(isClear()){
                    clear = true;
                    invalidate();
                    playable = false;
                }
            }
        }).start();
    }
}
