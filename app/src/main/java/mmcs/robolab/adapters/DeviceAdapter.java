package mmcs.robolab.adapters;

import android.os.AsyncTask;
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
import mmcs.robolab.utils.GUIHelper;
import mmcs.robolab.utils.network.Response;

public class DeviceAdapter extends ArrayAdapter<Device> {
    @NonNull
    private final Activity activity;
    @NonNull
    private final List<Device> list;

    static class CustomSwitchListener extends AsyncTask<Void, Void, Response> implements View.OnClickListener {
        ViewHolder view;
        Device item;
        boolean lastState;

        public CustomSwitchListener(ViewHolder view, Device item) {
            this.view = view;
            this.item = item;
        }

        @Override public void onClick(View v) {
            lastState = view.switchState.isChecked();
            item.state = (lastState) ? Device.ON : Device.OFF;
            execute();
        }

        @NonNull protected Response doInBackground(Void... e) {
            return item.setDeviceState();
        }

        protected void onPostExecute(@NonNull Response response) {
            if (response.isFailure()) {
                view.switchState.setChecked(!lastState);
                GUIHelper.message(GUIHelper.createMessage(response));
            }
        }
    }

    public DeviceAdapter(@NonNull Activity activity, @NonNull List<Device> list) {
        super(activity, R.layout.device_item, list);
        this.activity = activity;
        this.list = list;
    }

    protected void initItem(int position, final ViewHolder view) {
        final Device item = list.get(position);
        view.name.setText(item.name.toUpperCase());
        view.switchState.setChecked(item.state == Device.ON);

        if(item.type.equals("sensor")) {
            view.img.setImageResource(R.drawable.sensor_white);
            view.switchState.setClickable(false); // запретить нажимать как-нибудь
        }
        view.switchState.setOnClickListener(new CustomSwitchListener(view, item));
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

            initItem(position, view);
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
