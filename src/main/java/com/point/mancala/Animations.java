package com.point.mancala;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.io.IOException;

public class Animations{
    public static boolean inTransition = false;
    private ScaleTransition scale = new ScaleTransition();
    public TranslateTransition translate = new TranslateTransition();
    private RotateTransition rotate = new RotateTransition();
    private FadeTransition fade = new FadeTransition();
    public void stopAnimation() throws IOException {
        logAction("stop Animation", 3);
        this.scale.stop();
        this.scale.setByX(0.0);
        this.scale.setByY(0.0);
        this.translate.stop();
        this.fade.stop();
        this.rotate.stop();

    }
    public void translate(Node node) throws IOException {
        logAction("translate animation", 3);
        //stopAnimation();
        translate.stop();
        translate.setNode(node);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByX(0);
        translate.setByY(-15);
        translate.setAutoReverse(true);
        translate.playFromStart();
    }
    public void stopTranslate(){
        this.translate.setFromY(0);
        this.translate.stop();

    }

    public static void moveNodeWithTransition(Node node, Pane newParent, Duration duration){

        if(!inTransition) {
            inTransition = true;
            TranslateTransition transition = new TranslateTransition(duration, node);

            double nodeXLocation = node.getBoundsInParent().getMinX(); // get the node X location in its parent
            double destNodeXLocation = newParent.getChildren().getLast().getBoundsInParent().getMaxX(); // get the destination of node X location in its new parent

            logAction("nodeXLocation: " + nodeXLocation + ", destNodeXLocation: " + destNodeXLocation, 2);
            // decrease or increase the x location of the node (from 0 - the x location of the node)
            double xdest = (Math.max(nodeXLocation, destNodeXLocation) - Math.min(nodeXLocation, destNodeXLocation)) * ((nodeXLocation > destNodeXLocation) ? -1 : 1);


            double nodeYLocation = node.getParent().getBoundsInParent().getMinY(); // get the node Y location in its parent
            double destNodeYLocation = newParent.getBoundsInParent().getMinY(); // get the destination of node Y location in its new parent
            logAction("nodeYLocation: " + nodeYLocation + ", destNodeYLocation: " + destNodeYLocation, 2);

            // decrease or increase the y location of the node (from 0 - the y location of the node)
            double ydest = (Math.max(nodeYLocation, destNodeYLocation) - Math.min(nodeYLocation, destNodeYLocation)) * ((nodeYLocation > destNodeYLocation) ? -1 : 1);

            logAction("X: " + xdest + ", dest Y: " + ydest, 2);

            transition.setToX(xdest); // set the X transition
            transition.setToY(ydest); // set the Y transition
            transition.playFromStart(); // start the transition animation


            // action to be performed after the transition ends
            // add the node to the new parent (witch also remove it from its previous parent)
            transition.setOnFinished(e -> {
                logAction("Transition ended! add the node to the new parent: " + newParent.getId(), 2);
                newParent.getChildren().add(node);
                node.setTranslateX(0);
                node.setTranslateY(0);
                inTransition = false;
            });
        }
        else {
            logAction("Can't make another transition, there is active transition now!", 2);
        }

    }
    public void rotate(Node node, int duration, Boolean repeat) {
        logAction("rotate animation", 3);
        // rotate
        rotate.setNode(node);
        rotate.setDuration(Duration.millis(duration));
        if(repeat)
            rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Z_AXIS);
        //rotate.play();
        rotate.playFromStart();
    }
    public void fadeAnimation(Node node, int duration, boolean repeat) {
        logAction("fade animation", 3);
        // fade

        fade.setNode(node);
        fade.setDuration(Duration.millis(duration));
        if(repeat)
            fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.EASE_IN);
        fade.setFromValue(0);
        fade.setToValue(1);
        //fade.play();
        fade.playFromStart();
    }
    public void scale(Node node, int duration, double scaleBy, int setCycleCount) {
        // to repate set setCycleCount to -1 (TranslateTransition.INDEFINITE = -1)
        logAction("scale animation", 3);
        // scale
        scale.setNode(node);
        scale.setDuration(Duration.millis(duration));
            scale.setCycleCount(setCycleCount);
        scale.setInterpolator(Interpolator.EASE_BOTH);
        scale.setFromY(0.85);
        scale.setFromX(0.85);
        scale.setByX(scaleBy);
        scale.setByY(scaleBy);
        scale.setAutoReverse(true);
        scale.playFromStart();

    }

    // print log message
    // importance: the level of importance of this log.
    // if it not that important to print this log (like show that a hole is highlighted) it will be a higher number
    // 1 = the most important and 3 = is not that important
    public static void logAction(String message, int importance)
    {
        if(importance < 3)
        {
            // print just yhe important log
            System.out.println(message);
        }
    }
}
