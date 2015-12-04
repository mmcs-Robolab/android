package mmcs.robolab.models.robots;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

import mmcs.robolab.models.devices.Device;
import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

/**
 * Created by pimka on 04.12.15.
 */
public class Robots {
    static final protected String path = "device/connectedRobots";

    @JsonProperty("robots")
    public List<Robot> robots;

    @NonNull
    @WorkerThread
    static protected Response getRaw() {
        return Request
                .create(Robots.path, Request.Method.GET)
                .execute();
    }

    @Nullable
    static protected Robots parse(@NonNull String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Robots.class);
    }

    @Nullable @WorkerThread
    static public Robots getRobots() {
        final Response resp = Robots.getRaw();
        Robots robots = null;

        if (resp.isSuccess()) {
            try {
                robots = parse(resp.response);
            } catch (IOException e) {
                // todo: handle
            }
        }
        return robots;
    }
}
