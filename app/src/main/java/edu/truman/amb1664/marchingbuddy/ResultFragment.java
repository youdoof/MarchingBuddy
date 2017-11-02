package edu.truman.amb1664.marchingbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Andrew Brogan
 * @since 3/21/2017
 */

public class ResultFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.result_fragment, container, false);
        Button b = (Button) v.findViewById(R.id.buttonFindNext);
        Button b2 = (Button) v.findViewById(R.id.buttonUseEnd);
        TextView display = (TextView) v.findViewById(R.id.textDisplay);
        int fieldType = ((MainActivity) getActivity()).readFieldType();
        int hashType = ((MainActivity) getActivity()).readHashType();
        int sideType = ((MainActivity) getActivity()).readSideType();
        Field f = new Field(fieldType, sideType, hashType);
        int specificity = ((MainActivity) getActivity()).readSpecificity();
        Coordinate start = ((MainActivity) getActivity()).getStartCoordinate();
        Coordinate end = ((MainActivity) getActivity()).getEndCoordinate();
        int counts = ((MainActivity) getActivity()).getCounts();

        display.setText(Midset.getMidsetInformation(start, end, counts, f, specificity));

        b.setOnClickListener(this);
        b2.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFindNext:
                ((MainActivity) getActivity()).replaceFragment(1);
                break;
            case R.id.buttonUseEnd:
                ((MainActivity) getActivity()).setStartCoordinate(((MainActivity) getActivity()).getEndCoordinate());
                ((MainActivity) getActivity()).replaceFragment(5);
                break;
            default:
                break;
        }
    }
}
