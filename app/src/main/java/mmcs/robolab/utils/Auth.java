package mmcs.robolab.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import mmcs.robolab.activities.AuthActivity;
import mmcs.robolab.models.user.User;


public class Auth {
    public static void logout(final Activity cur) {
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
}
