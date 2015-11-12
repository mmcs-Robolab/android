package mmcs.robolab.utils.network;



public class Response {
    public String response;
    public Integer code;

    Response(String response, Integer code) {
        this.response = response;
        this.code = code;
    }

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
