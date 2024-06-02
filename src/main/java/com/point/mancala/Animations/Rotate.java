package com.point.mancala.Animations;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Rotate extends Animations{
    private RotateTransition rotate = new RotateTransition();
    public void rotate(Node node, int duration, Boolean repeat) {
        logAction("rotate animation", 3);
        // rotate
        rotate.setNode(node);
        rotate.setDuration(Duration.millis(duration));
        if(repeat)
            rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(javafx.scene.transform.Rotate.Z_AXIS);
        //rotate.play();
        rotate.playFromStart();
    }
}
