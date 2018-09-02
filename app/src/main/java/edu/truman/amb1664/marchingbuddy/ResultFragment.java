package edu.truman.amb1664.marchingbuddy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * @author Andrew Brogan
 * @since 3/21/2017
 */

public class ResultFragment extends Fragment implements View.OnClickListener {

    Switch resultSwitchCopy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_result_fragment, container, false);
        Button b = v.findViewById(R.id.buttonFindNext);
        TextView resultStartPointText = v.findViewById(R.id.textResultStartPointContent);
        TextView resultEndPointText = v.findViewById(R.id.textResultEndPointContent);
        TextView resultMidPointText = v.findViewById(R.id.textResultMidpointContent);
        TextView resultStepSizeText = v.findViewById(R.id.textResultStepSizeContent);
        resultSwitchCopy = v.findViewById(R.id.resultSwitchCopy);
        int fieldType = ((MainActivity) getActivity()).readFieldType();
        int hashType = ((MainActivity) getActivity()).readHashType();
        int sideType = ((MainActivity) getActivity()).readSideType();
        Field f = new Field(fieldType, sideType, hashType);
        int specificity = ((MainActivity) getActivity()).readSpecificity();
        Coordinate start = ((MainActivity) getActivity()).getStartCoordinate();
        Coordinate end = ((MainActivity) getActivity()).getEndCoordinate();

        int counts = ((MainActivity) getActivity()).getCounts();

        // Populate Start Point TextView
        resultStartPointText.setText(MidSet.printCoordinate(start, f));
        // Populate End Point TextView
        resultEndPointText.setText(MidSet.printCoordinate(end, f));
        // Populate Midpoint TextView
        resultMidPointText.setText(MidSet.printCoordinate(MidSet.getMidSetCoordinate(start, end, specificity), f));
        // Populate Step Size TextView
        resultStepSizeText.setText(MidSet.getStepSize(start, end, specificity, counts));

        // AdView Setup
        AdView adView = v.findViewById(R.id.resultAdView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);


        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        if (resultSwitchCopy.isChecked()) {
            ((MainActivity) getActivity()).setStartCoordinate(((MainActivity) getActivity()).getEndCoordinate());
            ((MainActivity) getActivity()).replaceFragment(5);
        } else {
            ((MainActivity) getActivity()).replaceFragment(1);
        }

    }
}
