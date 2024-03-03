package com.point.mancala;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class playerVsCpuMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(playerVsCpuMenu.class.getResource("player-vs-cpu-menu.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Player vs CPU");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
