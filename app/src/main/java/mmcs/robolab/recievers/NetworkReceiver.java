package mmcs.robolab.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import mmcs.robolab.utils.network.Request;

public class NetworkReceiver extends BroadcastReceiver {
    int counter = 0;
    public NetworkReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // todo: replace stub!
        if (counter > 0) {
            boolean online = Request.isOnline();
            // todo: move to resources
            Toast.makeText(context, "Интернет соединение " + (online ? "восстановлено" : "разорвано"),
                    Toast.LENGTH_LONG).show();
        }
        ++counter;
    }
}
