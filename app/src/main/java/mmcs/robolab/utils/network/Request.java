package mmcs.robolab.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mmcs.robolab.Robolab;

public class Request {
    protected static DefaultHttpClient client = null;

    protected ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    protected Method method;
    protected String path;

    private static void initCookieHandler() {
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
    public static Request create(@NonNull String path, @NonNull Method requestMethod) {
        return new Request(path, requestMethod);
    }

    public Request(@NonNull String path, @NonNull Method requestMethod) {
        initCookieHandler();

        this.path = path;
        this.method = requestMethod;
    }

    @NonNull
    public Request addParam(@NonNull String name, @NonNull String val) {
        params.add(new BasicNameValuePair(name, val));
        return this;
    }

    public static boolean isOnline() {
        Context context = Robolab.getAppContext();
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @NonNull @WorkerThread
    public Response execute() {
        if (isOnline()) {
            try {
                String url = URL.buildUrl(path);
                HttpRequestBase req = (method == Method.POST)
                        ? formPost(url)
                        : formGet(url);
                HttpResponse response = client.execute(req);

                String result = EntityUtils.toString(response.getEntity());
                int respCode = response.getStatusLine().getStatusCode();
                return new Response(result, respCode);

            } catch (IOException e) { /* empty block */}
        }
        return Response.getUndefined();
    }

    public static String getSession() {
        if (client != null) {
            List<Cookie> a = client.getCookieStore().getCookies();
            return a.get(0).getValue();
        }
        return null;
    }

    public static void setSession(String token) {
        initCookieHandler();
        if (client != null) {
            Cookie cookie = new BasicClientCookie("session_cookie_name", token) {
                public BasicClientCookie init() {
                    setPath("/");
                    setDomain("users.mmcs.sfedu.ru");
                    return this;
                }
            }.init();
            client.getCookieStore().addCookie(cookie);
        }
    }

}
