package mmcs.robolab.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Console;

import mmcs.robolab.R;
import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;
import mmcs.robolab.models.User;

public class AuthActivity extends AppCompatActivity {

    User user;

    protected void test() {
        AsyncTask task = new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... e) {
                Request query1 = new Request("auth/some", Request.Method.GET);
                Response resp1 = query1.execute();

                Response resp2 = user.SignIn(new User.Auth("sil", "11111"));

                Request query3 = new Request("auth/some", Request.Method.GET);
                Response resp3 = query3.execute();

                Response resp4 = user.LogOut();

                Request query5 = new Request("auth/some", Request.Method.GET);
                Response resp5 = query3.execute();

                return null;
            }

        }.execute();
    }

    TextView errText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        this.user = User.getInstance();

        final TextView loginText = (TextView) findViewById(R.id.loginText);
        final TextView passText = (TextView) findViewById(R.id.passText);
        errText = (TextView) findViewById(R.id.errText);
        Button singInBtn = (Button) findViewById(R.id.signInBtn);

        singInBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String login = loginText.getText().toString();
                String pass = passText.getText().toString();

                signIn(login,pass);
            }
        });
    }


    protected void signIn(final String login, final String pass) {
        new AsyncTask<Void, Void, Integer>() {
            protected Integer doInBackground(Void... e) {
                Response auth = user.SignIn(new User.Auth(login, pass));

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
