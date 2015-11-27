package mmcs.robolab.models.devices;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import org.codehaus.jackson.annotate.JsonProperty;

import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

public class Device {
    @JsonProperty("id")
    public long id = -1;

    @JsonProperty("name")
    public String name;

    @JsonProperty("type")
    public String type;

    @JsonProperty("state")
    public int state;


    Response getStatistic() {
        // todo: just stub
        final Response resp = Request
            .create("device/stat", Request.Method.GET)
            .addParam("id", String.valueOf(id))
            .execute();
        return resp;
    }

    public void setDeviceState() {
        new AsyncTask<Void, Void, Void>() {
            @Nullable
            protected Void doInBackground(Void... e) {
                Request.create("device/setDeviceState", Request.Method.POST)
                        .addParam("id", String.valueOf(id))
                        .addParam("state", String.valueOf(state))
                        .execute();

                return null;
            }

        }.execute();

    }

    void turnOn() {
        // todo:
    }
}
