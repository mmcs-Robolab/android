package mmcs.robolab.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import mmcs.robolab.R;
import mmcs.robolab.utils.GUIHelper;
import mmcs.robolab.utils.network.Response;
import mmcs.robolab.models.user.User;
import mmcs.robolab.models.user.Auth;


public class AuthActivity extends AppCompatActivity implements View.OnClickListener {

    protected User user;
    protected TextView loginText;
    protected TextView passText;
    protected Button signInBtn;
    protected ProgressBar authProgressBar;

    @UiThread
    public void onClick(View v) {
        // todo: move to preExecute
        authProgressBar.setVisibility(View.VISIBLE);
        final String login = loginText.getText().toString();
        final String pass = passText.getText().toString();
        signIn(new Auth(login, pass));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        loginText = (TextView) findViewById(R.id.loginText);
        passText = (TextView) findViewById(R.id.passText);
        signInBtn = (Button) findViewById(R.id.signInBtn);
        authProgressBar = (ProgressBar) findViewById(R.id.authProgressBar);

        new AsyncTask<Void, Void, String>() {
            @Nullable
            protected String doInBackground(Void... e) {
                return user.getLastLogin();
            }
            protected void onPostExecute(@Nullable String result) {
                if (result != null) {
                    loginText.setText(result);
                }
            }
        }.execute();


        signInBtn.setOnClickListener(this);
        this.user = User.getInstance();
    }

    @NonNull
    protected String createMessage(Response resp) {
        switch (resp.code) {
            // todo: move to resources
            case 0: return "Network connection missed";
            case 418: return "Invalid login or pass";
            default: return "Service isn't available";
        }
    }

    @UiThread
    protected void signIn(@NonNull final Auth authData) {
        new AsyncTask<Void, Void, Response>() {

            @NonNull
            protected Response doInBackground(Void... e) {
                return user.signIn(authData);
            }

            protected void onPostExecute(@NonNull Response result) {
                if(result.isFailure()) {
                    authProgressBar.setVisibility(View.INVISIBLE);
                    GUIHelper.message(createMessage(result));
                } else {
                    final Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.execute();
    }
}
