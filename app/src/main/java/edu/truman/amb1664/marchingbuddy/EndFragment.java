package edu.truman.amb1664.marchingbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import static edu.truman.amb1664.marchingbuddy.Midset.getBS;
import static edu.truman.amb1664.marchingbuddy.Midset.inputFB;
import static edu.truman.amb1664.marchingbuddy.Midset.inputLR;

/**
 * Created by Brogan on 3/21/2017.
 */

public class EndFragment extends Fragment implements View.OnClickListener {
    // Left to Right variables
    private double steps1;
    private int yardline1;
    // Front to Back variables
    private double y1;
    private EditText yardline;
    private EditText stepsfb;
    private EditText stepslr;
    private RadioButton s1;
    private RadioButton s2;
    private RadioButton on;
    private RadioButton in;
    private RadioButton out;
    private RadioButton onhash;
    private RadioButton infront;
    private RadioButton behind;
    private RadioButton front;
    private RadioButton back;
    private RadioButton hash;
    private RadioButton sideline;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.end_fragment, container, false);
        stepslr = (EditText) v.findViewById(R.id.editStepsLR);
        on = (RadioButton) v.findViewById(R.id.radioOn);
        in = (RadioButton) v.findViewById(R.id.radioInside);
        out = (RadioButton) v.findViewById(R.id.radioOutside);
        s1 = (RadioButton) v.findViewById(R.id.radioSide1);
        s2 = (RadioButton) v.findViewById(R.id.radioSide2);
        yardline = (EditText) v.findViewById(R.id.editYardline);

        stepsfb = (EditText) v.findViewById(R.id.editStepsFB);
        onhash = (RadioButton) v.findViewById(R.id.radioOnHash);
        infront = (RadioButton) v.findViewById(R.id.radioInFront);
        behind = (RadioButton) v.findViewById(R.id.radioBehind);
        front = (RadioButton) v.findViewById(R.id.radioFront);
        back = (RadioButton) v.findViewById(R.id.radioBack);
        hash = (RadioButton) v.findViewById(R.id.radioHash);
        sideline = (RadioButton) v.findViewById(R.id.radioSideline);

        int hashType = ((MainActivity) getActivity()).readHashType();
        int sideType = ((MainActivity) getActivity()).readSideType();

        if (hashType == 1) {
            front.setText(getString(R.string.home));
            back.setText(getString(R.string.visitor));
        }
        if (sideType == 1) {
            s1.setText(getString(R.string.left));
            s2.setText(getString(R.string.right));
        }

        Button b = (Button) v.findViewById(R.id.buttonPart2);
        b.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        ((MainActivity) getActivity()).readFieldType();
        int fieldType = ((MainActivity) getActivity()).readFieldType();
        boolean cont = true;
        ////////////////////
        // Left to Right //
        ///////////////////

        // Step
        //cont = getStep(steps2, cont);
        String tempstep = stepslr.getText().toString();
        final double step = !tempstep.equals("") ? Double.parseDouble(tempstep) : -1;
        if (!isValidLRStep(step)) {
            stepslr.setError("Must be <= 4!");
            cont = false;
        } else
            steps1 = step;

        // On or Inside or Outside
        //cont = getIO(IO2, cont);
        int IO1;
        if (on.isChecked())
            IO1 = 0;
        else if (in.isChecked())
            IO1 = 1;
        else if (out.isChecked())
            IO1 = 2;
        else {
            IO1 = -1;
            cont = false;
        }

        // Side 1 or Side 2
        //cont = getSide(side2, cont);
        int side1;
        if (s1.isChecked())
            side1 = 0;
        else if (s2.isChecked())
            side1 = 1;
        else {
            side1 = -1;
            cont = false;
        }

        // Yardline
        //cont = getYardline(yardline2, cont);
        String tempyardline = yardline.getText().toString();
        final int yard = !tempyardline.equals("") ? Integer.parseInt(tempyardline) : -1;
        if (!isValidLRYardline(yard)) {
            yardline.setError("Must be < 50 and divisible by 5!");
            cont = false;
        } else
            yardline1 = yard;

        ///////////////////
        // Front to Back //
        ///////////////////

        // y1
        //cont = getX(x2, cont);
        String tempstepfb = stepsfb.getText().toString();
        final double stepfb = !tempstepfb.equals("") ? Double.parseDouble(tempstepfb) : -1;
        if (!isValidFBStep(stepfb)) {
            stepsfb.setError("Must be > 0!");
            cont = false;
        } else
            y1 = stepfb;

        // OBF1
        //cont = getOBF(OBF2, cont);
        int OBF1;
        if (onhash.isChecked())
            OBF1 = 0;
        else if (infront.isChecked())
            OBF1 = 1;
        else if (behind.isChecked())
            OBF1 = 2;
        else {
            OBF1 = -1;
            cont = false;
        }

        // HS1
        //cont = getHS(HS2, cont);
        int HS1;
        if (front.isChecked() && sideline.isChecked())
            HS1 = 0;
        else if (front.isChecked() && hash.isChecked())
            HS1 = 1;
        else if (back.isChecked() && hash.isChecked())
            HS1 = 2;
        else if (back.isChecked() && sideline.isChecked())
            HS1 = 3;
        else {
            HS1 = -1;
            cont = false;
        }

        if (cont) {
            MarchingDot end = new MarchingDot(inputLR(steps1, IO1, side1, yardline1), inputFB(y1, OBF1, HS1, fieldType));
            ((MainActivity) getActivity()).setEnd_dot(end);
            ((MainActivity) getActivity()).replaceFragment(3);
        }
    }

    private boolean isValidLRStep(double x) {
        return on.isChecked() || x <= 4 && x > 0;
    }

    private boolean isValidFBStep(double x) {
        return onhash.isChecked() || x > 0 && x < getBS();
    }

    private boolean isValidLRYardline(int x) {
        return x <= 50 && (x % 5 == 0);
    }
}
