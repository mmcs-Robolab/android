package mmcs.robolab.models;
import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

public class User {
    final static public class Auth {
        public Auth(String login, String pass) {
            this.login = login;
            this.pass = pass;
        }

        public String pass;
        public String login;
    }

    private static User ourInstance = new User();

    private User() {}

    public static User getInstance() {
        return ourInstance;
    }


    // database

    protected void saveUser(final Auth user) {
        // todo: save to SQLite
    }
    protected Auth getLast() {
        // todo: save to SQLite
        return null;
    }


    // =================================
    //          public api
    // =================================

    public String getLastLogin() {
        Auth last = getLast();
        return last.login;
    }

    public Response remember() {
        Auth last = getLast();
        return SignIn(last);
    }

    public Response SignIn(final Auth data) {
        if (data == null) {
            Response.getUndefined();
        }

        Response resp = Request
            .create("auth", Request.Method.POST)
            .addParam("login", data.login)
            .addParam("pass", data.pass)
            .execute();

        if (resp.isSuccess()) {
            saveUser(data);
            // todo: gather user data
        }
        return resp;
    }

    public Response LogOut() {
        Response resp = Request
            .create("auth/logout", Request.Method.POST)
            .execute();
        return resp;
    }

    public void getUserInfo() {
        // todo: return cached data
    }



}
