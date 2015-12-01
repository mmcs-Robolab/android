package mmcs.robolab.models.devices;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import org.codehaus.jackson.annotate.JsonProperty;

import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

public class Device {
    public static final int ON = 1;
    public static final int OFF = 0;



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
        return Request
            .create("device/stat", Request.Method.GET)
            .addParam("id", String.valueOf(id))
            .execute();
    }

    @WorkerThread
    public Response setDeviceState() {
        return Request.create("device/setDeviceState", Request.Method.POST)
                .addParam("id", String.valueOf(id))
                .addParam("state", String.valueOf(state))
                .execute();
    }

}
