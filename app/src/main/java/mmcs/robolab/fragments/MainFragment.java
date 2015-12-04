package mmcs.robolab.fragments;
import android.support.annotation.UiThread;

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

    @Override @UiThread
    public void onClick(View view) {
        // todo: check <|> kill that
        view.setBackgroundColor(Color.parseColor("#cccccc"));

        MainActivity main = (MainActivity) getActivity();
        switch (view.getId()) {
            case R.id.deviceLayout:
                if (main != null) {
                    main.onDevicesClick();
                }
                break;
            case R.id.robotLayout:
                if (main != null) {
                    main.onRobotsClick();
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        RelativeLayout deviceLayout = (RelativeLayout) v.findViewById(R.id.deviceLayout);
        RelativeLayout robotLayout = (RelativeLayout) v.findViewById(R.id.robotLayout);

        deviceLayout.setOnClickListener(this);
        robotLayout.setOnClickListener(this);
        return v;
    }


}
