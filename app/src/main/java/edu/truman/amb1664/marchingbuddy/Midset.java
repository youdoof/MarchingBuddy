package edu.truman.amb1664.marchingbuddy;

import java.text.DecimalFormat;

/**
 * Midset is a helper class that has the methods to
 * compute the midset and step size for each set.
 *
 * @author Andrew Brogan
 * @since 12/18/16
 */
class Midset {
    // Yardline Constants
    private static final int[] YARDLINES = {50, 45, 40, 35, 30, 25, 20, 15, 10, 5, 0};
    // Hash Type Constants
    private static final String FRONT_HASH = "Front";
    private static final String BACK_HASH = "Back";
    private static final String HOME_HASH = "Home";
    private static final String VISITOR_HASH = "Visitor";
    // Side Type Constants
    private static final String ONE_SIDE = "Side 1: ";
    private static final String TWO_SIDE = "Side 2: ";
    private static final String LEFT_SIDE = "Left: ";
    private static final String RIGHT_SIDE = "Right: ";
    // Field Type Constants
    private static final String HIGH_SCHOOL = " (HS)";
    private static final String NCAA_COLLEGE = " (NCAA)";
    // Stepsize Constant -- 8 to 5
    private static final double STEPS = 8.0;
    // Global Sidelines
    private static final double FS = -42.0;
    private static final double BS = 42.0;
    // Stock NCAA Stuff
    private static final double NCAA_FH = -10.0;
    private static final double NCAA_BH = 10.0;
    // High School
    private static final double HS_FH = -14.0;
    private static final double HS_BH = 14.0;

    static double getBS() {
        return BS;
    }

    /**
     * This method gets the array index of the value you input.
     *
     * @param value the value of the index you want
     * @return index of the array
     */
    private static int getArrayIndex(int value) {
        int k = 0;
        for (int i = 0; i < Midset.YARDLINES.length; i++) {
            if (Midset.YARDLINES[i] == value) {
                k = i;
                break;
            }
        }
        return k;
    }

    /**
     * This method takes the yardline given and returns the representation of that yardline
     * in the internal coordinate plane, which is where all midsets are calculated.
     *
     * @param value yardline you want to be turned into coordinate
     * @return double representing the yardline in a coordinate plane
     */
    private static double findYardline(int value) {
        return getArrayIndex(value) * 8;
    }

    /**
     * outputFB Takes a double y and two settings types and translates the double y
     * into the representation format understood by human marchers, using terminology
     * set by the settings types.
     *
     * @param y          input double representing point on field
     * @param field_type 0 = High School, 1 = NCAA
     * @param hash_type  0 = Front/Back, 1 = Home/Visitor
     * @return formatted String
     */
    private static String outputFB(double y, int field_type, int hash_type) {
        String output;
        double fh;
        double bh;
        String front;
        String back;
        String field;

        // Modify type of field from Settings
        if (field_type == 0) {
            fh = HS_FH;
            bh = HS_BH;
            field = HIGH_SCHOOL;
        } else {
            fh = NCAA_FH;
            bh = NCAA_BH;
            field = NCAA_COLLEGE;
        }

        // Modify type of hash from Settings
        if (hash_type == 0) {
            front = FRONT_HASH;
            back = BACK_HASH;
        } else {
            front = HOME_HASH;
            back = VISITOR_HASH;
        }

        // Middle of the field.
        if (y == 0) {
            output = -fh + " steps behind " + front + " hash" + field;
            // return output;
        }
        // Front half of the field.
        else if (y < 0) {
            if (y > fh)
                output = (-fh + y) + " steps behind " + front + " hash" + field;
            else if (y == fh)
                output = "On " + front + " hash" + field;
            else if (y < fh && y > ((FS + fh) / 2))
                output = (fh + -y) + " steps in front of " + front + " hash" + field;
            else if (y > FS)
                output = (-FS + y) + " steps behind " + front + " sideline" + field;
            else if (y == FS)
                output = "On " + front + " sideline" + field;
            else // -FS + x
                output = -(y - FS) + " steps in front of " + front + " sideline" + field;
        }
        // Back half of the field.
        else {
            if (y < bh)
                output = (bh - y) + " steps in front of " + back + " hash" + field;
            else if (y == bh)
                output = "On " + back + " hash" + field;
            else if (y > bh && y < ((BS + bh) / 2))
                output = (y - bh) + " steps behind " + back + " hash" + field;
            else if (y < BS)
                output = (BS - y) + " steps in front of " + back + " sideline" + field;
            else if (y == BS)
                output = "On " + back + " sideline" + field;
            else
                output = (y - BS) + " steps behind " + back + " sideline" + field;
        }

        return output;
    }

