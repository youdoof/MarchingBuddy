package edu.truman.amb1664.marchingbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Brogan on 3/21/2017.
 */

public class EndFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.end_fragment, container, false);
        Button b = (Button) v.findViewById(R.id.buttonPart2);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        ((MainActivity)getActivity()).replaceFragment(3);
    }
}
