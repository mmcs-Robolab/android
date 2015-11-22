package mmcs.robolab.utils.network;

import android.support.annotation.NonNull;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Request {
    protected static DefaultHttpClient client = null;

    protected ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    protected Method method;
    protected String path;

    private void initCookieHandler() {
        if (client == null) {
            client = new DefaultHttpClient();
        }
    }

    // TODO: 11/22/2015 check non null
    @NonNull
    private HttpRequestBase formGet(String url) {
        if(!params.isEmpty() && !url.endsWith("?")) {
            url += "?";
        }
        String paramString = URLEncodedUtils.format(params, "utf-8");
        return new HttpGet(url + paramString);
    }

    // TODO: 11/22/2015 check non null
    @NonNull
    private HttpRequestBase formPost(String url) throws UnsupportedEncodingException {
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params));
        return httppost;
    }



    // ============================
    //      public api
    // ============================

    public enum Method {
        GET, POST
    }

    @NonNull
    public static Request create(String path, Method requestMethod) {
        return new Request(path, requestMethod);
    }

    public Request(String path, Method requestMethod) {
        initCookieHandler();

        this.path = path;
        this.method = requestMethod;
    }

    @NonNull
    public Request addParam(String name, String val) {
        params.add(new BasicNameValuePair(name, val));
        return this;
    }

    // warning: don't run in GUI thread
    @NonNull
    public Response execute() {
        try {
            String url = URL.buildUrl(path);
            HttpRequestBase req = (method == Method.POST)
                    ? formPost(url)
                    : formGet(url);
            HttpResponse response = client.execute(req);

            String result = EntityUtils.toString(response.getEntity());
            Integer respCode = response.getStatusLine().getStatusCode();
            return new Response(result, respCode);

        } catch (IOException e) {
            return Response.getUndefined();
        }
    }

}
