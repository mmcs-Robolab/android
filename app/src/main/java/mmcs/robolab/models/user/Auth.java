package mmcs.robolab.models.user;

import android.support.annotation.NonNull;

final public class Auth {
    public Auth(@NonNull final String login, @NonNull final String pass) {
        this.login = login;
        this.pass = pass;
    }

    @NonNull
    public String pass;

    @NonNull
    public String login;
}
