package mmcs.robolab.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import mmcs.robolab.models.devices.Device;

public class DeviceAdapter extends ArrayAdapter {
    private final Activity activity;
    private final List<Device> list;

    public DeviceAdapter(Activity activity, List<Device> list) {
        super(activity, android.R.layout.simple_list_item_1, list);
        this.activity = activity;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder view;

        if(rowView == null)
        {
            // Get a new instance of the row layout view
            LayoutInflater inflater = activity.getLayoutInflater();
            rowView = inflater.inflate(android.R.layout.simple_list_item_1, null);

            // Hold the view objects in an object, that way the don't need to be "re-  finded"
            view = new ViewHolder();
            view.id = (TextView) rowView.findViewById(android.R.id.text1);
            view.name = (TextView) rowView.findViewById(android.R.id.text1);

            rowView.setTag(view);
        } else {
            view = (ViewHolder) rowView.getTag();
        }

        /** Set data to your Views. */
        Device item = list.get(position);
        view.id.setText(String.valueOf(item.id));
        view.name.setText(item.name);

        return rowView;
    }

    protected static class ViewHolder{
        protected TextView id;
        protected TextView name;
    }
}
