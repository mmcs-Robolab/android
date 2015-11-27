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


public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    protected User user;
    protected TextView errText;
    protected TextView loginText;
    protected TextView passText;
    protected Button signInBtn;

    public void onClick(View v) {
        final String login = loginText.getText().toString();
        final String pass = passText.getText().toString();
        // todo: remove last error msg (or use Toast, see @todo below)
        signIn(new Auth(login, pass));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        errText = (TextView) findViewById(R.id.errText); // todo: kill, use Toast
        loginText = (TextView) findViewById(R.id.loginText);
        passText = (TextView) findViewById(R.id.passText);
        signInBtn = (Button) findViewById(R.id.signInBtn);

        signInBtn.setOnClickListener(this);
        this.user = User.getInstance();
    }

    protected void signIn(@NonNull final Auth authData) {
        new AsyncTask<Void, Void, Response>() {

            @NonNull
            protected Response doInBackground(Void... e) {
                return user.signIn(authData);
            }

            protected void onPostExecute(@NonNull Response result) {
                if(result.isFailure()) {
                    // todo: use Toast, choose msg depend on response code
                    errText.setVisibility(View.VISIBLE);
                } else {
                    final Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.execute();
    }
}
