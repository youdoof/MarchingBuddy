package edu.truman.amb1664.marchingbuddy;

/**
 * MarchingDot is what the system uses to hold the information
 * contained within a dot. The Left to Right coordinate x, and
 * the Front to Back coordinate y.
 *
 * @author Andrew Brogan
 * @since 3/23/2017
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
