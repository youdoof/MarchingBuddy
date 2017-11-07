package edu.truman.amb1664.marchingbuddy;

/**
 * MidSet is a helper class that has the methods to
 * compute the mid set and step size for each set.
 *
 * @author Andrew Brogan
 * @since 12/18/16
 */
class MidSet {
    // Yard Line Constants
    static final int[] YARD_LINES = {50, 45, 40, 35, 30, 25, 20, 15, 10, 5, 0};
    // Hash Type Constants
    private static final String FRONT_HASH = "Front";
    private static final String BACK_HASH = "Back";
    private static final String HOME_HASH = "Home";
    private static final String VISITOR_HASH = "Visitor";
    // Side Type Constants
    private static final String ONE_SIDE = "Side 1 ";
    private static final String TWO_SIDE = "Side 2 ";
    private static final String LEFT_SIDE = "Left ";
    private static final String RIGHT_SIDE = "Right ";
    // Step Size Constant -- 8 to 5
    private static final double STEP_SIZE_REFERENCE = 8.0;
    // Sideline distance constants
    private static final double FRONT_SIDELINE = -42.0;
    private static final double BACK_SIDELINE = 42.0;
    // NCAA Hash distances
    private static final double NCAA_FRONT_HASH = -10.0;
    private static final double NCAA_BACK_HASH = 10.0;
    // High School Hash distances
    private static final double HS_FRONT_HASH = -14.0;
    private static final double HS_BACK_HASH = 14.0;
    // Rounding constants
    private static final double NEAREST_HALF_STEP = 2f;
    private static final double NEAREST_QUARTER_STEP = 4f;
    private static final double NEAREST_EIGHTH_STEP = 8f;
    private static final double NEAREST_SIXTEENTH_STEP = 16f;
    private static final double NEAREST_THIRTY_SECOND_STEP = 32f;

    static double getBS() {
        return BACK_SIDELINE;
    }

    static int getYardLine(int progress) {
        return YARD_LINES[progress];
    }

    /**
     * This method takes the yard line given and returns the representation in the
     * internal coordinate plane, which is where all mid sets are calculated
     *
     * @param value The yard line you want to find
     * @return double representing the yard line in the coordinate plane
     */
    private static int findYardLine(int value) {
        int k = 0;
        for (int i = 0; i < YARD_LINES.length; i++) {
            if (YARD_LINES[i] == value) {
                k = i;
                break;
            }
        }
        return k * 8;
    }

    /**
     * outputFrontToBack takes a double y and the current Field object and
     * translates the double y into a formatted String using marching band terms set
     * by the Field object.
     *
     * @param frontToBack input double representing the Front-to-Back on the field
     * @param f           Field object holding the field type and hash type
     * @return marching band term version of the front-to-back
     */
    private static String outputFrontToBack(double frontToBack, Field f) {
        String output;
        double frontHash;
        double backHash;
        String frontHashName;
        String backHashName;

        // Modify field type from Settings
        if (f.getFieldType() == 0) {
            frontHash = HS_FRONT_HASH;
            backHash = HS_BACK_HASH;
        } else {
            frontHash = NCAA_FRONT_HASH;
            backHash = NCAA_BACK_HASH;
        }

        // Modify hash type from Settings
        if (f.getHashType() == 0) {
            frontHashName = FRONT_HASH;
            backHashName = BACK_HASH;
        } else {
            frontHashName = HOME_HASH;
            backHashName = VISITOR_HASH;
        }

        // Middle of the field
        if (frontToBack == 0) {
            output = -frontHash + " behind " + frontHashName + " Hash";
            // return output;
        }
        // Front half of the field
        else if (frontToBack < 0) {
            if (frontToBack > frontHash)
                output = (-frontHash + frontToBack) + " behind " + frontHashName + " Hash";
            else if (frontToBack == frontHash)
                output = "On " + frontHashName + " Hash";
            else if (frontToBack < frontHash && frontToBack > ((FRONT_SIDELINE + frontHash) / 2))
                output = (frontHash + -frontToBack) + " in front of " + frontHashName + " Hash";
            else if (frontToBack > FRONT_SIDELINE)
                output = (-FRONT_SIDELINE + frontToBack) + " behind " + frontHashName + " Sideline";
            else if (frontToBack == FRONT_SIDELINE)
                output = "On " + frontHashName + " Sideline";
            else // -FS + x
                output = -(frontToBack - FRONT_SIDELINE) + " in front of " + frontHashName + " Sideline";
        }
        // Back half of the field
        else {
            if (frontToBack < backHash)
                output = (backHash - frontToBack) + " in front of " + backHashName + " Hash";
            else if (frontToBack == backHash)
                output = "On " + backHashName + " Hash";
            else if (frontToBack > backHash && frontToBack < ((BACK_SIDELINE + backHash) / 2))
                output = (frontToBack - backHash) + " behind " + backHashName + " Hash";
            else if (frontToBack < BACK_SIDELINE)
                output = (BACK_SIDELINE - frontToBack) + " in front of " + backHashName + " Sideline";
            else if (frontToBack == BACK_SIDELINE)
                output = "On " + backHashName + " Sideline";
            else
                output = (frontToBack - BACK_SIDELINE) + " behind " + backHashName + " Sideline";
        }
        return output;
    }

