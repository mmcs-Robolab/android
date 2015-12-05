package mmcs.robolab.activities;

import android.content.IntentFilter;
import android.support.annotation.UiThread;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mmcs.robolab.R;
import mmcs.robolab.fragments.DeviceFragment;
import mmcs.robolab.fragments.MainFragment;
import mmcs.robolab.fragments.PropertiesFragment;
import mmcs.robolab.fragments.RobotControlFragment;
import mmcs.robolab.fragments.RobotsFragment;
import mmcs.robolab.models.user.User;
import mmcs.robolab.models.user.UserInfo;
import mmcs.robolab.recievers.NetworkReceiver;
import mmcs.robolab.utils.GUIHelper;
import mmcs.robolab.utils.Transaction;

public class MainActivity extends AppCompatActivity {

    private Transaction trans;
    private User user;
    private UserInfo userInfo;
    private NetworkReceiver receiver = new NetworkReceiver();

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(receiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private ImageView curMenuImg;


    public void onExitClick() {
        GUIHelper.logout(this);
    }

    @UiThread
    public void onDevicesClick() {
        curMenuImg.setImageResource(R.drawable.control_white);
        final Fragment fragment = new DeviceFragment();
        this.trans.replace(R.id.content, fragment, true);
    }

    public void onRobotsClick() {
        curMenuImg.setImageResource(R.drawable.control_white);
        final Fragment fragment = new RobotsFragment();
        this.trans.replace(R.id.content, fragment, true);
    }

    public void onRobotItemClick(long id, String name, int x, int y) {
        curMenuImg.setImageResource(R.drawable.control_white);

        Bundle args = new Bundle();
        args.putLong("id", id);
        args.putString("name", name);
        args.putInt("x", x);
        args.putInt("y", y);

        final Fragment fragment = new RobotControlFragment();

        fragment.setArguments(args);
        this.trans.replace(R.id.content, fragment, true);
    }


    public void onControlImgClick(View v) {
        final Fragment fragment = new MainFragment();
        this.trans.replace(R.id.content, fragment, true);

        ImageView controlImg = (ImageView) findViewById(R.id.controlImg);
        ImageView profileImg = (ImageView) findViewById(R.id.profileImg);
        profileImg.setImageResource(R.drawable.profile_white);
        curMenuImg = controlImg;
        curMenuImg.setImageResource(R.drawable.control_active);
    }

    public void onProfileImgClick(View v) {
        final Fragment fragment = new PropertiesFragment();
        this.trans.replace(R.id.content, fragment, true);

        ImageView profileImg = (ImageView) findViewById(R.id.profileImg);
        ImageView controlImg = (ImageView) findViewById(R.id.controlImg);
        controlImg.setImageResource(R.drawable.control_white);
        curMenuImg = profileImg;
        curMenuImg.setImageResource(R.drawable.profile_clr);
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
        ImageView controlImg = (ImageView) findViewById(R.id.controlImg);
        controlImg.setImageResource(R.drawable.control_active);

        curMenuImg = controlImg;
    }


    @Override @UiThread
    public void onBackPressed() {
        boolean empty = !trans.pop();
        if (empty) {
            super.onBackPressed();
        }
    }
}
