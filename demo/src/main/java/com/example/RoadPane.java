package com.example;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RoadPane extends Pane {

    public RoadPane() {
        setPrefSize(900, 900);
        setStyle("-fx-background-color: black;"); // fixed color syntax
    }

    // Custom method to draw roads
    public void drawRoads() {
        Line verticalLine1 = new Line(400, 0, 400, 900);
        verticalLine1.setStroke(Color.WHITE);
        verticalLine1.setStrokeWidth(2);

        Line verticalLine2 = new Line(450, 0, 450, 900);
        verticalLine2.setStroke(Color.WHITE);
        verticalLine2.setStrokeWidth(2);

        Line verticalLine3 = new Line(500, 0, 500, 900);
        verticalLine3.setStroke(Color.WHITE);
        verticalLine3.setStrokeWidth(2);

        Line horizontalLine1 = new Line(0, 400, 900, 400);
        horizontalLine1.setStroke(Color.WHITE);
        horizontalLine1.setStrokeWidth(2);

        Line horizontalLine2 = new Line(0, 450, 900, 450);
        horizontalLine2.setStroke(Color.WHITE);
        horizontalLine2.setStrokeWidth(2);

        Line horizontalLine3 = new Line(0, 500, 900, 500);
        horizontalLine3.setStroke(Color.WHITE);
        horizontalLine3.setStrokeWidth(2);

        getChildren().addAll(verticalLine1, verticalLine2, verticalLine3,
                             horizontalLine1, horizontalLine2, horizontalLine3);
    }
}