    /**
     * outputLeftToRight takes a double x and the current Field object and
     * translates the double x into a formatted String using marching band terms set
     * by the Field object.
     *
     * @param leftToRight input double representing the left-to-right on the field
     * @param f           Field object holding the side type
     * @return marching band term version of the left-to-right
     */
    private static String outputLeftToRight(double leftToRight, Field f) {
        String output;
        String leftSideName;
        String rightSideName;

        // Set side type from Settings
        if (f.getSideType() == 0) {
            leftSideName = ONE_SIDE;
            rightSideName = TWO_SIDE;
        } else {
            leftSideName = LEFT_SIDE;
            rightSideName = RIGHT_SIDE;
        }

        // 50 Yard Line (Middle)
        if (leftToRight == 0.0) {
            output = "On 50";
        }
        // Side 2, Right Side
        else if (leftToRight > 0) {
            int yardLine = (int) (leftToRight / 8);
            double step = leftToRight % 8;
            if (step == 0.0) {
                output = "On " + rightSideName + YARD_LINES[yardLine];
            } else if (step <= 4.0) {
                output = step + " steps Outside " + rightSideName + YARD_LINES[yardLine];
            } else {
                step = 8 - step;
                output = step + " steps Inside " + rightSideName + YARD_LINES[yardLine + 1];
            }
        }
        // Side 1, Left Side
        else {
            int yardLine = (int) (leftToRight / 8);
            double step = leftToRight % 8;
            if (step == 0.0) {
                output = "On " + leftSideName + YARD_LINES[-yardLine];
            } else if (step >= -4.0) {
                output = -step + " steps Outside " + leftSideName + YARD_LINES[-yardLine];
            } else {
                step = 8 + step;
                output = step + " steps Inside " + leftSideName + YARD_LINES[-yardLine + 1];
            }
        }
        return output;
    }

    /**
     * inputLeftToRight translates the input from the GUI and translates it into a
     * double which the system understands.
     *
     * @param steps      number of steps inside or outside of the yard line
     * @param onInOutBtn 0 - On, 1 - Inside, 2 - Outside
     * @param sideBtn    0 - Side 1, 1 - Side 2
     * @param yardline   actual integer value of the yard line
     * @return double equivalent of the input left to right coordinate
     */
    static double inputLeftToRight(double steps, int onInOutBtn, int sideBtn, int yardline) {
        double result = 0;

        switch (onInOutBtn) {
            // On the Yard Line
            case 0:
                if (sideBtn == 0)
                    result = -findYardLine(yardline);
                else
                    result = findYardLine(yardline);
                break;
            // Inside the Yard Line (towards the 50)
            case 1:
                if (yardline == 50)
                    if (sideBtn == 0)
                        result = -steps;
                    else
                        result = steps;
                else if (sideBtn == 0)
                    result = -findYardLine(yardline) + steps;
                else
                    result = findYardLine(yardline) - steps;
                break;
            // Outside the Yard Line (away from the 50)
            case 2:
                if (yardline == 50)
                    if (sideBtn == 0)
                        result = -steps;
                    else
                        result = steps;
                else if (sideBtn == 0)
                    result = -findYardLine(yardline) - steps;
                else
                    result = findYardLine(yardline) + steps;
                break;
        }
        return result;
    }

    /**
     * inputFrontToBack translates the input from GUI to a double understood by the
     * system.
     *
     * @param steps            number of steps away from the hash or sideline
     * @param onFrontBehindBtn 0 - On, 1 - In Front Of, 2 - Behind
     * @param hashSidelineBtn  0 - Front Sideline, 1 - Front Hash, 2 - Back Hash, 3 - Back
     *                         Sideline
     * @param f                Using to get the field type, 0 - High School, 1 - NCAA
     * @return double equivalent of input front to back coordinate
     */
    static double inputFrontToBack(double steps, int onFrontBehindBtn, int hashSidelineBtn, Field f) {
        double result = 0;
        double frontHash;
        double backHash;

        // Check the field type, and adjust reference points accordingly
        if (f.getFieldType() == 0) {
            frontHash = HS_FRONT_HASH;
            backHash = HS_BACK_HASH;
        } else {
            frontHash = NCAA_FRONT_HASH;
            backHash = NCAA_BACK_HASH;
        }

        switch (hashSidelineBtn) {
            // Front Sideline
            case 0:
                result = onFrontBehindHelper(steps, onFrontBehindBtn, FRONT_SIDELINE);
                break;
            // Front Hash
            case 1:
                result = onFrontBehindHelper(steps, onFrontBehindBtn, frontHash);
                break;
            // Back hash
            case 2:
                result = onFrontBehindHelper(steps, onFrontBehindBtn, backHash);
                break;
            // Back Sideline
            case 3:
                result = onFrontBehindHelper(steps, onFrontBehindBtn, BACK_SIDELINE);
                break;
        }
        return result;
    }

