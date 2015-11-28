package mmcs.robolab.models.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.content.SharedPreferences;

import mmcs.robolab.Robolab;
import mmcs.robolab.utils.network.Request;

class UserPref {
    @NonNull static final String LAST_LOGIN = "userLogin";
    @NonNull static final String SESSION_ID = "sessionID";

    @WorkerThread
    public static void logout() {
        SharedPreferences sPref = Robolab.getPreference();
        SharedPreferences.Editor editor = sPref.edit();
        editor.remove(SESSION_ID);
        editor.apply();
    }

    @WorkerThread
    public static void saveUser(final @NonNull String login) {
        SharedPreferences sPref = Robolab.getPreference();
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(LAST_LOGIN, login);
        editor.apply();
    }

    @Nullable @WorkerThread
    public static String getLogin() {
        SharedPreferences sPref = Robolab.getPreference();
        return sPref.getString(LAST_LOGIN, null);
    }

    @Nullable
    public static String getSession() {
        SharedPreferences sPref = Robolab.getPreference();
        return sPref.getString(SESSION_ID, null);
    }

    public static void setSession(@NonNull final String session) {
        SharedPreferences sPref = Robolab.getPreference();
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(SESSION_ID, session);
        editor.apply();
    }

}
