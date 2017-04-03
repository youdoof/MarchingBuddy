package edu.truman.amb1664.marchingbuddy;

/**
 * Created by Brogan on 3/23/2017.
 */

class MarchingDot {
    private final double leftToRight;
    private final double frontToBack;

    /**
     * @param leftToRight double representing the 'x' coordinate for computation
     * @param frontToBack double representing the 'y' coordinate for computation
     */
    MarchingDot(double leftToRight, double frontToBack) {
        this.leftToRight = leftToRight;
        this.frontToBack = frontToBack;
    }

    /**
     * @return double for 'x' coordinate of MarchingDot
     */
    double getLeftToRight() {
        return leftToRight;
    }

    /**
     * @return double for 'y' coordinate fof MarchingDot
     */
    double getFrontToBack() {
        return frontToBack;
    }
}
