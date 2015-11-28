package mmcs.robolab;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import android.content.res.Resources;
import android.content.SharedPreferences;
import android.app.Application;
import android.content.Context;

public class Robolab extends Application {

    // todo: move to resources
    @NonNull public static final String APP_PREFERENCES = "settings";
    private static Context context;
    private static SharedPreferences preference;
    private static Resources resources;

    @CallSuper
    public void onCreate() {
        super.onCreate();
        Robolab.context = getApplicationContext();
        Robolab.preference = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        Robolab.resources = context.getResources();
    }

    @NonNull
    public static Context getAppContext() {
        return context;
    }

    @NonNull
    public static SharedPreferences getPreference() {
        return preference;
    }

    @NonNull
    public static Resources getResManager() {
        return resources;
    }

    @Nullable
    public static String getStringRes(@StringRes int stringID) {
        return resources.getString(stringID);
    }
}
