package mmcs.robolab;

import android.app.Application;
import android.content.Context;

public class Robolab extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        Robolab.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Robolab.context;
    }
}
