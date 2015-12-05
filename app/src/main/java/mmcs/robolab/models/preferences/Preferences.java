package mmcs.robolab.models.preferences;

public class Preferences {
    public int autoLog;

    public Preferences(int newAutoLog)  {
        autoLog = newAutoLog;
    }

    public static Preferences getPreferences(String login) {
        Preferences  pref = PrefDB.getPref(login);

        if(pref == null)
            pref = Preferences.getDefault();

        return  pref;
    }

    public void setPreferences(String login, Preferences prefs) {
        PrefDB.setPref(login, prefs);
    }


    public static Preferences getDefault() {

        return new Preferences(0);
    }
}
