package com.point.mancala;

/**
 * Like the Hole class but is a smaller model to use less memory
 * The: grid, anchorPane, rect and label are not supported here.
 */
public class BasicHole implements Comparable<BasicHole> {
    protected short holeKey;
    protected short ballCount;

    public BasicHole(short holeKey, short ballCount){
        this.holeKey = holeKey;
        this.ballCount = ballCount;
    }
    // Copy constructor for deep copy
    public BasicHole(BasicHole original) {
        this.holeKey = original.holeKey;
        this.ballCount = original.ballCount;
    }
    @Override
    public String toString() {
        return "HoleKey: " + this.holeKey + " (Ball count: " + this.ballCount + ")";
    }

    public void setBallCount(short newScore){
        this.ballCount = newScore;
    }
    public void incBallCount(){
        this.ballCount++;
    }
    public void decBallCount(){
        this.ballCount--;
    }

    @Override
    public int compareTo(BasicHole other) {
        return Integer.compare(this.holeKey, other.holeKey);
    }
}

