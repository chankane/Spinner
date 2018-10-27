package com.chankane.chocolate.brawny.spinner;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;

class Dao {

    private static final int RADIUS = 80;

    ArrayList<Circle> temp = new ArrayList<Circle>();

    Circle[] read() {
        Circle[] ret = generate();
        for (int i=0; i<ret.length; i++) {
            ret[i].setX(ret[i].getX() * RADIUS);
            ret[i].setY(ret[i].getY() * RADIUS);
        }
        return ret;
    }

    private Circle[] generate() {
        Circle topCenter = new Circle(0, -1 - Math.sqrt(3), Color.RED, true);
        Circle bottomCenter = new Circle(0, 1 + Math.sqrt(3), Color.GREEN, true);
        temp.add(topCenter);
        temp.add(bottomCenter);
        for(int i=0; i<6; i++){
            double dig = Math.toRadians(60 * i);
            temp.add(new Circle(topCenter.getX() + 2 * Math.cos(dig), topCenter.getY() + 2 * Math.sin(dig), topCenter.getColor(), false));
        }
        for(int i=0; i<6; i++) {
            double dig = Math.toRadians(60 * i);
            temp.add(new Circle(bottomCenter.getX() + 2 * Math.cos(dig), bottomCenter.getY() + 2 * Math.sin(dig), bottomCenter.getColor(), false));
        }
        for(int i=0; i<12; i++) {
            double dig = Math.toRadians(30 * i + 15);
            double dist = Math.sqrt(2) + Math.sqrt(6);
            Circle c = new Circle(topCenter.getX() + dist * Math.cos(dig), topCenter.getY() + dist * Math.sin(dig), Color.GRAY, false);
            if(!isOverlap(c)) {
                temp.add(c);
            }
        }
        for(int i=0; i<12; i++){
            double dig = Math.toRadians(30 * i + 15);
            double dist = Math.sqrt(2) + Math.sqrt(6);
            Circle c = new Circle(bottomCenter.getX() + dist * Math.cos(dig), bottomCenter.getY() + dist * Math.sin(dig), Color.GRAY, false);
            if(!isOverlap(c)) {
                temp.add(c);
            }
        }
        Log.d("tag", String.valueOf(temp.size()));
        return temp.toArray(new Circle[0]);
    }

    private boolean isOverlap(Circle circle) {
        for(Circle e: temp) {
            if((e.getX() - circle.getX()) * (e.getX() - circle.getX()) + (e.getY() - circle.getY()) * (e.getY() - circle.getY()) <= 1) {
                return true;
            }
        }
        return false;
    }
}
