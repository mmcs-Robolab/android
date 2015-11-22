package mmcs.robolab.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mmcs.robolab.Robolab;


public class DBHelper extends SQLiteOpenHelper {

    final static public String TABLE_USER = "users";
    final static public String COL_ID = "_id";
    final static public String COL_LOGIN = "name";
    final static public String COL_PASS = "pass";
    final static public String COL_LAST = "isLast";


    static private DBHelper instance;

    private DBHelper(Context context) {
        super(context, "robolab", null, 1);
    }

    static public DBHelper getInstance() {
        if (instance == null) {
            final Context context = Robolab.getAppContext();
            instance = new DBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USER + " ("
                + COL_ID + " integer primary key autoincrement,"
                + COL_LOGIN + " text,"
                + COL_PASS + " text,"
                + COL_LAST + " integer"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
