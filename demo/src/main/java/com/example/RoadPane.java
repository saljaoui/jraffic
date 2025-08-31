package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RoadPane extends Pane {

    private List<Vehicle> vehicles = new ArrayList<>();

    public RoadPane() {
        setPrefSize(900, 900);
        setStyle("-fx-background-color: black;"); // fixed color syntax
    }

    public void spawnVehicle(Direction direction) {
        Route route;
        int choice = new Random().nextInt(3);
        switch (choice) {
            case 0:
                route = Route.STRAIGHT;
                break;
            case 1:
                route = Route.LEFT;
                break;
            default:
                route = Route.RIGHT;
                break;
        }

        Color color;
        switch (route) {
            case LEFT:
                color = Color.RED;
                break;
            case RIGHT:
                color = Color.YELLOW;
                break;
            default:
                color = Color.BLUE;
                break;
        }

        Vehicle v = new Vehicle(direction, route, color);
        vehicles.add(v);
        getChildren().add(v);
    }

    public void drawRoads() {
        Line verticalLine1 = new Line(400, 0, 400, 900);
        verticalLine1.setStroke(Color.WHITE);
        verticalLine1.setStrokeWidth(2);

        Line verticalLine2 = new Line(450, 0, 450, 900);
        verticalLine2.setStroke(Color.WHITE);
        verticalLine2.setStrokeWidth(2);
        verticalLine2.getStrokeDashArray().addAll(10d, 5d);

        Line verticalLine3 = new Line(500, 0, 500, 900);
        verticalLine3.setStroke(Color.WHITE);
        verticalLine3.setStrokeWidth(2);

        Line horizontalLine1 = new Line(0, 400, 900, 400);
        horizontalLine1.setStroke(Color.WHITE);
        horizontalLine1.setStrokeWidth(2);

        Line horizontalLine2 = new Line(0, 450, 900, 450);
        horizontalLine2.setStroke(Color.WHITE);
        horizontalLine2.setStrokeWidth(2);
        horizontalLine2.getStrokeDashArray().addAll(10d, 5d);

        Line horizontalLine3 = new Line(0, 500, 900, 500);
        horizontalLine3.setStroke(Color.WHITE);
        horizontalLine3.setStrokeWidth(2);

        getChildren().addAll(verticalLine1, verticalLine2, verticalLine3,
                horizontalLine1, horizontalLine2, horizontalLine3);
    }

    public void startAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Vehicle v : vehicles) {
                    v.move();
                    // stopAtRedLight(v);
                }
            }
        };
        timer.start();
    }
}
