package mmcs.robolab.utils.network;


import android.support.annotation.NonNull;

public class Response {
    public String response;
    public int code;

    Response(String response, int code) {
        this.response = response;
        this.code = code;
    }

    @NonNull
    public static Response getUndefined() {
        return new Response(null, 0);
    }

    // ====================
    //      helpers
    // ====================

    public boolean isSuccess() {
        return code == 200;
    }
    public boolean isFailure() {
        return code != 200;
    }
    public boolean isUndefined() {
        return code == 0;
    }
}
