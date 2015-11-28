package mmcs.robolab.models.user;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class UserInfo {
    public @JsonProperty("userId") long id;
    public @JsonProperty("login") String login;
    public @JsonProperty("username") String name;
    public @JsonProperty("email") String email;

    @Nullable
    public static UserInfo parse(@NonNull String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // todo: check fields
            return mapper.readValue(json, UserInfo.class);
        } catch (IOException e) {
            return null;
            // todo: handle
        }
    }
}
