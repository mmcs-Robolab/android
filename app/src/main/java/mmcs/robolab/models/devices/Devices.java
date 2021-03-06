package mmcs.robolab.models.devices;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

public class Devices {
    // todo: move to resources
    // todo: fake stub path
    static final protected String path = "device/list";

    @JsonProperty("devices")
    public List<Device> devices;

    @NonNull @WorkerThread
    static protected Response getRaw() {
        return Request
            .create(Devices.path, Request.Method.GET)
            .execute();
    }

    @Nullable
    static protected Devices parse(@NonNull String json) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Devices.class);
    }

    @Nullable @WorkerThread
    static public Devices getDevices() {
        final Response resp = Devices.getRaw();
        Devices devices = null;

        if (resp.isSuccess()) {
            try {
                devices = parse(resp.response);
            } catch (IOException e) {
                // todo: handle
            }
        }
        return devices;
    }

}

