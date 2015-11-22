package mmcs.robolab.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import mmcs.robolab.R;
import mmcs.robolab.fragments.MainFragment;
import mmcs.robolab.models.user.User;

public class MainActivity extends AppCompatActivity {

    private MainFragment mainFrag;

    public void onExitClick(View view) {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... e) {
                User.getInstance().logout();
                return null;
            }

            protected void onPostExecute(Void v) {
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        }.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFrag = new MainFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, mainFrag);
        transaction.commit();

        ImageView controlImg = (ImageView) findViewById(R.id.controlImg);

        controlImg.setImageResource(R.drawable.control_clr);  //"@drawable/control_clr");
    }
}
