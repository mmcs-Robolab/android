package mmcs.robolab.adapters;

import android.support.annotation.NonNull;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
            view.id = (TextView) rowView.findViewById(R.id.idTextView);
            view.name = (TextView) rowView.findViewById(R.id.nameTextView);

            rowView.setTag(view);
        } else {
            view = (ViewHolder) rowView.getTag();
        }

        final Device item = list.get(position);
        view.id.setText(String.valueOf(item.id));
        view.name.setText(item.name);

        return rowView;
    }

    protected static class ViewHolder {
        protected TextView id;
        protected TextView name;
    }
}
