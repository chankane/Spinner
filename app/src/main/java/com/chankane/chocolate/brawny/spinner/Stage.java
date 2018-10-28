package com.chankane.chocolate.brawny.spinner;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

class Stage {

    //static final int RADIUS = 100;
    private static final int CENTER_X = 550;
    private static final int CENTER_Y = 850;

    ArrayList<Circle> temp = new ArrayList<Circle>();

    Circle[] read() {
        Circle[] ret = generate();
        for (int i=0; i<ret.length; i++) {
            ret[i].position.x *= Circle.RADIUS;
            ret[i].position.x += CENTER_X;
            ret[i].position.y *= Circle.RADIUS;
            ret[i].position.y += CENTER_Y;
        }
        return ret;
    }

    private Circle[] generate() {
        Circle topCenter = new Circle(new Vector2(0, -1 - (float)Math.sqrt(3)), Color.RED, true);
        Circle bottomCenter = new Circle(new Vector2(0, 1 + (float)Math.sqrt(3)), Color.GREEN, true);
        temp.add(topCenter);
        temp.add(bottomCenter);
        for(int i=0; i<6; i++){
            double dig = Math.toRadians(60 * i);
            temp.add(new Circle(new Vector2(topCenter.position.x + 2 * (float)Math.cos(dig), topCenter.position.y + 2 * (float)Math.sin(dig)), topCenter.getColor(), false));
        }
        for(int i=0; i<6; i++) {
            double dig = Math.toRadians(60 * i);
            temp.add(new Circle(new Vector2(bottomCenter.position.x + 2 * (float)Math.cos(dig), bottomCenter.position.y + 2 * (float)Math.sin(dig)), bottomCenter.getColor(), false));
        }
        for(int i=0; i<12; i++) {
            double dig = Math.toRadians(30 * i + 15);
            float dist = (float)Math.sqrt(2) + (float)Math.sqrt(6);
            Circle c = new Circle(new Vector2(topCenter.position.x + dist * (float)Math.cos(dig), topCenter.position.y + dist * (float)Math.sin(dig)), Color.GRAY, false);
            if(!isOverlap(c)) {
                temp.add(c);
            }
        }
        for(int i=0; i<12; i++){
            double dig = Math.toRadians(30 * i + 15);
            float dist = (float)Math.sqrt(2) + (float)Math.sqrt(6);
            Circle c = new Circle(new Vector2(bottomCenter.position.x + dist * (float)Math.cos(dig), bottomCenter.position.y + dist * (float)Math.sin(dig)), Color.GRAY, false);
            if(!isOverlap(c)) {
                temp.add(c);
            }
        }
        Log.d("tag", String.valueOf(temp.size()));
        return temp.toArray(new Circle[0]);
    }

    private boolean isOverlap(Circle circle) {
        for(Circle e: temp) {
            if((e.position.x - circle.position.x) * (e.position.x - circle.position.x) + (e.position.y - circle.position.y) * (e.position.y - circle.position.y) <= 1) {
                return true;
            }
        }
        return false;
    }
}
