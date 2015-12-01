package mmcs.robolab.utils;

import android.support.annotation.NonNull;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import mmcs.robolab.Robolab;
import mmcs.robolab.activities.AuthActivity;
import mmcs.robolab.models.user.User;
import mmcs.robolab.utils.network.Response;


public class GUIHelper {
    public static void logout(@NonNull final Activity cur) {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... e) {
                User.getInstance().logout();
                return null;
            }
        }.execute();

        Intent intent = new Intent(cur, AuthActivity.class);
        cur.startActivity(intent);
        cur.finish();
    }

    public static void message(@NonNull String msg) {
        Toast toast = Toast.makeText(Robolab.getAppContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    @NonNull
    public static String createMessage(Response resp) {
        switch (resp.code) {
            // todo: move to resources
            case Response.CONNECTION_MISSED: return "Network connection missed";
            case Response.UNKNOWN_ERROR: return "Server doesn't respond";
            case Response.BAD_AUTH: return "Invalid login or pass";
            default: return "Service isn't available";
        }
    }
}
