package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) {
        RoadPane root = new RoadPane();
        root.setPrefSize(900, 900);
        root.setStyle("-fx-background-color: #000;");
        root.drawRoads();
        

        scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("JRafic Traffic Simulation");
        stage.setMinWidth(900);
        stage.setMaxWidth(900);
        stage.setMinHeight(900);
        stage.setMaxHeight(900);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
