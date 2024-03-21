package com.point.mancala;

import com.point.mancala.Animations.FadeAnimation;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;


/**
 * Extended version of BasicHole.
 * The: grid, anchorPane, rect and label are supported here.
 */
public class Hole extends BasicHole {
    protected GridPane grid;
    protected AnchorPane anchorPane;
    protected Rectangle rect;
    protected Label label;

    public Hole(short holeKey, short ballCount, GridPane grid, AnchorPane anchorPane, Rectangle rect, Label label){
        super(holeKey, ballCount);
        this.grid = grid;
        this.anchorPane = anchorPane;
        this.rect = rect;
        this.label = label;
    }
//    // Copy constructor for deep copy
//    public Hole(Hole original) {
//        super(original.holeKey, original.ballCount);
//        // Assuming that grid, anchorPane, rect, and label are immutable, so we don't need to make deep copies of them
//        this.grid = original.grid;
//        this.anchorPane = original.anchorPane;
//        this.rect = original.rect;
//        this.label = original.label;
//    }
    public void setBallCount(short newScore, boolean updateUI, boolean withAnimation){
        this.ballCount = newScore;
        if (updateUI){
            this.label.setText(String.valueOf(ballCount));
            if(withAnimation)
            {
                new FadeAnimation().fadeAnimation(this.label, 300, false);
            }
        }
    }
    public void updateBallCountLabel(boolean withAnimation){
        this.label.setText(String.valueOf(ballCount));
        if(withAnimation)
        {
            new FadeAnimation().fadeAnimation(this.label, 300, false);
        }
    }
    public void incBallCount(boolean updateUI){
        this.ballCount++;
        if (updateUI)
            this.label.setText(String.valueOf(ballCount));
    }

    public void decBallCount(boolean updateUI){
        this.ballCount--;
        if (updateUI)
            this.label.setText(String.valueOf(ballCount));
    }
}

