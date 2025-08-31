package com.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TrafficLight extends Circle {
    private boolean green;

    public TrafficLight(double x, double y) {
        super(x, y, 10, Color.RED);
        green = false;
    }

    public void setGreen(boolean green) {
        this.green = green;
        setFill(green ? Color.GREEN : Color.RED);
    }

    public boolean isGreen() {
        return green;
    }
}
