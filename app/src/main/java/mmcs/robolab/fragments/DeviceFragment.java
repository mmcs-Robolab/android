package mmcs.robolab.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import mmcs.robolab.R;
import mmcs.robolab.adapters.DeviceAdapter;
import mmcs.robolab.models.devices.Device;
import mmcs.robolab.models.devices.Devices;


public class DeviceFragment extends Fragment {


    protected void fillDevices() {
        new AsyncTask<Void, Void, Devices>() {
            protected Devices doInBackground(Void... e) {
                return Devices.getDevices();
            }

            @Override
            protected void onPostExecute(Devices devices) {
                super.onPostExecute(devices);
                ListView listView = (ListView) DeviceFragment.this.
                        getActivity(). findViewById(R.id.deviceListView);

                DeviceAdapter adapter = new DeviceAdapter(
                        DeviceFragment.this.getActivity(),
                        devices.devices
                );
//                ArrayAdapter<Device> adapter = new ArrayAdapter<>(
//                        DeviceFragment.this.getActivity(),
//                        android.R.layout.simple_list_item_1,
//                        devices.devices
//                );

                listView.setAdapter(adapter);
            }
        }.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_device, container, false);
        fillDevices();
        return v;
    }



}