    /**
     * outputLR Takes a double x and one setting type and translates the double x
     * into the representation format understood by human marchers, using terminology
     * set by the setting type.
     *
     * @param x         double to be converted to text
     * @param side_type 0 = Side 1/Side 2, 1 = Left/Right
     * @return Formatted String to be printed to console
     */
    private static String outputLR(double x, int side_type) {
        String output;
        String left;
        String right;

        // Set side type from Settings
        if (side_type == 0) {
            left = ONE_SIDE;
            right = TWO_SIDE;
        } else {
            left = LEFT_SIDE;
            right = RIGHT_SIDE;
        }


        // 50 Yardline (Middle)
        if (x == 0.0) {
            output = "On 50";
        }
        // Side 2, Right Side
        else if (x > 0) {
            int yardline = (int) (x / 8);
            double step = x % 8;
            if (step == 0.0) {
                output = right + "On " + YARDLINES[yardline];
            } else if (step <= 4.0) {
                output = right + step + " steps outside " + YARDLINES[yardline];
            } else {
                step = 8 - step;
                output = right + step + " steps inside " + YARDLINES[yardline + 1];
            }
        }
        // Side 1, Left Side
        else {
            int yardline = (int) (x / 8);
            double step = x % 8;
            if (step == 0.0) {
                output = left + "On " + YARDLINES[-yardline];
            } else if (step >= -4.0) {
                output = left + -step + " steps outside " + YARDLINES[-yardline];
            } else {
                step = 8 + step;
                output = left + step + " steps inside " + YARDLINES[-yardline + 1];
            }
        }
        return output;
    }

    /**
     * inputLR translates the input received from the GUI and translates it into a single
     * double value that the midset system understands. (Left to Right)
     *
     * @param yardline actual integer value of the yardline
     * @param steps    steps inside or outside
     * @param side     0-S1, 1-S2
     * @param IO       0-On, 1-Inside, 2-Outside
     * @return double equivalent of input left to right coordinate
     */
    static double inputLR(double steps, int IO, int side, int yardline) {
        double result = 0;
        // getYardline(YARDLINES, yardline);
        switch (IO) {
            // On Yardline
            case 0:
                if (side == 0)
                    result = -findYardline(yardline);
                else
                    result = findYardline(yardline);
                break;
            // Inside Yardline
            case 1:
                if (yardline == 50)
                    if (side == 0)
                        result = -steps;
                    else
                        result = steps;
                else if (side == 0)
                    result = -findYardline(yardline) + steps;
                else
                    result = findYardline(yardline) - steps;
                break;
            // Outside Yardline
            case 2:
                if (yardline == 50)
                    if (side == 0)
                        result = -steps;
                    else
                        result = steps;
                else if (side == 0)
                    result = -findYardline(yardline) - steps;
                else
                    result = findYardline(yardline) + steps;
                break;
        }
        return result;
    }

    /**
     * inputFB translates the input received from the GUI and translates it into a single
     * double value that the midset system understands. (Front to Back)
     *
     * @param y          steps
     * @param OBF        0-On, 1-Front, 2-Behind
     * @param HS         0-FS, 1-FH, 2-BH, 3-BS
     * @param field_type 0 = High School, 1 = NCAA
     * @return double equivalent of input front to back coordinate
     */
    static double inputFB(double y, int OBF, int HS, int field_type) {
        double result = 0;
        double fh;
        double bh;

        if (field_type == 0) {
            fh = HS_FH;
            bh = HS_BH;
        } else {
            fh = NCAA_FH;
            bh = NCAA_BH;
        }
        switch (HS) {
            // FS
            case 0:
                if (OBF == 0)
                    result = FS;
                else if (OBF == 1)
                    result = FS - y;
                else
                    result = FS + y;
                break;
            // FH
            case 1:
                if (OBF == 0)
                    result = fh;
                else if (OBF == 1)
                    result = fh - y;
                else
                    result = fh + y;
                break;
            // BH
            case 2:
                if (OBF == 0)
                    result = bh;
                else if (OBF == 1)
                    result = bh - y;
                else
                    result = bh + y;
                break;
            // BS
            case 3:
                if (OBF == 0)
                    result = BS;
                else if (OBF == 1)
                    result = BS - y;
                else
                    result = BS + y;
                break;
        }
        return result;
    }

