package edu.truman.amb1664.marchingbuddy;

import java.text.DecimalFormat;

/**
 * Created by Brogan on 3/21/2017.
 */

class Midset {
    // Yardline Constants
    private static final int[] yardlines = {50, 45, 40, 35, 30, 25, 20, 15, 10, 5, 0};
    // Stepsize Constant -- 8 to 5
    private static final double STEPS = 8.0;
    // Global Sidelines
    private static double FS = -42.0;
    private static double BS = 42.0;
    // Stock NCAA Stuff
    private static double NCAA_FH = -10.0;
    private static double NCAA_BH = 10.0;
    // High School
    private static double HS_FH = -14.0;
    private static double HS_BH = 14.0;
    // Hash Type Constants
    private static String FRONT_HASH = "Front";
    private static String BACK_HASH = "Back";
    private static String HOME_HASH = "Home";
    private static String VISITOR_HASH = "Visitor";
    // Side Type Constants
    private static String ONE_SIDE = "Side 1 ";
    private static String TWO_SIDE = "Side 2 ";
    private static String LEFT_SIDE = "Left ";
    private static String RIGHT_SIDE = "Right ";

    static double getBS() {
        return BS;
    }

    /**
     * @param arr   array of integers
     * @param value the value of the index you want
     * @return index of the array
     */
    private static int getArrayIndex(int[] arr, int value) {
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                k = i;
                break;
            }
        }
        return k;
    }

    private static double findYardline(int value) {
        return getArrayIndex(Midset.yardlines, value) * 8;
    }

    /**
     * outputFB Description about what outputFB does
     *
     * @param y          input double representing point on field
     * @param field_type 0 = High School, 1 = NCAA
     * @return formatted String
     */
    private static String outputFB(double y, int field_type, int hash_type) {
        String output;
        double fh;
        double bh;
        String front;
        String back;

        // Modify type of field from Settings
        if (field_type == 0) {
            fh = HS_FH;
            bh = HS_BH;
        } else {
            fh = NCAA_FH;
            bh = NCAA_BH;
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
            output = -fh + " behind " + front + " Hash";
            // return output;
        }
        // Front half of the field.
        else if (y < 0) {
            if (y > fh)
                output = (-fh + y) + " behind " + front + " Hash";
            else if (y == fh)
                output = "On " + front + " Hash";
            else if (y < fh && y > ((FS + fh) / 2))
                output = (fh + -y) + " in front of " + front + " Hash";
            else if (y > FS)
                output = (-FS + y) + " behind " + front + " Sideline";
            else if (y == FS)
                output = "On " + front + " Sideline";
            else // -FS + x
                output = -(y - FS) + " in front of " + front + " Sideline";
        }
        // Back half of the field.
        else {
            if (y < bh)
                output = (bh - y) + " in front of " + back + " Hash";
            else if (y == bh)
                output = "On " + back + " Hash";
            else if (y > bh && y < ((BS + bh) / 2))
                output = (y - bh) + " behind " + back + " Hash";
            else if (y < BS)
                output = (BS - y) + " in front of " + back + " Sideline";
            else if (y == BS)
                output = "On " + back + " Sideline";
            else
                output = (y - BS) + " behind " + back + " Sideline";
        }

        return output;
    }

    /**
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
                output = "On " + right + yardlines[yardline];
            } else if (step <= 4.0) {
                output = step + " steps Outside " + right + yardlines[yardline];
            } else {
                step = 8 - step;
                output = step + " steps Inside " + right + yardlines[yardline + 1];
            }
        }
        // Side 1, Left Side
        else {
            int yardline = (int) (x / 8);
            double step = x % 8;
            if (step == 0.0) {
                output = "On " + left + yardlines[-yardline];
            } else if (step >= -4.0) {
                output = -step + " steps Outside " + left + yardlines[-yardline];
            } else {
                step = 8 + step;
                output = step + " steps Inside " + left + yardlines[-yardline + 1];
            }
        }
        return output;
    }

    /**
     * @param yardline actual integer value of the yardline
     * @param steps    steps inside or outside
     * @param side     0-S1, 1-S2
     * @param IO       0-On, 1-Inside, 2-Outside
     * @return double with a dingus!
     */
    static double inputLR(double steps, int IO, int side, int yardline) {
        double result = 0;
        // getYardline(yardlines, yardline);
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
        //System.out.println(result);
        return result;
    }

    /**
     * @param y          steps
     * @param OBF        0-On, 1-Front, 2-Behind
     * @param HS         0-FS, 1-FH, 2-BH, 3-BS
     * @param field_type 0 = High School, 1 = NCAA
     * @return double with a dingus!
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
        //System.out.println(result);
        return result;
    }

    private static double distance(MarchingDot start, MarchingDot end) {
        double startX = start.getLeftToRight();
        double startY = start.getFrontToBack();
        double endX = end.getLeftToRight();
        double endY = end.getFrontToBack();
        return Math.sqrt(Math.pow((endX - startX), 2) + Math.pow(endY - startY, 2));
    }

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
