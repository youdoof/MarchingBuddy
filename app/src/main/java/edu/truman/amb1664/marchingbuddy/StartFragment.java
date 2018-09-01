package edu.truman.amb1664.marchingbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.BitSet;

import static edu.truman.amb1664.marchingbuddy.MidSet.inputFrontToBack;
import static edu.truman.amb1664.marchingbuddy.MidSet.inputLeftToRight;

/**
 * @author Andrew Brogan
 * @since 3/21/2017
 */

public class StartFragment extends Fragment implements View.OnClickListener {
    // Left to Right variables
    private double stepsLeftRight;
    private int onInOut;
    private int sideOneSideTwo;
    private int yardLineNumber;

    // Front to Back variables
    private double stepsFrontBack;
    private int onFrontBehind;
    private int hashSideline;

    // TextViews which are updated by seek bars
    private TextView stepslr_textview;
    private TextView yardline_textview;
    private TextView stepsfb_textview;

    // Radio Group On In Out
    private RadioGroup groupOnInOut;
    private RadioButton on_radiobutton;
    private RadioButton in_radiobutton;
    private RadioButton out_radiobutton;

    // Radio Group Sides
    private RadioGroup groupSides;
    private RadioButton s1_radiobutton;
    private RadioButton s2_radiobutton;

    // Radio Group On Front Behind
    private RadioGroup groupOnFrontBehind;
    private RadioButton onhash_radiobutton;
    private RadioButton infront_radiobutton;
    private RadioButton behind_radiobutton;

    // Radio Group Front Back
    private RadioGroup groupFrontBack;
    private RadioButton front_radiobutton;
    private RadioButton back_radiobutton;

