package mmcs.robolab.models.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import mmcs.robolab.utils.DBHelper;

class UserDB {

    @WorkerThread
    static void saveUser(@NonNull final Auth user) {
        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();

        final ContentValues cv = new ContentValues();
        cv.put(DBHelper.COL_LOGIN, user.login);
        cv.put(DBHelper.COL_PASS, user.pass);

        final long id = db.insert(DBHelper.TABLE_USER, null, cv);
        // todo: delete repetitions
    }

    @Nullable @WorkerThread
    static Auth getUser(long id) {
        Auth user = null;

        SQLiteDatabase db = DBHelper.getInstance().getWritableDatabase();
        final Cursor cur = db.query(
                DBHelper.TABLE_USER, new String[] { DBHelper.COL_LOGIN, DBHelper.COL_PASS },
                DBHelper.COL_ID + "= ?", new String[] { String.valueOf(id) },
                null, null, null
        );

        if (cur.moveToFirst()) {
            final int idPass = cur.getColumnIndex(DBHelper.COL_PASS);
            final int idLogin = cur.getColumnIndex(DBHelper.COL_LOGIN);

            final String pass = cur.getString(idPass);
            final String login = cur.getString(idLogin);
            user = new Auth(login, pass);
        }
        cur.close();
        return user;
    }

}
