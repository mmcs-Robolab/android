package mmcs.robolab.models.robots;

import android.support.annotation.WorkerThread;

import org.codehaus.jackson.annotate.JsonProperty;

import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;


public class Robot {

    @JsonProperty("id")
    public long id = -1;

    @JsonProperty("name")
    public String name;

    @JsonProperty("x")
    public int x;

    @JsonProperty("y")
    public int y;


    @WorkerThread
    public static Response setX(long id, int state) {
        return Request.create("device/setX", Request.Method.POST)
                .addParam("id", String.valueOf(id))
                .addParam("state", String.valueOf(state))
                .execute();
    }

    public static Response setY(long id, int state) {
        return Request.create("device/setY", Request.Method.POST)
                .addParam("id", String.valueOf(id))
                .addParam("state", String.valueOf(state))
                .execute();
    }
}
