package com.point.mancala;

import javafx.scene.layout.AnchorPane;

public class Hole {
    public AnchorPane hole;
    public char ballCount;

    Hole(AnchorPane hole, char ballCount){
        this.hole = hole;
        this.ballCount = ballCount;
    }
}
