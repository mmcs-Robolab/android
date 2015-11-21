package mmcs.robolab.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    static public String TABLE_USER = "users";
    static public String COL_ID = "_id";
    static public String COL_LOGIN = "name";
    static public String COL_PASS = "pass";
    static public String COL_LAST = "isLast";


    static private DBHelper instance;

    private DBHelper(Context context) {
        super(context, "robolab", null, 1);
    }

    static public int initialize(Context context) {
        instance = new DBHelper(context);
        return 0;
    }

    static public DBHelper getInstance() {
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
