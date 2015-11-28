package mmcs.robolab.activities;

import android.support.annotation.NonNull;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mmcs.robolab.R;
import mmcs.robolab.models.user.User;
import mmcs.robolab.utils.network.Response;

public class LogoActivity extends AppCompatActivity implements Runnable {

    @NonNull
    Response resp = Response.getUndefined();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        final Handler handler = new Handler();
        new Thread() {
            @Override
            public void run() {
                LogoActivity.this.resp = User.getInstance().remember();
                handler.post(LogoActivity.this);
            }
        }.start();
    }

    @Override @UiThread
    public void run() {
        final Class<?> activity = resp.isSuccess() ? MainActivity.class : AuthActivity.class;
        startActivity(new Intent(this, activity));
        finish();
    }
}
