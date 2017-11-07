package edu.truman.amb1664.marchingbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * @author Andrew Brogan
 * @since 3/21/2017
 */

public class ReviewFragment extends Fragment implements View.OnClickListener {
    // private EditText countText;
    private int counts = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_review_fragment, container, false);
        // New Stuff:
        TextView startPointContent = (TextView) v.findViewById(R.id.textReviewStartPointContent);
        TextView endPointContent = (TextView) v.findViewById(R.id.textReviewEndPointContent);
        final TextView countsContent = (TextView) v.findViewById(R.id.textCounts);
        int fieldType = ((MainActivity) getActivity()).readFieldType();
        int hashType = ((MainActivity) getActivity()).readHashType();
        int sideType = ((MainActivity) getActivity()).readSideType();
        Field f = new Field(fieldType, sideType, hashType);

        Coordinate start = ((MainActivity) getActivity()).getStartCoordinate();
        Coordinate end = ((MainActivity) getActivity()).getEndCoordinate();

        startPointContent.setText(MidSet.printCoordinate(start, f));
        endPointContent.setText(MidSet.printCoordinate(end, f));

        /*
        Counts SeekBar Setup
         */
        final SeekBar seekBarCounts = (SeekBar) v.findViewById(R.id.seekBarCounts);
        seekBarCounts.setMax(63);
        seekBarCounts.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String content;
                if (progress == 0) {
                    content = (progress + 1) + " Count";
                } else {
                    content = (progress + 1) + " Counts";
                }
                countsContent.setText(content);
                counts = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                counts = seekBarCounts.getProgress();
            }
        });

        Button b = (Button) v.findViewById(R.id.buttonCalculate);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        ((MainActivity) getActivity()).setCounts(counts);
        ((MainActivity) getActivity()).replaceFragment(4);
    }

}
