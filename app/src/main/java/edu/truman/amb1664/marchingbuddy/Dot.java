package edu.truman.amb1664.marchingbuddy;

import java.text.DecimalFormat;

/**
 * Created by Brogan on 3/21/2017.
 */

public class Dot {
    // Yardline Constants
    private static final int[] yardlines = {50, 45, 40, 35, 30, 25, 20, 15, 10, 5, 0};
    // Stepsize Constant -- 8 to 5
    private static final double STEPS = 8.0;
    // Global Sidelines
    public static double FS = -42.0;
    public static double BS = 42.0;
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

    /**
     * @param value
     * @return
     */
    private static double findYardline(int value) {
        return getArrayIndex(Dot.yardlines, value) * 8;
    }

    /**
     * outputFB Description about what outputFB does
     *
     * @param y          input double representing point on field
     * @param field_type 0 = High School, 1 = NCAA
     * @return formatted String
     */
    static String outputFB(double y, int field_type, int hash_type) {
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
            else if (y < fh && y > (FS - fh))
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
            else if (y > bh && y < (BS - bh))
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
    static String outputLR(double x, int side_type) {
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
     * @param OBF        0-On, 1-Front, 2-Behind
     * @param HS         0-FS, 1-FH, 2-BH, 3-BS
     * @param y          steps
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
        return result;
    }

    private static String midset(double x1, double y1, double x2, double y2, int field_type, int hash_type, int side_type) {
        String out;
        double fb = (y1 + y2) / 2;
        double lr = (x1 + x2) / 2;
        out = "Start: " + outputLR(x1, side_type) + " || " + outputFB(y1, field_type, hash_type) + "\n" +
                "End: " + outputLR(x2, side_type) + " || " + outputFB(y2, field_type, hash_type) + "\n" +
                "Midset: " + outputLR(lr, side_type) + " || " + outputFB(fb, field_type, hash_type);
        return out;
    }

    static String compute(double x1, double y1, double x2, double y2, int counts, int field_type, int hash_type, int side_type) {
        String out1;
        String out2;
        DecimalFormat df = new DecimalFormat("#.###"); // Format how specific stepsize is
        out1 = midset(x1, y1, x2, y2, field_type, hash_type, side_type);
        x1 = Math.abs(x1);
        y1 = Math.abs(y1);
        x2 = Math.abs(x2);
        y2 = Math.abs(y2);
        double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        double stepsizemultiplier = distance / counts;
        double stepsize = STEPS / stepsizemultiplier;
        stepsize = Double.valueOf(df.format(stepsize)); // Currently to 3 decimal places
        out2 = out1 + "\n" + "Stepsize: " + stepsize + " to 5";
        return out2;
    }
}
