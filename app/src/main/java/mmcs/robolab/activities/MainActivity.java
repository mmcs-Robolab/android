package mmcs.robolab.activities;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import mmcs.robolab.R;
import mmcs.robolab.fragments.DeviceFragment;
import mmcs.robolab.fragments.MainFragment;
import mmcs.robolab.utils.Auth;

public class MainActivity extends AppCompatActivity {

    private MainFragment mainFrag;

    public void onExitClick(View view) {
        Auth.logout(this);
    }

    // todo: extract transaction helper
    public void onDevicesClick() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new DeviceFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFrag = new MainFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, mainFrag);
        transaction.commit();

        ImageView controlImg = (ImageView) findViewById(R.id.controlImg);

        // controlImg.setImageResource(R.drawable.control_clr);
    }

    // todo: extract transaction helper
    @Override
    public void onBackPressed() {
        final int count = getFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
