package mmcs.robolab.utils.network;


import android.support.annotation.NonNull;

public class Response {
    public static final int CONNECTION_MISSED = -1;
    public static final int UNKNOWN_ERROR = 0;
    public static final int OK = 200;
    public static final int BAD_AUTH = 418;
    public static final int INTERNAL_SERVER_ERROR = 500;




    public String response;
    public int code;

    public Response(String response, int code) {
        this.response = response;
        this.code = code;
    }

    @NonNull
    public static Response getUndefined() {
        return new Response(null, UNKNOWN_ERROR);
    }

    @NonNull
    public static Response getConnMissed() {
        return new Response(null, CONNECTION_MISSED);
    }

    // ====================
    //      helpers
    // ====================

    public boolean isSuccess() {
        return code == OK;
    }
    public boolean isFailure() {
        return code != OK;
    }
}
