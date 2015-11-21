package mmcs.robolab.models;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mmcs.robolab.utils.DBHelper;
import mmcs.robolab.utils.network.Request;
import mmcs.robolab.utils.network.Response;

public class User {
    final static public class Auth {
        public Auth(String login, String pass) {
            this.login = login;
            this.pass = pass;
        }

        public Auth() {}

        public String pass;
        public String login;
    }

    private static User ourInstance = new User();

    private User() {}

    public static User getInstance() {
        return ourInstance;
    }


    // database

    private void resetLast(long id) {
        DBHelper dbHelper = DBHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.COL_LAST, 0);
        db.update(
            DBHelper.TABLE_USER, cv,
            DBHelper.COL_LAST + " != 0 and " + DBHelper.COL_ID  + " != ?",
            new String[]{Long.toString(id)}
        );
    }

    private void saveUser(final Auth user) {
        DBHelper dbHelper = DBHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_LOGIN, user.login);
        cv.put(DBHelper.COL_PASS, user.pass);
        cv.put(DBHelper.COL_LAST, 1);
        long id = db.insert(DBHelper.TABLE_USER, null, cv);

        resetLast(id);
    }
    protected Auth getLast() {

        DBHelper dbHelper = DBHelper.getInstance();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = DBHelper.COL_LAST + " != 0";
        Cursor cur = db.query(DBHelper.TABLE_USER, null, selection, null, null, null, null);

        Auth res = null;
        if (cur.moveToFirst()) {
            int idLogin = cur.getColumnIndex(DBHelper.COL_LOGIN);
            int idPass = cur.getColumnIndex(DBHelper.COL_PASS);

            res = new Auth();
            res.login = cur.getString(idLogin);
            res.pass = cur.getString(idPass);
        }
        cur.close();
        return res;
    }


    // =================================
    //          public api
    // =================================

    public String getLastLogin() {
        Auth last = getLast();
        return last.login;
    }

    public Response remember() {
        Auth last = getLast();
        return SignIn(last);
    }

    public Response SignIn(final Auth data) {
        if (data == null) {
            Response.getUndefined();
        }

        Response resp = Request
            .create("auth", Request.Method.POST)
            .addParam("login", data.login)
            .addParam("pass", data.pass)
            .execute();

        if (resp.isSuccess()) {
            saveUser(data);
            // todo: gather user data
        }
        return resp;
    }

    public Response LogOut() {
        Response resp = Request
            .create("auth/logout", Request.Method.POST)
            .execute();
        return resp;
    }

    public void getUserInfo() {
        // todo: return cached data
    }



}
