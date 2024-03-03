package com.point.mancala;


import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class Ball extends Pane { //  Create a Pane for pane
    private static final int diameter = 43; //48; // Define the ball width and Height
    private ImageView Ball;


    Ball(Pane parent, Color color, int ballIndex) throws FileNotFoundException { // Set ball properties
        //old - create in random place
        //Point2D point = GetRandomLoaction(parent.getPrefWidth(), parent.getPrefHeight());
        //Ball = new Circle(point.getX(), point.getY(), radius);

        /*
        double x = 78, y = 88;
        String path = "src/main/resources/assets/Balls/Ball-Yellow.png";
        if(ballIndex == 0)
        {
            x = 42;
            y = 32;
            path = "src/main/resources/assets/Balls/Ball-Red.png";
        }
        else if(ballIndex == 1)
        {
            x = 88;
            y = 42;
            path = "src/main/resources/assets/Balls/Ball-Green.png";
        }
        else if(ballIndex == 2){
            x = 32;
            y = 78;
            path = "src/main/resources/assets/Balls/Ball-Blue.png";
        }

        Ball.setX(x-22);
        Ball.setY(y-20);
*/

        String path = "src/main/resources/assets/Balls/Ball-Yellow.png";
        if(ballIndex == 0)
        {
            path = "src/main/resources/assets/Balls/Ball-Red.png";
        }
        else if(ballIndex == 1)
        {
            path = "src/main/resources/assets/Balls/Ball-Green.png";
        }
        else if(ballIndex == 2){
            path = "src/main/resources/assets/Balls/Ball-Blue.png";
        }
        Ball = new ImageView(new Image(new FileInputStream(path)));
        Ball.setFitHeight(diameter);
        Ball.setFitWidth(diameter);
        getChildren().add(Ball);


    }


    //get parent continer (it take the width and height from it) and return random point
    //to place the ball
    private static Point2D GetRandomLoaction(double parentWidth, double parentHeight) {
        int randX = (int)(Math.random() * ((parentWidth - (diameter+50)) + 1)) + 50;
        //int num = (int)(Math.random() * ((max - min) + 1)) + min
        int randY = (int)(Math.random() * ((parentHeight - (diameter+50)) + 1)) + 50;
        return new Point2D(randX, randY);
    }

    /*
    public void MoveLeft() { // Method for moving the ball left
        if (Ball.getRadius() < Ball.getCenterX()) {
            Ball.setCenterX(Ball.getCenterX() - 10);
        }
    }

    public void MoveRight() { // Method for moving the ball Right
        if (Ball.getCenterX() < width - Ball.getRadius()) {
            Ball.setCenterX(Ball.getCenterX() + 10);
        }
    }

    public void MoveUp() { // Method for moving the ball Up
        if (Ball.getRadius() < Ball.getCenterY()) {
            Ball.setCenterY(Ball.getCenterY() - 10);
        }
    }

    public void MoveDown() { // Method for moving the ball Down
        if (Ball.getCenterY() < height - Ball.getRadius()) {
            Ball.setCenterY(Ball.getCenterY() + 10);
        }
    }*/
}

