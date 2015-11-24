package mmcs.robolab.activities;

import android.support.annotation.NonNull;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mmcs.robolab.R;
import mmcs.robolab.utils.network.Response;
import mmcs.robolab.models.user.User;
import mmcs.robolab.models.user.Auth;


public class AuthActivity extends AppCompatActivity {

    protected User user;
    protected TextView errText;

    protected void processSignIn() {
        errText = (TextView) findViewById(R.id.errText);
        final TextView loginText = (TextView) findViewById(R.id.loginText);
        final TextView passText = (TextView) findViewById(R.id.passText);
        final Button singInBtn = (Button) findViewById(R.id.signInBtn);

        singInBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String login = loginText.getText().toString();
                String pass = passText.getText().toString();
                signIn(login,pass);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        this.user = User.getInstance();
        this.processSignIn();
    }

    protected void signIn(final String login, final String pass) {
        new AsyncTask<Void, Void, Integer>() {

            @NonNull
            protected Integer doInBackground(Void... e) {
                Response auth = user.signIn(new Auth(login, pass));
                return auth.code;
            }

            protected void onPostExecute(Integer result) {
                if(result != 200) {
                    errText.setVisibility(View.VISIBLE);
                } else {
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.execute();
    }
}
