package mmcs.robolab.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import mmcs.robolab.R;
import mmcs.robolab.activities.MainActivity;

public class MainFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        // todo: kill that
        view.setBackgroundColor(Color.parseColor("#cccccc"));

        switch (view.getId()) {
            case R.id.deviceLayout:
                MainActivity main = (MainActivity) getActivity();
                if (main != null) {
                    main.onDevicesClick();
                }
                break;
            case R.id.robotLayout:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_main, null);

        RelativeLayout deviceLayout = (RelativeLayout) v.findViewById(R.id.deviceLayout);
        RelativeLayout robotLayout = (RelativeLayout) v.findViewById(R.id.robotLayout);

        deviceLayout.setOnClickListener(this);
        robotLayout.setOnClickListener(this);
        return v;
    }


}
