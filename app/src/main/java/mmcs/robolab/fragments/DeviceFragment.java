package mmcs.robolab.fragments;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import mmcs.robolab.R;
import mmcs.robolab.adapters.DeviceAdapter;
import mmcs.robolab.models.devices.Devices;


public class DeviceFragment extends Fragment {

    @UiThread
    protected void fillDevices() {
        new AsyncTask<Void, Void, Devices>() {
            @Nullable
            protected Devices doInBackground(Void... e) {
                return Devices.getDevices();
            }

            @Override
            protected void onPostExecute(@Nullable Devices devices) {
                super.onPostExecute(devices);

                if (devices != null) {
                    final Activity activity = DeviceFragment.this.getActivity();
                    final ListView listView = (ListView) activity.findViewById(R.id.deviceListView);
                    DeviceAdapter adapter = new DeviceAdapter(activity, devices.devices);
                    listView.setAdapter(adapter);
                } else {
                    // todo: handle this
                }
            }
        }.execute();
    }

    @Override
    public View onCreateView(   LayoutInflater inflater,
                                ViewGroup container,
                                Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_device, container, false);
        fillDevices();
        return v;
    }



}
