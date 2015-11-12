package mmcs.robolab.utils.network;

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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Query {
    protected static DefaultHttpClient client = null;

    protected ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    protected Method method;
    protected String path;


    private void initCookieHandler() {
        if (client == null) {
            client = new DefaultHttpClient();
        }
    }

    private HttpRequestBase formGet(String url) {
        if(!params.isEmpty() && !url.endsWith("?")) {
            url += "?";
        }
        String paramString = URLEncodedUtils.format(params, "utf-8");
        return new HttpGet(url + paramString);
    }

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

    public Query(String path, Method requestMethod) {
        initCookieHandler();

        this.path = path;
        this.method = requestMethod;
    }

    public Query addParam(String name, String val) {
        params.add(new BasicNameValuePair(name, val));
        return this;
    }


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

        } catch (Exception e) {
            return Response.getUndefined();
        }
    }

}
