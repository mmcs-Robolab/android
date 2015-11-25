package mmcs.robolab.models.devices;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

public class Devices {
    // todo: move to resources
    // todo: fake stub path
    static final protected String path = "auth/some";

    @JsonProperty("devices")
    public List<Device> devices;

    @NonNull
    static protected Response getRaw() {
        return Request
            .create(Devices.path, Request.Method.GET)
            .execute();
    }

    @Nullable
    static public Devices getDevices() {
        final Response resp = Devices.getRaw();
        Devices devices = null;

        if (resp.isSuccess()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                devices = mapper.readValue(resp.response, Devices.class);
            } catch (IOException e) {
                // todo: handle
            }
        }
        return devices;
    }

}