    /**
     * Helps reduce duplicate code in the switch statement
     *
     * @param steps                 number of steps away from hash or sideline
     * @param onFrontBehindBtn      0 = on, 1 = in front of, 2 = behind
     * @param hashSidelineReference constant passed in after preferences
     * @return double version of input
     */
    private static double onFrontBehindHelper(double steps, int onFrontBehindBtn, double hashSidelineReference) {
        if (onFrontBehindBtn == 0) // On
            return hashSidelineReference;
        else if (onFrontBehindBtn == 1) // In Front Of
            return hashSidelineReference - steps;
        else // Behind
            return hashSidelineReference + steps;
    }

    /**
     * This method computes the distance between to Coordinate objects,
     * using the distance formula for coordinates.
     *
     * @param start initial Coordinate
     * @param end   final Coordinate
     * @return distance between the two Coordinates
     */
    private static double distance(Coordinate start, Coordinate end) {
        double startX = start.getLeftToRight();
        double startY = start.getFrontToBack();
        double endX = end.getLeftToRight();
        double endY = end.getFrontToBack();
        return Math.sqrt(Math.pow((endX - startX), 2) + Math.pow(endY - startY, 2));
    }

    /**
     * Simplified version of review and getMidSsetInformation. Just prints one
     * Coordinate rather than the entire formatted string. Should get used
     * sometime in the near future
     *
     * @param c Coordinate to print in marching band terms
     * @param f Field to set terms and field type
     * @return formatted string representing the given Coordinate
     */
    static String printCoordinate(Coordinate c, Field f) {
        return outputLeftToRight(c.getLeftToRight(), f) + " \n" +
                outputFrontToBack(c.getFrontToBack(), f);
    }

    /**
     * getMidSetCoordinate calculates the mid set and returns a new coordinate representing
     * the calculated mid set
     *
     * @param start coordinate starting from
     * @param end coordinate going to
     * @param specificity rounding from settings
     * @return new coordinate object representing calculated mid set
     */
    static Coordinate getMidSetCoordinate(Coordinate start, Coordinate end, int specificity) {

        double leftToRightMiddle = specificitySwitcher(specificity, start.getLeftToRight() + end.getLeftToRight()) / 2;
        double frontToBackMiddle = specificitySwitcher(specificity, start.getFrontToBack() + end.getFrontToBack()) / 2;
        return new Coordinate(leftToRightMiddle, frontToBackMiddle);
    }

    /**
     * getStepSize calculates the step size relative to the distance covered
     * from the two coordinates fed into the function
     *
     * @param start       coordinate starting from
     * @param end         coordinate going to
     * @param specificity rounding from settings
     * @param counts      number of counts the move was from start to end
     * @return String containing the step size formatted for humans to read
     */
    static String getStepSize(Coordinate start, Coordinate end, int specificity, int counts) {
        double computedStepSize;
        double distance = distance(start, end);
        double stepSizeMultiplier = distance / counts;
        if (stepSizeMultiplier == 0.0) {
            computedStepSize = 0.0;
        } else {
            computedStepSize = specificitySwitcher(specificity, STEP_SIZE_REFERENCE / stepSizeMultiplier);
        }
        return "Step Size: " + computedStepSize + " to 5";
    }

    private static double specificitySwitcher(int specificity, double target) {
        switch (specificity) {
            case 0: // No Rounding
                break;
            case 1: // Nearest 1/2 Step
                target = midSetRounder(target, NEAREST_HALF_STEP);
                break;
            case 2: // Nearest 1/4 Step
                target = midSetRounder(target, NEAREST_QUARTER_STEP);
                break;
            case 3: // Nearest 1/8 Step
                target = midSetRounder(target, NEAREST_EIGHTH_STEP);
                break;
            case 4: // Nearest 1/16 Step
                target = midSetRounder(target, NEAREST_SIXTEENTH_STEP);
                break;
            case 5: // Nearest 1/32 Step
                target = midSetRounder(target, NEAREST_THIRTY_SECOND_STEP);
                break;
            default:
                break;
        }
        return target;
    }

    private static double midSetRounder(double target, double amount) {
        return Math.round(target * amount) / amount;
    }
}
