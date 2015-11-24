package mmcs.robolab.models.devices;

import org.codehaus.jackson.annotate.JsonProperty;

public class Device {
    @JsonProperty("name")
    public String name;

    @JsonProperty("id")
    public long id = -1;
}
