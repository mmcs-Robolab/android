package mmcs.robolab.models.user;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import mmcs.robolab.models.preferences.Preferences;
import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

public class User {

    private static User ourInstance = new User();

    private UserInfo userInfo = null;

    private User() {}

    public Preferences prefs;

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
        String cookie = UserPref.getSession();
        if (cookie != null) {
            Request.setSession(cookie);
            this.userInfo = loadUserInfo();
            if (this.userInfo != null) {
                return new Response(null, 200);
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
                final String cookie = Request.getSession();
                UserPref.saveUser(userInfo.login);
                if (cookie != null) {
                    UserPref.setSession(cookie);
                }
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

//    public Preferences getPrefs() {
//        return prefs.getPreferences(this.userInfo.id);
//    }
//
//    public void setPrefs(Preferences newPrefs) {
//        prefs = newPrefs;
//        prefs.setPreferences(this.userInfo.id, prefs);
//    }

    public Preferences lastLoginPrefs() {

        String login = this.getLastLogin();

        prefs = (login != null) ? Preferences.getPreferences(login)
                : Preferences.getDefault();

        return prefs;
    }

}
