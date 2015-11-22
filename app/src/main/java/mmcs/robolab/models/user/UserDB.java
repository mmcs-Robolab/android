package mmcs.robolab.models.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import mmcs.robolab.utils.DBHelper;

class UserDB {

    static protected void resetLast(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_LAST, 0);
        db.update(DBHelper.TABLE_USER, cv, DBHelper.COL_LAST + " != 0", null);
    }

    static void resetLast() {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        UserDB.resetLast(db);
    }

    static void saveUser(final Auth user) {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        UserDB.resetLast(db);

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_LOGIN, user.login);
        cv.put(DBHelper.COL_PASS, user.pass);
        cv.put(DBHelper.COL_LAST, 1);

        long id = db.insert(DBHelper.TABLE_USER, null, cv);
    }

    @Nullable
    static Auth getLast() {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();

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
}
