package mmcs.robolab.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import mmcs.robolab.R;
import mmcs.robolab.adapters.RobotsAdapter;
import mmcs.robolab.models.robots.Robots;

public class RobotsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeLayout;

    @UiThread
    void fillRobotsLayout(@Nullable Robots robots) {
        if (robots != null) {
            final Activity activity = RobotsFragment.this.getActivity();
            final ListView listView = (ListView) activity.findViewById(R.id.robotsListView);
            RobotsAdapter adapter = new RobotsAdapter(activity,robots.robots);
            listView.setAdapter(adapter);
        } else {
            // todo: handle this
        }
    }


    @UiThread
    protected void fillRobots() {
        new AsyncTask<Void, Void, Robots>() {
            @Nullable
            protected Robots doInBackground(Void... e) {
                return Robots.getRobots();
            }

            @Override
            protected void onPostExecute(@Nullable Robots robots) {
                super.onPostExecute(robots);
                fillRobotsLayout(robots);
                swipeLayout.setRefreshing(false);
            }
        }.execute();
    }

    @Override
    public View onCreateView(   LayoutInflater inflater,
                                ViewGroup container,
                                Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_robots, container, false);
        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container_robots);
        swipeLayout.setOnRefreshListener(this);

        fillRobots();
        return v;
    }

    public static void robotItemClick()
    {

    }

    @Override
    public void onRefresh() {
        fillRobots();
    }

}
