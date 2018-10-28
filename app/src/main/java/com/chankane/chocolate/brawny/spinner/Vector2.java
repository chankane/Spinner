package com.chankane.chocolate.brawny.spinner;

class Vector2 {

    float x;
    float y;

    Vector2 (float x, float y) {
        this.x = x;
        this.y = y;
    }

    static float calcSqrDistance(float x0, float y0, float x1, float y1) {
        return (x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1);
    }

    static float crossProduct(Vector2 v0, Vector2 v1) {
        return (v0.x * v1.y) - (v0.y * v1.x);
    }
}
