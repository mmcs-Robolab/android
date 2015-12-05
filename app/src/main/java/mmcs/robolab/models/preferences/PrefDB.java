package mmcs.robolab.models.preferences;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mmcs.robolab.utils.DBHelper;

public class PrefDB {

    static Preferences getPref(String login) {

        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();

        final Cursor cur = db.query(
                DBHelper.TABLE_USER, new String[]{DBHelper.COL_AUTO_LOGIN},
                DBHelper.COL_LOGIN + "= ?", new String[]{login},
                null, null, null
        );

        Preferences res = null;

        if (cur.moveToFirst()) {
            final int idAutoLogin = cur.getColumnIndex(DBHelper.COL_AUTO_LOGIN);

            int autoLogin = cur.getInt(idAutoLogin);

            res = new Preferences(autoLogin);
        }

        return res;
    }

    static void setPref(String login, Preferences prefs) {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();

        final ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_AUTO_LOGIN, prefs.autoLog);
        cv.put(DBHelper.COL_LOGIN, login);


        int res = db.update(DBHelper.TABLE_USER, cv, DBHelper.COL_LOGIN + "='" + login + "'", null);
        if(res == 0)
            db.insert(DBHelper.TABLE_USER, null, cv);
        //db.insertWithOnConflict(DBHelper.TABLE_USER, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }
}