    // Radio Group Hash Sideline
    private RadioGroup groupHashSideline;
    private RadioButton hash_radiobutton;
    private RadioButton sideline_radiobutton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.input_fragment, container, false);
        stepslr_textview = (TextView) v.findViewById(R.id.textStepsLR);
        groupOnInOut = (RadioGroup) v.findViewById(R.id.radioGroupOnInOut);
        on_radiobutton = (RadioButton) v.findViewById(R.id.radioOn);
        in_radiobutton = (RadioButton) v.findViewById(R.id.radioInside);
        out_radiobutton = (RadioButton) v.findViewById(R.id.radioOutside);
        groupSides = (RadioGroup) v.findViewById(R.id.radioGroupSides);
        s1_radiobutton = (RadioButton) v.findViewById(R.id.radioSide1);
        s2_radiobutton = (RadioButton) v.findViewById(R.id.radioSide2);
        yardline_textview = (TextView) v.findViewById(R.id.textYardline);

        stepsfb_textview = (TextView) v.findViewById(R.id.textStepsFB);
        groupOnFrontBehind = (RadioGroup) v.findViewById(R.id.radioGroupOnFrontBehind);
        onhash_radiobutton = (RadioButton) v.findViewById(R.id.radioOnHash);
        infront_radiobutton = (RadioButton) v.findViewById(R.id.radioInFront);
        behind_radiobutton = (RadioButton) v.findViewById(R.id.radioBehind);
        groupFrontBack = (RadioGroup) v.findViewById(R.id.radioGroupFrontBack);
        front_radiobutton = (RadioButton) v.findViewById(R.id.radioFront);
        back_radiobutton = (RadioButton) v.findViewById(R.id.radioBack);
        groupHashSideline = (RadioGroup) v.findViewById(R.id.radioGroupHashSideline);
        hash_radiobutton = (RadioButton) v.findViewById(R.id.radioHash);
        sideline_radiobutton = (RadioButton) v.findViewById(R.id.radioSideline);

        TextView startFragmentText = (TextView) v.findViewById(R.id.textPointTitle);
        startFragmentText.setText(getResources().getString(R.string.start_point));

        // Final specificity grab from preferences to get to rounding
        final int spec = ((MainActivity) getActivity()).getConvertedSpecificity();

        // AdView Setup
        AdView adView = (AdView) v.findViewById(R.id.inputAdView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);


        /*
         * Left To Right SeekBar Setup
         *
         */
        final SeekBar stepslr_seekbar = (SeekBar) v.findViewById(R.id.seekBarStepsLR);
        stepslr_seekbar.setMax(seekBarLRMaxCalc(spec));
        stepslr_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String content = intervalCalc(progress, spec) + " Steps";
                stepslr_textview.setText(content);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /*
         * Yard Line SeekBar Setup
         *
         */
        final SeekBar yardline_seekbar = (SeekBar) v.findViewById(R.id.seekBarYardline);
        // sets maximum to one less than the length of the YardLines array
        yardline_seekbar.setMax(MidSet.YARD_LINES.length - 1);
        yardline_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String content = MidSet.getYardLine(progress) + " Yard Line";
                yardline_textview.setText(content);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /*
         * Front To Back SeekBar Setup
         *
         */

        final SeekBar stepsfb_seekbar = (SeekBar) v.findViewById(R.id.seekBarStepsFB);
        // Arbitrarily chosen this number of 32. Might need to revisit this sometime afterwards.
        stepsfb_seekbar.setMax(seekBarFBMaxCalc(spec));
        stepsfb_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String content = intervalCalc(progress, spec) + " Steps";
                stepsfb_textview.setText(content);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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
        int hashType = ((MainActivity) getActivity()).readHashType();
        int sideType = ((MainActivity) getActivity()).readSideType();
        Field f = new Field(fieldType, sideType, hashType);

        checkInputErrorAgain();

        if (checkCompletion()) {
            double leftToRight = inputLeftToRight(stepsLeftRight, onInOut, sideOneSideTwo, yardLineNumber);
            double frontToBack = inputFrontToBack(stepsFrontBack, onFrontBehind, hashSideline, f);
            Coordinate start = new Coordinate(leftToRight, frontToBack);
            ((MainActivity) getActivity()).setStartCoordinate(start);
            ((MainActivity) getActivity()).replaceFragment(2);
        }
    }

    /**
     * intervalCalc produces the value located at the position
     * of the seek bar's progress
     *
     * @param progress    the progress from the seek bar
     * @param specificity current rounding specificity
     * @return value represented at the position of the seek bar
     */
    private double intervalCalc(int progress, int specificity) {
        return (double) progress / (double) specificity;
    }

    /**
     * Helps setup Left to Right SeekBar
     *
     * @param specificity how specific the sliders will be
     * @return max for the left to right seekbar
     */
    private int seekBarLRMaxCalc(int specificity) {
        return specificity * 4;
    }

    /**
     * Helps setup Front to Back SeekBar
     *
     * @param specificity how specific the sliders will be
     * @return max for the front to back seekbar
     */
    private int seekBarFBMaxCalc(int specificity) {
        return specificity * 16;
    }

    /**
     * Checks all radio buttons to ensure all were pressed,
     * sets an error on the group that does not have a
     * button selected.
     */
    private void checkInputErrorAgain() {
        BitSet groupBitSets = new BitSet(5);
        if (groupOnInOut.getCheckedRadioButtonId() <= 0) {
            out_radiobutton.setError("Select Item");
        } else {
            out_radiobutton.setError(null);
            groupBitSets.set(0);
        }

        if (groupSides.getCheckedRadioButtonId() <= 0) {
            s2_radiobutton.setError("Select Item");
        } else {
            s2_radiobutton.setError(null);
        }

        if (groupOnFrontBehind.getCheckedRadioButtonId() <= 0) {
            behind_radiobutton.setError("Select Item");
        } else {
            behind_radiobutton.setError(null);
        }

        if (groupFrontBack.getCheckedRadioButtonId() <= 0) {
            back_radiobutton.setError("Select Item");
        } else {
            back_radiobutton.setError(null);
        }

        if (groupHashSideline.getCheckedRadioButtonId() <= 0) {
            sideline_radiobutton.setError("Select Item");
        } else {
            sideline_radiobutton.setError(null);
        }

    }


    /**
     * checks to see that all the bitset groups have completed
     *
     * @return true if complete, false otherwise
     */
    private boolean checkCompletion() {
        BitSet complete = new BitSet(7);
        complete.set(0, getStepsLeftRight());
        complete.set(1, getOnInOut());
        complete.set(2, getSideOneSideTwo());
        complete.set(3, getYardLineNumber());
        complete.set(4, getStepsFrontBack());
        complete.set(5, getOnFrontBehind());
        complete.set(6, getHashSideline());
        return complete.cardinality() == 7;
    }

    private boolean getStepsLeftRight() {
        SeekBar seekBar = (SeekBar) getView().findViewById(R.id.seekBarStepsLR);
        stepsLeftRight = intervalCalc(seekBar.getProgress(), ((MainActivity) getActivity()).getConvertedSpecificity());
        return true;
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

    private boolean getYardLineNumber() {
        SeekBar seekBar = (SeekBar) getView().findViewById(R.id.seekBarYardline);
        yardLineNumber = MidSet.getYardLine(seekBar.getProgress());
        return true;
    }

    private boolean getStepsFrontBack() {
        SeekBar seekBar = (SeekBar) getView().findViewById(R.id.seekBarStepsFB);
        stepsFrontBack = intervalCalc(seekBar.getProgress(), ((MainActivity) getActivity()).getConvertedSpecificity());
        return true;
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
}
