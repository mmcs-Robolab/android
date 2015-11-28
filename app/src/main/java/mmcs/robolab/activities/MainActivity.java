package mmcs.robolab.activities;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import mmcs.robolab.R;
import mmcs.robolab.fragments.DeviceFragment;
import mmcs.robolab.fragments.MainFragment;
import mmcs.robolab.models.user.User;
import mmcs.robolab.models.user.UserInfo;
import mmcs.robolab.utils.Auth;
import mmcs.robolab.utils.Transaction;

public class MainActivity extends AppCompatActivity {

    private Transaction trans;
    private User user;
    private UserInfo userInfo;

    public void onExitClick(View view) {
        Auth.logout(this);
    }

    public void onDevicesClick() {
        final Fragment fragment = new DeviceFragment();
        this.trans.replace(R.id.content, fragment, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.user = User.getInstance();
        this.userInfo = user.getUserInfo();
        this.trans = new Transaction(this);


        final TextView titleText = (TextView) findViewById(R.id.nameText);
        titleText.setText(userInfo.name);

        // todo: check, current fragment?
        final MainFragment mainFrag = new MainFragment();
        trans.replace(R.id.content, mainFrag, false);


        // todo: seek and destroy (check & kill)
        // ImageView controlImg = (ImageView) findViewById(R.id.controlImg);
        // controlImg.setImageResource(R.drawable.control_clr);
    }


    @Override
    public void onBackPressed() {
        boolean empty = !trans.pop();
        if (empty) {
            super.onBackPressed();
        }
    }
}
