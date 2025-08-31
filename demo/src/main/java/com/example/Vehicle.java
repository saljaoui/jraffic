package com.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Vehicle extends Rectangle {

    private String direction; // "N", "S", "E", "W"
    private String route;     // "straight", "left", "right"
    private double velocity = 2;
    private boolean stopped = false;

    public Vehicle(String direction, String route, Color color) {
        super(40, 40, color);
        this.direction = direction;
        this.route = route;
        setInitialPosition();
    }

    private void setInitialPosition() {
        switch (direction) {
            case "N": 
                setX(405);
                setY(0);
                break;
            case "S":
                setX(405);
                setY(900);
                break;
            case "E":
                setX(900);
                setY(405);
                break;
            case "W":
                setX(0);
                setY(405);
                break;
        }
    }

    public void move() {
        if (stopped) return;

        switch (direction) {
            case "N":
                setY(getY() + velocity);
                break;
            case "S":
                setY(getY() - velocity);
                break;
            case "E":
                setX(getX() - velocity);
                break;
            case "W":
                setX(getX() + velocity);
                break;
        }
    }

    public void stop() {
        stopped = true;
    }

    public void go() {
        stopped = false;
    }
}
