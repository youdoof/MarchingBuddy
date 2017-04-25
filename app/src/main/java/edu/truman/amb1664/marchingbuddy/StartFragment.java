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
import android.widget.Toast;

import java.util.BitSet;

import static edu.truman.amb1664.marchingbuddy.Midset.getBS;
import static edu.truman.amb1664.marchingbuddy.Midset.inputFB;
import static edu.truman.amb1664.marchingbuddy.Midset.inputLR;

/**
 * @author Andrew Brogan
 * @since 3/21/2017
 */

public class StartFragment extends Fragment implements View.OnClickListener {
    // Left to Right variables
    private double stepsLeftRight;
    private int onInOut;
    private int sideOneSideTwo;
    private int yardlineNumber;

    // Front to Back variables
    private double stepsFrontBack;
    private int onFrontBehind;
    private int hashSideline;

    private EditText yardline_edittext;
    private EditText stepsfb_edittext;
    private EditText stepslr_edittext;
    private RadioButton s1_radiobutton;
    private RadioButton s2_radiobutton;
    private RadioButton on_radiobutton;
    private RadioButton in_radiobutton;
    private RadioButton out_radiobutton;
    private RadioButton onhash_radiobutton;
    private RadioButton infront_radiobutton;
    private RadioButton behind_radiobutton;
    private RadioButton front_radiobutton;
    private RadioButton back_radiobutton;
    private RadioButton hash_radiobutton;
    private RadioButton sideline_radiobutton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.start_fragment, container, false);
        stepslr_edittext = (EditText) v.findViewById(R.id.editStepsLR);
        on_radiobutton = (RadioButton) v.findViewById(R.id.radioOn);
        in_radiobutton = (RadioButton) v.findViewById(R.id.radioInside);
        out_radiobutton = (RadioButton) v.findViewById(R.id.radioOutside);
        s1_radiobutton = (RadioButton) v.findViewById(R.id.radioSide1);
        s2_radiobutton = (RadioButton) v.findViewById(R.id.radioSide2);
        yardline_edittext = (EditText) v.findViewById(R.id.editYardline);

        stepsfb_edittext = (EditText) v.findViewById(R.id.editStepsFB);
        onhash_radiobutton = (RadioButton) v.findViewById(R.id.radioOnHash);
        infront_radiobutton = (RadioButton) v.findViewById(R.id.radioInFront);
        behind_radiobutton = (RadioButton) v.findViewById(R.id.radioBehind);
        front_radiobutton = (RadioButton) v.findViewById(R.id.radioFront);
        back_radiobutton = (RadioButton) v.findViewById(R.id.radioBack);
        hash_radiobutton = (RadioButton) v.findViewById(R.id.radioHash);
        sideline_radiobutton = (RadioButton) v.findViewById(R.id.radioSideline);

        int hashType = ((MainActivity) getActivity()).readHashType();
        int sideType = ((MainActivity) getActivity()).readSideType();

        if (hashType == 1) {
            front_radiobutton.setText(getString(R.string.home));
            back_radiobutton.setText(getString(R.string.visitor));
        }
        if (sideType == 1) {
            s1_radiobutton.setText(getString(R.string.left));
            s2_radiobutton.setText(getString(R.string.right));
        }

        Button b = (Button) v.findViewById(R.id.buttonPart1);

        b.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        int fieldType = ((MainActivity) getActivity()).readFieldType();

        checkInputError();

        if (checkCompletion()) {
            MarchingDot start = new MarchingDot(inputLR(stepsLeftRight, onInOut, sideOneSideTwo, yardlineNumber), inputFB(stepsFrontBack, onFrontBehind, hashSideline, fieldType));
            ((MainActivity) getActivity()).setStart_dot(start);
            ((MainActivity) getActivity()).replaceFragment(2);
        }
    }

    private void checkInputError() {
        BitSet groupOnInOut = new BitSet(3);
        BitSet groupSides = new BitSet(2);
        BitSet groupOnFrontBehind = new BitSet(3);
        BitSet groupFrontBack = new BitSet(2);
        BitSet groupHashSideline = new BitSet(2);
        BitSet groupBitSets = new BitSet(5);
        int hashtype = ((MainActivity) getActivity()).readHashType();
        int sidetype = ((MainActivity) getActivity()).readSideType();
        String toastString = "Please Check Radio Buttons:\n";

        groupOnInOut.set(0, on_radiobutton.isChecked());
        groupOnInOut.set(1, in_radiobutton.isChecked());
        groupOnInOut.set(2, out_radiobutton.isChecked());
        groupSides.set(0, s1_radiobutton.isChecked());
        groupSides.set(1, s2_radiobutton.isChecked());
        groupOnFrontBehind.set(0, onhash_radiobutton.isChecked());
        groupOnFrontBehind.set(1, infront_radiobutton.isChecked());
        groupOnFrontBehind.set(2, behind_radiobutton.isChecked());
        groupFrontBack.set(0, front_radiobutton.isChecked());
        groupFrontBack.set(1, back_radiobutton.isChecked());
        groupHashSideline.set(0, hash_radiobutton.isChecked());
        groupHashSideline.set(1, sideline_radiobutton.isChecked());

        // Group On In Out
        if (groupOnInOut.cardinality() != 1)
            toastString = toastString + "On In Out\n";
        else
            groupBitSets.set(0);

        // Group Sides
        if (groupSides.cardinality() != 1) {
            if (sidetype == 0)
                toastString = toastString + "Side 1 Side 2\n";
            else
                toastString = toastString + "Left Right\n";
        } else
            groupBitSets.set(1);

        // Group On Front Behind
        if (groupOnFrontBehind.cardinality() != 1)
            toastString = toastString + "On Front Behind\n";
        else
            groupBitSets.set(2);

        // Group Front Back
        if (groupFrontBack.cardinality() != 1) {
            if (hashtype == 0)
                toastString = toastString + "Front Back\n";
            else
                toastString = toastString + "Home Visitor\n";
        } else
            groupBitSets.set(3);

        // Group Hash Sideline
        if (groupHashSideline.cardinality() != 1)
            toastString = toastString + "Hash Sideline\n";
        else
            groupBitSets.set(4);

        // Check if all groups were set. If all were set, then
        // one of each group was selected, and no Toast needed.
        if (groupBitSets.cardinality() != 5) {
            toastString = toastString + "Thanks!";
            Toast.makeText(getContext(), toastString, Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkCompletion() {
        BitSet complete = new BitSet(7);
        complete.set(0, getStepsLeftRight());
        complete.set(1, getOnInOut());
        complete.set(2, getSideOneSideTwo());
        complete.set(3, getYardlineNumber());
        complete.set(4, getStepsFrontBack());
        complete.set(5, getOnFrontBehind());
        complete.set(6, getHashSideline());
        return complete.cardinality() == 7;
    }

    private boolean getStepsLeftRight() {
        String tempStepLR = stepslr_edittext.getText().toString();
        if (doubleInputRegex(tempStepLR)) {
            double step = Double.parseDouble(tempStepLR);
            if (!isValidLRStep(step)) {
                stepslr_edittext.setError("Must be between 0 and 4.");
                return false;
            } else
                stepsLeftRight = step;
            return true;
        } else if (on_radiobutton.isChecked()) {
            return true;
        } else {
            stepslr_edittext.setError("Enter a number between 0 and 4.");
            return false;
        }
    }

    private boolean getOnInOut() {
        if (on_radiobutton.isChecked()) {
            onInOut = 0;
            return true;
        } else if (in_radiobutton.isChecked()) {
            onInOut = 1;
            return true;
        } else if (out_radiobutton.isChecked()) {
            onInOut = 2;
            return true;
        } else
            return false;
    }

    private boolean getSideOneSideTwo() {
        if (s1_radiobutton.isChecked()) {
            sideOneSideTwo = 0;
            return true;
        } else if (s2_radiobutton.isChecked()) {
            sideOneSideTwo = 1;
            return true;
        } else
            return false;
    }

    private boolean getYardlineNumber() {
        String tempyardline = yardline_edittext.getText().toString();
        final int yard = !tempyardline.equals("") ? Integer.parseInt(tempyardline) : -1;
        if (!isValidLRYardline(yard)) {
            yardline_edittext.setError("Must be <= 50 and divisible by 5.");
            return false;
        } else {
            yardlineNumber = yard;
            return true;
        }
    }

    private boolean getStepsFrontBack() {
        String tempStepFB = stepsfb_edittext.getText().toString();
        if (doubleInputRegex(tempStepFB)) {
            double stepfb = Double.parseDouble(tempStepFB);
            if (!isValidFBStep(stepfb)) {
                if (stepfb > getBS()) {
                    stepsfb_edittext.setError("Must be less than " + getBS() + ".");
                } else {
                    stepsfb_edittext.setError("Must be > 0.");
                }
                return false;
            } else {
                stepsFrontBack = stepfb;
                return true;
            }
        } else if (onhash_radiobutton.isChecked()) {
            return true;
        } else {
            stepsfb_edittext.setError("Enter a number > 0.");
            return false;
        }
    }

    private boolean getOnFrontBehind() {
        if (onhash_radiobutton.isChecked()) {
            onFrontBehind = 0;
            return true;
        } else if (infront_radiobutton.isChecked()) {
            onFrontBehind = 1;
            return true;
        } else if (behind_radiobutton.isChecked()) {
            onFrontBehind = 2;
            return true;
        } else
            return false;
    }

    private boolean getHashSideline() {
        if (front_radiobutton.isChecked() && sideline_radiobutton.isChecked()) {
            hashSideline = 0;
            return true;
        } else if (front_radiobutton.isChecked() && hash_radiobutton.isChecked()) {
            hashSideline = 1;
            return true;
        } else if (back_radiobutton.isChecked() && hash_radiobutton.isChecked()) {
            hashSideline = 2;
            return true;
        } else if (back_radiobutton.isChecked() && sideline_radiobutton.isChecked()) {
            hashSideline = 3;
            return true;
        } else
            return false;
    }

    private boolean isValidLRStep(double x) {
        return on_radiobutton.isChecked() || x <= 4 && x > 0;
    }

    private boolean isValidFBStep(double x) {
        return onhash_radiobutton.isChecked() || x > 0 && x < getBS();
    }

    private boolean isValidLRYardline(int x) {
        return x <= 50 && (x % 5 == 0);
    }

    private boolean doubleInputRegex(String s) {
        String pattern = "[0-9]*\\.?[0-9]+";
        return s.matches(pattern);
    }
}
