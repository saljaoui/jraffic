package com.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Vehicle extends Rectangle {

    private Direction direction;
    private Route route;    
    private double velocity = 2;
    private boolean stopped = false;

    public Vehicle(Direction direction, Route route, Color color) {
        super(40, 40, color);
        this.direction = direction;
        this.route = route;
        setInitialPosition();
    }

    private void setInitialPosition() {
        switch (direction) {
            case NORTH: 
                setX(405);
                setY(-45);
                break;
            case SOUTH:
                setX(455);
                setY(900);
                break;
            case EAST:
                setX(900);
                setY(405);
                break;
            case WEST:
                setX(-45);
                setY(455);
                break;
        }
    }

    public void move() {
        if (stopped) return;

        switch (direction) {
            case NORTH:
                setY(getY() + velocity);
                break;
            case SOUTH:
                setY(getY() - velocity);
                break;
            case EAST:
                setX(getX() - velocity);
                break;
            case WEST:
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
