package com.example;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TrafficSimulationFX extends Application {

    private final List<Car> cars = new ArrayList<>();
    private final Map<KeyCode, Rectangle> lights = new HashMap<>();

    private boolean isIntersectionOccupied = false;
    private int carIdCounter = 0;
    private int carInIntersectionId = -1;
    private KeyCode intersectionLaneKey = null;

    private Pane root;

    private long lastUpdateTime = 0;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        root.setPrefSize(Constants.SCREEN_WIDTH, Constants.SCREEN_WIDTH);
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> {
            SpawnData spawnData = getSpawnPosition(event.getCode());
            if (spawnData != null && canSpawnCarAtPosition(spawnData)) {
                cars.add(new Car(spawnData.x(), spawnData.y(), spawnData.keyCode(), carIdCounter++, root));
            }
        });

        drawRoad();
        initializeLights();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
            }
        };
        gameLoop.start();

        primaryStage.setTitle("JavaFX Traffic Simulation");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void updateGame() {
        List<Car> carsToRemove = new ArrayList<>();
        for (Car car : cars) {
            car.updatePosition(cars);
            if (car.isOutOfBounds()) {
                carsToRemove.add(car);
            }
        }

        for (Car car : carsToRemove) {
            root.getChildren().remove(car.getShape());
            cars.remove(car);
        }

        updateIntersectionLogic();
    }

    private void updateIntersectionLogic() {
        for (Car car : cars) {
            if (car.isInsideStop() && !isIntersectionOccupied) {
                car.canMove = true;
                isIntersectionOccupied = true;
                intersectionLaneKey = car.movementKey;
                carInIntersectionId = car.id;
                break;
            }

            if (car.isInsideStop() && intersectionLaneKey == car.movementKey) {
                car.canMove = true;
                isIntersectionOccupied = true;
                intersectionLaneKey = car.movementKey;
                carInIntersectionId = car.id;
            }
            if (car.id == carInIntersectionId && !car.isInsideStop()) {
                isIntersectionOccupied = false;
                intersectionLaneKey = null;
            }
        }

        lights.values().forEach(light -> light.setStroke(Color.RED));
        if (intersectionLaneKey != null && lights.containsKey(intersectionLaneKey)) {
            lights.get(intersectionLaneKey).setStroke(Color.GREEN);
        }
    }

    private void grantAccessToLane(Car car) {
        isIntersectionOccupied = true;
        intersectionLaneKey = car.movementKey;
        carInIntersectionId = car.id;
        car.canMove = true;

        lights.values().forEach(light -> light.setStroke(Color.RED));
        if (lights.containsKey(intersectionLaneKey)) {
            lights.get(intersectionLaneKey).setStroke(Color.GREEN);
        }
    }

    private void drawRoad() {
        int[] lines = { Constants.SCREEN_CENTER, Constants.SCREEN_CENTER - Constants.CAR_SIZE,
                Constants.SCREEN_CENTER + Constants.CAR_SIZE };
        for (int pos : lines) {
            Line vLine = new Line(pos, 0, pos, Constants.SCREEN_WIDTH);
            vLine.setStroke(Color.BLUE);
            Line hLine = new Line(0, pos, Constants.SCREEN_WIDTH, pos);
            hLine.setStroke(Color.BLUE);
            root.getChildren().addAll(vLine, hLine);
        }
    }

    private void initializeLights() {
        Map<KeyCode, Rectangle> lightRects = new HashMap<>();
        lightRects.put(KeyCode.DOWN, new Rectangle(Constants.SCREEN_CENTER - 2 * Constants.CAR_SIZE,
                Constants.SCREEN_CENTER - 2 * Constants.CAR_SIZE, Constants.CAR_SIZE, Constants.CAR_SIZE));
        lightRects.put(KeyCode.UP, new Rectangle(Constants.SCREEN_CENTER + Constants.CAR_SIZE,
                Constants.SCREEN_CENTER + Constants.CAR_SIZE, Constants.CAR_SIZE, Constants.CAR_SIZE));
        lightRects.put(KeyCode.LEFT, new Rectangle(Constants.SCREEN_CENTER + Constants.CAR_SIZE,
                Constants.SCREEN_CENTER - 2 * Constants.CAR_SIZE, Constants.CAR_SIZE, Constants.CAR_SIZE));
        lightRects.put(KeyCode.RIGHT, new Rectangle(Constants.SCREEN_CENTER - 2 * Constants.CAR_SIZE,
                Constants.SCREEN_CENTER + Constants.CAR_SIZE, Constants.CAR_SIZE, Constants.CAR_SIZE));

        lightRects.forEach((key, rect) -> {
            rect.setStroke(Color.RED);
            rect.setStrokeWidth(2);
            rect.setFill(Color.TRANSPARENT);
            lights.put(key, rect);
            root.getChildren().add(rect);
        });
    }

    private boolean canSpawnCarAtPosition(SpawnData spawnData) {
        for (Car existingCar : cars) {
            double dx = Math.abs(existingCar.getShape().getX() - spawnData.x());
            double dy = Math.abs(existingCar.getShape().getY() - spawnData.y());

            if (dx < Constants.MIN_SPAWN_DISTANCE && dy < Constants.MIN_SPAWN_DISTANCE) {
                return false;
            }
        }
        return true;
    }

    private SpawnData getSpawnPosition(KeyCode keyCode) {
        double[] xSpawns = { Constants.SCREEN_CENTER, Constants.SCREEN_CENTER - Constants.CAR_SIZE,
                Constants.SCREEN_WIDTH - Constants.CAR_SIZE, 0 };
        double[] ySpawns = { Constants.SCREEN_WIDTH - Constants.CAR_SIZE, 0,
                Constants.SCREEN_CENTER - Constants.CAR_SIZE, Constants.SCREEN_CENTER };

        if (keyCode == KeyCode.UP) {
            return new SpawnData(xSpawns[0], ySpawns[0], keyCode);
        } else if (keyCode == KeyCode.DOWN) {
            return new SpawnData(xSpawns[1], ySpawns[1], keyCode);
        } else if (keyCode == KeyCode.LEFT) {
            return new SpawnData(xSpawns[2], ySpawns[2], keyCode);
        } else if (keyCode == KeyCode.RIGHT) {
            return new SpawnData(xSpawns[3], ySpawns[3], keyCode);
        } else if (keyCode == KeyCode.R) {
            int i = new Random().nextInt(4);
            KeyCode newKeyCode;
            if (i == 0) {
                newKeyCode = KeyCode.UP;
            } else if (i == 1) {
                newKeyCode = KeyCode.DOWN;
            } else if (i == 2) {
                newKeyCode = KeyCode.LEFT;
            } else {
                newKeyCode = KeyCode.RIGHT;
            }
            return new SpawnData(xSpawns[i], ySpawns[i], newKeyCode);
        } else if (keyCode == KeyCode.ESCAPE) {
            System.exit(0);
            return null;
        } else {
            return null;
        }
    }
}