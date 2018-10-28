package com.chankane.chocolate.brawny.spinner;

class Circle {

    static final int RADIUS = 100;
    Vector2 position;
    private int color;
    private boolean isCenter;

    Circle(Vector2 position, int color, boolean isCenter) {
        this.position = position;
        this.color = color;
        this.isCenter = isCenter;
    }

    int getColor() {
        return color;
    }

    boolean isCenter() { return isCenter; }
}
