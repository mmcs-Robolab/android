package mmcs.robolab.models.devices;

import org.codehaus.jackson.annotate.JsonProperty;

import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

public class Device {
    @JsonProperty("name")
    public String name;

    @JsonProperty("id")
    public long id = -1;


    Response getStatistic() {
        // todo: just stub
        final Response resp = Request
            .create("device/stat", Request.Method.GET)
            .addParam("id", String.valueOf(id))
            .execute();
        return resp;
    }

    void turnOn() {
        // todo:
    }
}
