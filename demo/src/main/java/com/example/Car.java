package com.example;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Random;

public class Car {
    public Direction direction;
    public KeyCode movementKey;
    public boolean canMove;
    public final int id;
    private final Rectangle shape;

    public Car(double x, double y, KeyCode movementKey, int id, Pane parentPane) {
        this.movementKey = movementKey;
        this.id = id;
        this.canMove = false;

        this.shape = new Rectangle(x, y, Constants.CAR_SIZE, Constants.CAR_SIZE);

        int randomNum = new Random().nextInt(3);
        if (randomNum == 0) {
            this.direction = Direction.Left;
            this.shape.setFill(Color.BLUEVIOLET);
        } else if (randomNum == 1) {
            this.direction = Direction.Right;
            this.shape.setFill(Color.YELLOW);
        } else {
            this.direction = Direction.Straight;
            this.shape.setFill(Color.BLUE);
        }
        parentPane.getChildren().add(this.shape);
    }

    public Rectangle getShape() {
        return shape;
    }

    public void updatePosition(List<Car> otherCars) {
        if (!this.canMove && this.isInsideStop())
            return;
        if (this.isTooCloseToOtherCar(otherCars))
            return;

        double distance = Constants.CAR_SPEED;

        if (this.movementKey == KeyCode.UP) {
            shape.setY(shape.getY() - distance);
            checkTurnUp();
        } else if (this.movementKey == KeyCode.DOWN) {
            shape.setY(shape.getY() + distance);
            checkTurnDown();
        } else if (this.movementKey == KeyCode.RIGHT) {
            shape.setX(shape.getX() + distance);
            checkTurnRight();
        } else if (this.movementKey == KeyCode.LEFT) {
            shape.setX(shape.getX() - distance);
            checkTurnLeft();
        }
    }

    public boolean isOutOfBounds() {
        return shape.getX() < -Constants.CAR_SIZE || shape.getX() > Constants.SCREEN_WIDTH + Constants.CAR_SIZE ||
                shape.getY() < -Constants.CAR_SIZE || shape.getY() > Constants.SCREEN_WIDTH + Constants.CAR_SIZE;
    }

    public boolean isInsideStop() {
        return shape.getY() > Constants.SCREEN_CENTER - Constants.CAR_SIZE * 2 &&
                shape.getY() < Constants.SCREEN_CENTER + Constants.CAR_SIZE &&
                shape.getX() > Constants.SCREEN_CENTER - Constants.CAR_SIZE * 2 &&
                shape.getX() < Constants.SCREEN_CENTER + Constants.CAR_SIZE;
    }

    private void checkTurnUp() {
        if (direction == Direction.None)
            return;
        double turnPoint = (direction == Direction.Left) ? Constants.SCREEN_CENTER - Constants.CAR_SIZE
                : Constants.SCREEN_CENTER;
        if (shape.getY() <= turnPoint) {
            if (direction == Direction.Left) {
                movementKey = KeyCode.LEFT;
            } else if (direction == Direction.Right) {
                movementKey = KeyCode.RIGHT;
            } else {
                movementKey = KeyCode.UP;
            }
            direction = Direction.None;
        }
    }

    private void checkTurnDown() {
        if (direction == Direction.None)
            return;
        double turnPoint = (direction == Direction.Left) ? Constants.SCREEN_CENTER
                : Constants.SCREEN_CENTER - Constants.CAR_SIZE;
        if (shape.getY() >= turnPoint) {
            if (direction == Direction.Left) {
                movementKey = KeyCode.RIGHT;
            } else if (direction == Direction.Right) {
                movementKey = KeyCode.LEFT;
            } else {
                movementKey = KeyCode.DOWN;
            }
            direction = Direction.None;
        }
    }

    private void checkTurnRight() {
        if (direction == Direction.None)
            return;
        double turnPoint = (direction == Direction.Left) ? Constants.SCREEN_CENTER
                : Constants.SCREEN_CENTER - Constants.CAR_SIZE;
        if (shape.getX() >= turnPoint) {
            if (direction == Direction.Left) {
                movementKey = KeyCode.UP;
            } else if (direction == Direction.Right) {
                movementKey = KeyCode.DOWN;
            } else {
                movementKey = KeyCode.RIGHT;
            }
            direction = Direction.None;
        }
    }

    private void checkTurnLeft() {
        if (direction == Direction.None)
            return;
        double turnPoint = (direction == Direction.Left) ? Constants.SCREEN_CENTER - Constants.CAR_SIZE
                : Constants.SCREEN_CENTER;
        if (shape.getX() <= turnPoint) {
            if (direction == Direction.Left) {
                movementKey = KeyCode.DOWN;
            } else if (direction == Direction.Right) {
                movementKey = KeyCode.UP;
            } else {
                movementKey = KeyCode.LEFT;
            }
            direction = Direction.None;
        }
    }

    private boolean isTooCloseToOtherCar(List<Car> otherCars) {
        for (Car otherCar : otherCars) {
            if (this == otherCar || this.movementKey != otherCar.movementKey)
                continue;
            boolean tooClose = false;
            if (this.movementKey == KeyCode.UP) {
                tooClose = otherCar.shape.getY() < this.shape.getY() &&
                        (this.shape.getY() - otherCar.shape.getY()) < 70 &&
                        Math.abs(this.shape.getX() - otherCar.shape.getX()) < Constants.CAR_SIZE;
            } else if (this.movementKey == KeyCode.DOWN) {
                tooClose = otherCar.shape.getY() > this.shape.getY() &&
                        (otherCar.shape.getY() - this.shape.getY()) < 70 &&
                        Math.abs(this.shape.getX() - otherCar.shape.getX()) < Constants.CAR_SIZE;
            } else if (this.movementKey == KeyCode.LEFT) {
                tooClose = otherCar.shape.getX() < this.shape.getX() &&
                        (this.shape.getX() - otherCar.shape.getX()) < 70 &&
                        Math.abs(this.shape.getY() - otherCar.shape.getY()) < Constants.CAR_SIZE;
            } else if (this.movementKey == KeyCode.RIGHT) {
                tooClose = otherCar.shape.getX() > this.shape.getX() &&
                        (otherCar.shape.getX() - this.shape.getX()) < 70 &&
                        Math.abs(this.shape.getY() - otherCar.shape.getY()) < Constants.CAR_SIZE;
            }
            if (tooClose)
                return true;
        }
        return false;
    }
}