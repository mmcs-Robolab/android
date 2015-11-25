package mmcs.robolab.models.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import mmcs.robolab.utils.DBHelper;

class UserDB {

    static protected void resetLast(@NonNull SQLiteDatabase db) {
        final ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_LAST, 0);
        db.update(DBHelper.TABLE_USER, cv, DBHelper.COL_LAST + " != 0", null);
    }

    static void resetLast() {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        UserDB.resetLast(db);
    }

    static void saveUser(@NonNull final Auth user) {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        UserDB.resetLast(db);

        final ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_LOGIN, user.login);
        cv.put(DBHelper.COL_PASS, user.pass);
        cv.put(DBHelper.COL_LAST, 1);

        final long id = db.insert(DBHelper.TABLE_USER, null, cv);
        // todo: delete repetitions
    }

    @Nullable
    static Auth getLast() {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();

        final String selection = DBHelper.COL_LAST + " != 0";
        final Cursor cur = db.query(DBHelper.TABLE_USER, null, selection, null, null, null, null);

        Auth res = null;
        if (cur.moveToFirst()) {
            int idLogin = cur.getColumnIndex(DBHelper.COL_LOGIN);
            int idPass = cur.getColumnIndex(DBHelper.COL_PASS);

            String login = cur.getString(idLogin);
            String pass = cur.getString(idPass);
            res = new Auth(login, pass);
        }

        cur.close();
        return res;
    }
}
