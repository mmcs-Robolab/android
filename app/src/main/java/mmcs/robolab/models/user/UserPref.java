package mmcs.robolab.models.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.SharedPreferences;

import mmcs.robolab.Robolab;

class UserPref {
    @NonNull static final String LAST_LOGIN = "userLogin";
    @NonNull static final String LAST_ID = "userID";
    @NonNull static final String IS_SIGNED = "isSigned";

    public static void logout() {
        SharedPreferences sPref = Robolab.getPreference();
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(IS_SIGNED, false);
        editor.apply();
    }

    public static void saveUser(long id, @NonNull String login) {
        SharedPreferences sPref = Robolab.getPreference();
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(IS_SIGNED, true);
        editor.putString(LAST_LOGIN, login);
        editor.putLong(LAST_ID, id);
        editor.apply();
    }

    @Nullable
    public static String getLogin() {
        SharedPreferences sPref = Robolab.getPreference();
        return sPref.getString(LAST_LOGIN, null);
    }

    public static long getID() {
        SharedPreferences sPref = Robolab.getPreference();
        return sPref.getLong(LAST_ID, -1);
    }

    public static boolean getSigned() {
        SharedPreferences sPref = Robolab.getPreference();
        return sPref.getBoolean(IS_SIGNED, false);
    }

}
