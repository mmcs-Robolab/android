package mmcs.robolab.models.user;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

public class User {

    private static User ourInstance = new User();

    private User() {}

    @NonNull
    public static User getInstance() {
        return ourInstance;
    }


    // =================================
    //          public api
    // =================================

    @Nullable
    public String getLastLogin() {
        Auth last = UserDB.getLast();
        return (last != null) ? last.login : null;
    }

    @NonNull
    public Response remember() {
        Auth last = UserDB.getLast();
        return SignIn(last);
    }

    @NonNull
    public Response SignIn(final Auth data) {
        if (data == null) {
            return Response.getUndefined();
        }

        Response resp = Request
            .create("auth", Request.Method.POST)
            .addParam("login", data.login)
            .addParam("pass", data.pass)
            .execute();

        if (resp.isSuccess()) {
            UserDB.saveUser(data);
            // todo: gather user data
        }
        return resp;
    }

    @NonNull
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
