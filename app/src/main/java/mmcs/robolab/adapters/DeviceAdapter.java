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

        view.switchState.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final boolean state = view.switchState.isChecked();
                item.state = (state) ? Device.ON : Device.OFF;

                new AsyncTask<Void, Void, Response>() {
                    @NonNull
                    protected Response doInBackground(Void... e) {
                        return item.setDeviceState();
                    }
                    protected void onPostExecute(@NonNull Response response) {
                        if (response.isFailure()) {
                            view.switchState.setChecked(!state);
                            GUIHelper.message(GUIHelper.createMessage(response));
                        }
                    }
                }.execute();
            }
        });

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
