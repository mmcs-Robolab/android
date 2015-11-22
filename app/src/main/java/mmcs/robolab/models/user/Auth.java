package mmcs.robolab.models.user;

final public class Auth {
    public Auth(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public Auth() {}

    public String pass;
    public String login;
}
