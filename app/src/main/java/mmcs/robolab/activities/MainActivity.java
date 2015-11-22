package mmcs.robolab.activities;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import mmcs.robolab.R;
import mmcs.robolab.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    private MainFragment mainFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFrag = new MainFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, mainFrag);
        transaction.commit();

        ImageView controlImg = (ImageView) findViewById(R.id.controlImg);

        controlImg.setImageResource(R.drawable.control_clr);  //"@drawable/control_clr");
    }
}
