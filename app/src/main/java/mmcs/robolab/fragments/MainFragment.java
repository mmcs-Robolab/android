package mmcs.robolab.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import mmcs.robolab.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_main, null);

        RelativeLayout deviceLayout = (RelativeLayout) v.findViewById(R.id.deviceLayout);
        RelativeLayout robotLayout = (RelativeLayout) v.findViewById(R.id.robotLayout);

        View.OnClickListener layoutClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(Color.parseColor("#cccccc"));



            }
        };


        deviceLayout.setOnClickListener(layoutClick);
        robotLayout.setOnClickListener(layoutClick);
        return v;
    }


}