    /**
     * This method computes the distance between to MarchingDot objects,
     * using the distance formula for coordinates.
     *
     * @param start initial MarchingDot
     * @param end   final MarchingDot
     * @return distance between the two MarchingDots
     */
    private static double distance(MarchingDot start, MarchingDot end) {
        double startX = start.getLeftToRight();
        double startY = start.getFrontToBack();
        double endX = end.getLeftToRight();
        double endY = end.getFrontToBack();
        return Math.sqrt(Math.pow((endX - startX), 2) + Math.pow(endY - startY, 2));
    }

    /**
     * review takes the input start and end dots and produces a formatted string
     * to display the data that was entered by the user
     *
     * @param start      initial MarchingDot
     * @param end        final MarchingDot
     * @param field_type 0 = High School, 1 = NCAA
     * @param hash_type  0 = Front/Back, 1 = Home/Visitor
     * @param side_type  0 = Side 1/Side 2, 1 = Left/Right
     * @return formatted String representing given inputs for both MarchingDots
     */
    static String review(MarchingDot start, MarchingDot end, int field_type, int hash_type, int side_type) {
        String output;
        output = "Start:\n" +
                outputLR(start.getLeftToRight(), side_type) + "\n" +
                outputFB(start.getFrontToBack(), field_type, hash_type) + "\n\n" +
                "End:\n" +
                outputLR(end.getLeftToRight(), side_type) + "\n" +
                outputFB(end.getFrontToBack(), field_type, hash_type);
        return output;
    }

    /**
     * getMidsetInfo creates a formatted string to display the
     * start set, end set, mid set, and step size for the move
     * given by the user.
     *
     * @param start       initial MarchingDot
     * @param end         final MarchingDot
     * @param counts      number of counts to get from start to end
     * @param field_type  0 = High School, 1 = NCAA
     * @param hash_type   0 = Front/Back, 1 = Home/Visitor
     * @param side_type   0 = Side 1/Side 2, 1 = Left/Right
     * @param specificity 0 = no rounding, 1 = 1 decimal place, 2 = 2 decimal places, 3 = 3 decimal places
     * @return formatted String including: start set, end set, mid set, and step size
     */
    static String getMidsetInfo(MarchingDot start, MarchingDot end, int counts, int field_type, int hash_type, int side_type, int specificity) {
        String output;
        DecimalFormat decimalFormat;
        double stepsize;

        double fb = (start.getFrontToBack() + end.getFrontToBack()) / 2;
        double lr = (start.getLeftToRight() + end.getLeftToRight()) / 2;

        double distance = distance(start, end);

        double stepSizeMultiplier = distance / counts;
        if (stepSizeMultiplier == 0.0) {
            stepsize = 0.0;
        } else {
            stepsize = STEPS / stepSizeMultiplier;
        }


        switch (specificity) {
            case 0:
                break;
            case 1:
                decimalFormat = new DecimalFormat("#.#");
                stepsize = Double.valueOf(decimalFormat.format(stepsize));
                fb = Double.valueOf(decimalFormat.format(fb));
                lr = Double.valueOf(decimalFormat.format(lr));
                break;
            case 2:
                decimalFormat = new DecimalFormat("#.##");
                stepsize = Double.valueOf(decimalFormat.format(stepsize));
                fb = Double.valueOf(decimalFormat.format(fb));
                lr = Double.valueOf(decimalFormat.format(lr));
                break;
            case 3:
                decimalFormat = new DecimalFormat("#.###");
                stepsize = Double.valueOf(decimalFormat.format(stepsize));
                fb = Double.valueOf(decimalFormat.format(fb));
                lr = Double.valueOf(decimalFormat.format(lr));
                break;
            default:
                break;
        }

        output = "Start:\n" +
                outputLR(start.getLeftToRight(), side_type) + "\n" +
                outputFB(start.getFrontToBack(), field_type, hash_type) + "\n\n" +
                "End:\n" +
                outputLR(end.getLeftToRight(), side_type) + "\n" +
                outputFB(end.getFrontToBack(), field_type, hash_type) + "\n\n" +
                "Midset:\n" +
                outputLR(lr, side_type) + "\n" +
                outputFB(fb, field_type, hash_type) + "\n\n" +
                "Stepsize:\n" +
                stepsize + " to 5";

        return output;
    }
}
