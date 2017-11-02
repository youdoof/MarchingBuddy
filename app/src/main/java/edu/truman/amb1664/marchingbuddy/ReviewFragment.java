package edu.truman.amb1664.marchingbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Integer.parseInt;

/**
 * @author Andrew Brogan
 * @since 3/21/2017
 */

public class ReviewFragment extends Fragment implements View.OnClickListener {
    private EditText countText;
    private int counts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.review_fragment, container, false);
        TextView review = (TextView) v.findViewById(R.id.textPreview);
        countText = (EditText) v.findViewById(R.id.editCounts);
        int fieldType = ((MainActivity) getActivity()).readFieldType();
        int hashType = ((MainActivity) getActivity()).readHashType();
        int sideType = ((MainActivity) getActivity()).readSideType();
        Field f = new Field(fieldType, sideType, hashType);
        Coordinate start = ((MainActivity) getActivity()).getStartCoordinate();
        Coordinate end = ((MainActivity) getActivity()).getEndCoordinate();

        review.setText(Midset.review(start, end, f));

        Button b = (Button) v.findViewById(R.id.buttonCompute);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        boolean cont = true;
        String tempcount = countText.getText().toString();
        final int c = !tempcount.equals("") ? parseInt(tempcount) : -1;
        if (!isValidCount(c)) {
            countText.setError("Must be > 0!");
            cont = false;
        } else
            counts = c;

        if (cont) {
            ((MainActivity) getActivity()).setCounts(counts);
            ((MainActivity) getActivity()).replaceFragment(4);
        }
    }

    private boolean isValidCount(int c) {
        return c > 0;
    }
}
