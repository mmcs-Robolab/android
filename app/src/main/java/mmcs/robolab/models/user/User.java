package mmcs.robolab.models.user;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;


import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

public class User {

    private static User ourInstance = new User();

    private UserInfo userInfo = null;

    private User() {}

    @NonNull
    public static User getInstance() {
        return ourInstance;
    }

    @Nullable @WorkerThread
    private UserInfo loadUserInfo() {
        final Response resp = Request
                .create("auth/userInfo", Request.Method.GET)
                .execute();
        return resp.isSuccess() ? UserInfo.parse(resp.response) : null;
    }


    // =================================
    //          public api
    // =================================

    @Nullable @WorkerThread
    public String getLastLogin() {
        return UserPref.getLogin();
    }

    // try sign in last user
    @NonNull @WorkerThread
    public Response remember() {
        final boolean signed = UserPref.getSigned();
        final long userID = UserPref.getID();

        if (signed) {
            final Auth user = UserDB.getUser(userID);
            if (user != null) {
                return signIn(user);
            }
        }
        return Response.getUndefined();
    }

    @NonNull @WorkerThread
    public Response signIn(@NonNull final Auth data) {
        final Response resp = Request
            .create("auth", Request.Method.POST)
            .addParam("login", data.login)
            .addParam("pass", data.pass)
            .execute();

        if (resp.isSuccess()) {
            this.userInfo = loadUserInfo();
            if (userInfo != null) {
                UserPref.saveUser(userInfo.id, userInfo.login);
                UserDB.saveUser(data);
            }
        }
        return resp;
    }

    @NonNull @WorkerThread
    public Response logout() {
        this.userInfo = null;
        UserPref.logout();
        return Request
            .create("auth/logout", Request.Method.POST)
            .execute();
    }

    @Nullable
    public UserInfo getUserInfo() {
        return this.userInfo;
    }

}
