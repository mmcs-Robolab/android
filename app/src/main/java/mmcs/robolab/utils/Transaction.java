package mmcs.robolab.utils;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;

public class Transaction {
    @NonNull Activity activity;
    @NonNull FragmentManager manager;

    public Transaction(@NonNull Activity activity) {
        this.activity = activity;
        this.manager = activity.getFragmentManager();
    }

    @NonNull
    static public Transaction get(@NonNull Activity activity) {
        return new Transaction(activity);
    }

    public void replace(int into, Fragment src, boolean addToBackStack, String tag) {
        final FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(into, src);
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    public void replace(int into, Fragment src, boolean addToBackStack) {
        replace(into, src, addToBackStack, null);
    }

    public boolean pop() {
        final int count = manager.getBackStackEntryCount();
        if (count > 0) {
            manager.popBackStack();
        }
        return count > 0;
    }

}
