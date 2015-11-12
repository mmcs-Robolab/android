package mmcs.robolab.utils.network;


public final class URL {
    // todo: gather from resource file
    protected static String urlBase = "http://users.mmcs.sfedu.ru:3002";

    public static String buildUrl(String uri) {
        // todo: check uri
        return urlBase + '/' + uri;
    }

}
