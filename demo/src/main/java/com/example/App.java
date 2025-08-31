package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) {
        RoadPane root = new RoadPane();
        root.drawRoads();
        

        scene = new Scene(root);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.UP) {
                root.spawnVehicle("S");
            } else if (code == KeyCode.DOWN) {
                root.spawnVehicle("N");
            } else if (code == KeyCode.LEFT) {
                root.spawnVehicle("E");
            } else if (code == KeyCode.RIGHT) {
                root.spawnVehicle("W");
            } else if (code == KeyCode.R) {
                // root.spawnVehicleRandom();
            } else if (code == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("JRafic Traffic Simulation");
        stage.setMinWidth(900);
        stage.setMaxWidth(900);
        stage.setMinHeight(900);
        stage.setMaxHeight(900);
        stage.show();

        root.startAnimation();
    }

    public static void main(String[] args) {
        launch();
    }
}
