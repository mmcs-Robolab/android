package mmcs.robolab.adapters;

import android.support.annotation.NonNull;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import mmcs.robolab.R;
import mmcs.robolab.models.devices.Device;

public class DeviceAdapter extends ArrayAdapter<Device> {
    @NonNull
    private final Activity activity;
    @NonNull
    private final List<Device> list;

    public DeviceAdapter(@NonNull Activity activity, @NonNull List<Device> list) {
        super(activity, R.layout.device_item, list);
        this.activity = activity;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View rowView = convertView;
        final ViewHolder view;

        if(rowView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.device_item, parent, false);

            view = new ViewHolder();
            view.name = (TextView) rowView.findViewById(R.id.nameTextView);
            view.img = (ImageView) rowView.findViewById(R.id.deviceImg);
            view.switchState = (Switch) rowView.findViewById(R.id.switchState);

            final Device item = list.get(position);
            view.name.setText(item.name.toUpperCase());

            if(item.type.equals("sensor")) {
                view.img.setImageResource(R.drawable.sensor_white);
                view.switchState.setClickable(false); // запретить нажимать как-нибудь
            }

            if(item.state == 1) {
                view.switchState.setChecked(true);
            } else {
                view.switchState.setChecked(false);
            }

            View.OnClickListener switchClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(view.switchState.isChecked()) {
                        item.state = 1;
                        item.setDeviceState();
                    } else {
                        item.state = 0;
                        item.setDeviceState();
                    }
                }
            };

            view.switchState.setOnClickListener(switchClick);

            rowView.setTag(view);
        }
        return rowView;
    }





    protected static class ViewHolder {
        protected TextView name;
        protected ImageView img;
        protected Switch switchState;
    }
}
