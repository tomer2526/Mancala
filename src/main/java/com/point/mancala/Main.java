package com.point.mancala;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Mancala V1");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        stage.maxWidthProperty().bind(stage.widthProperty());
        stage.minWidthProperty().bind(stage.widthProperty());
    }



}
