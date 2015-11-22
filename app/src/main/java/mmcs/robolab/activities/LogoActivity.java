package mmcs.robolab.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mmcs.robolab.R;
import mmcs.robolab.models.user.User;
import mmcs.robolab.utils.network.Response;

public class LogoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        final Handler handler = new Handler();
        getWorker(handler).start();
    }

    private Runnable getRespondent(final Response resp) {
        return new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(
                    LogoActivity.this,
                    resp.isSuccess() ? MainActivity.class : AuthActivity.class
                );
                startActivity(intent);
                finish();
            }
        };
    }

    private Thread getWorker(final Handler handler) {
        return new Thread() {
            @Override
            public void run() {
                Response resp = doBackgroundWork();
                handler.post(LogoActivity.this.getRespondent(resp));
            }

            private Response doBackgroundWork() {
                return User.getInstance().remember();
            }
        };
    }
}
