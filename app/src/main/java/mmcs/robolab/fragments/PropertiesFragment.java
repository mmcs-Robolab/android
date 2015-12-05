package mmcs.robolab.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import mmcs.robolab.R;
import mmcs.robolab.activities.MainActivity;
import mmcs.robolab.models.preferences.Preferences;
import mmcs.robolab.models.user.User;


public class PropertiesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_properties, container, false);

        Button exitBtn = (Button) v.findViewById(R.id.exitBtn);
        final CheckBox autoLoginCheckBox = (CheckBox) v.findViewById(R.id.autoLoginCheckBox);

        Preferences prefs = User.getInstance().lastLoginPrefs();

        if(prefs.autoLog == 1) {
            autoLoginCheckBox.setChecked(true);
        } else {
            autoLoginCheckBox.setChecked(false);
        }

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();

                main.onExitClick();
            }
        });


        autoLoginCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = User.getInstance().getUserInfo().login;

                if(autoLoginCheckBox.isChecked()) {
                    Preferences prefs = new Preferences(1);

                    prefs.setPreferences(login, prefs);
                } else {
                    Preferences prefs = new Preferences(0);

                    prefs.setPreferences(login, prefs);
                }

            }
        });


        return v;
    }


}
