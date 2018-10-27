package com.chankane.chocolate.brawny.spinner;

import android.graphics.Color;

class Circle {

    static final int RADIUS = 100;
    private double x;
    private double y;
    private int color;
    private boolean isCenter;

    Circle(double x, double y, int color, boolean isCenter) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isCenter = isCenter;
    }

    double getX() {
        return x;
    }
    void setX(double x) { this.x = x; }

    double getY() {
        return y;
    }
    void setY(double y) { this.y = y; }

    int getColor() {
        return color;
    }
    void setColor(int color) { this.color = color; }

    boolean isCenter() {
        return isCenter;
    }
}
