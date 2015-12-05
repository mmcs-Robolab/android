package mmcs.robolab.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mmcs.robolab.R;
import mmcs.robolab.activities.MainActivity;
import mmcs.robolab.models.devices.Devices;
import mmcs.robolab.models.robots.Robot;
import mmcs.robolab.models.robots.Robots;
import mmcs.robolab.utils.GUIHelper;
import mmcs.robolab.utils.network.Response;


public class RobotControlFragment extends Fragment implements View.OnClickListener {

    private long id;
    private int x;
    private int y;

    TextView robotName;
    TextView coordinates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_robot_control, container, false);
        robotName = (TextView) v.findViewById(R.id.robotNameView);
        coordinates = (TextView) v.findViewById(R.id.coordinatesView);

        Bundle args = this.getArguments();
        id = args.getLong("id", -1);
        x = args.getInt("x", 0);
        y = args.getInt("y", 0);
        String name = args.getString("name");
        robotName.setText(name);
        coordinates.setText("X: " + x + " Y: " + y);

        ImageButton upBtn = (ImageButton) v.findViewById(R.id.upBtn);
        ImageButton downBtn = (ImageButton) v.findViewById(R.id.downBtn);
        ImageButton leftBtn = (ImageButton) v.findViewById(R.id.leftBtn);
        ImageButton rightBtn = (ImageButton) v.findViewById(R.id.rightBtn);

        upBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);

        return  v;
    }

    @Override @UiThread
    public void onClick(View view) {
        // todo: check <|> kill that
        view.setBackgroundColor(Color.parseColor("#cccccc"));

        switch (view.getId()) {
            case R.id.upBtn:
                changeY(5);
                break;
            case R.id.downBtn:
                changeY(-5);
                break;
            case R.id.leftBtn:
                changeX(-5);
                break;
            case R.id.rightBtn:
                changeX(5);
                break;
        }
    }

    public void changeX(final int state) {
        new AsyncTask<Void, Void, Response>() {
            @NonNull
            protected Response doInBackground(Void... e) {
                return Robot.setX(id, x + state);
            }
            protected void onPostExecute(@NonNull Response response) {
                if (response.isFailure()) {
                    GUIHelper.message(GUIHelper.createMessage(response));
                } else {
                    x += state;
                    coordinates.setText("X: " + x + " Y: " + y);
                }
            }
        }.execute();
    }

    public void changeY(final int state) {
        new AsyncTask<Void, Void, Response>() {
            @NonNull
            protected Response doInBackground(Void... e) {
                return Robot.setY(id, y + state);
            }
            protected void onPostExecute(@NonNull Response response) {
                if (response.isFailure()) {
                    GUIHelper.message(GUIHelper.createMessage(response));
                } else {
                    y += state;
                    coordinates.setText("X: " + x + " Y: " + y);
                }
            }
        }.execute();
    }
}
