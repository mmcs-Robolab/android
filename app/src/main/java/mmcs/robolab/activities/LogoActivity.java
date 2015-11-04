package mmcs.robolab.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import mmcs.robolab.R;

public class LogoActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        final Handler handler = new Handler();
        getWorker(handler).start();
    }

    private Runnable getRespondent() {
        return new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    private Thread getWorker(final Handler handler) {
        return new Thread() {
            @Override
            public void run() {
                doBackgroundWork();
                handler.post(LogoActivity.this.getRespondent());
            }

            private void doBackgroundWork() {
                // todo: load last user
                SystemClock.sleep(2000);
            }
        };
    }
}
