package mmcs.robolab.adapters;

import android.app.Fragment;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import mmcs.robolab.R;
import mmcs.robolab.activities.MainActivity;
import mmcs.robolab.fragments.RobotsFragment;
import mmcs.robolab.models.devices.Device;
import mmcs.robolab.models.robots.Robot;
import mmcs.robolab.utils.GUIHelper;
import mmcs.robolab.utils.network.Response;

public class RobotsAdapter extends ArrayAdapter<Robot> {
    @NonNull
    private final MainActivity activity;
    @NonNull
    private final List<Robot> list;

    public RobotsAdapter(@NonNull Activity activity, @NonNull List<Robot> list) {
        super(activity, R.layout.robot_item, list);
        this.activity = (MainActivity) activity;
        this.list = list;
    }


    protected void initItem(int position, final ViewHolder view) {
        final Robot item = list.get(position);
        view.name.setText(item.name.toUpperCase());

        view.robotLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onRobotItemClick(item.id, item.name, item.x, item.y);
            }
        });

    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View rowView = convertView;
        final ViewHolder view;

        if(rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.robot_item, parent, false);

            view = new ViewHolder();
            view.name = (TextView) rowView.findViewById(R.id.nameTextView);
            view.robotLayout = (RelativeLayout) rowView.findViewById(R.id.robotLayout);

            initItem(position, view);
            rowView.setTag(view);
        }
        return rowView;
    }

    protected static class ViewHolder {
        protected TextView name;
        protected RelativeLayout robotLayout;
    }

}
