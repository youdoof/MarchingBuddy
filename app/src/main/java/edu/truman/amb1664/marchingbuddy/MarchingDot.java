package edu.truman.amb1664.marchingbuddy;

/**
 * Created by Brogan on 3/23/2017.
 */

class MarchingDot {
    private double leftToRight;
    private double frontToBack;

    MarchingDot(double leftToRight, double frontToBack) {
        this.leftToRight = leftToRight;
        this.frontToBack = frontToBack;
    }

    double getLeftToRight() {
        return leftToRight;
    }

    double getFrontToBack() {
        return frontToBack;
    }
}
