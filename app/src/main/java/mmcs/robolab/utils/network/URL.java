package mmcs.robolab.utils.network;


import android.support.annotation.NonNull;

public final class URL {
    // todo: gather from resource file
    @NonNull
    protected static String urlBase = "http://users.mmcs.sfedu.ru:3002";

    @NonNull
    public static String buildUrl(@NonNull String uri) {
        // todo: check uri
        return urlBase + '/' + uri;
    }

}
