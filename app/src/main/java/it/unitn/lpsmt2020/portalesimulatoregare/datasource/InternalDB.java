package it.unitn.lpsmt2020.portalesimulatoregare.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InternalDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public static final String TABLE_SUB_CHAMPIONSHIP = "championship";
    public static final String TABLE_SUB_CHAMPIONSHIP_ID = "id";

    private static Context context;
    private static InternalDB helper = null;

    public InternalDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + InternalDB.TABLE_SUB_CHAMPIONSHIP + "(" +
                        TABLE_SUB_CHAMPIONSHIP_ID + " INTEGER PRIMARY KEY" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUB_CHAMPIONSHIP);
        onCreate(db);
    }

    public static void initDB(Context ctx) {
        if (InternalDB.helper == null) {
            InternalDB.context = ctx;
            InternalDB.helper = new InternalDB(ctx);
        }

    }

    public static void subscribe(int id) {

        ContentValues values = new ContentValues();
        values.put(InternalDB.TABLE_SUB_CHAMPIONSHIP_ID, id);

        SQLiteDatabase db = InternalDB.helper.getWritableDatabase();
        db.insert(InternalDB.TABLE_SUB_CHAMPIONSHIP, null, values);
    }

    public static boolean isSubscribed(int id) {
        SQLiteDatabase db = InternalDB.helper.getReadableDatabase();

        int cc = db.query(
                InternalDB.TABLE_SUB_CHAMPIONSHIP,
                new String[] {InternalDB.TABLE_SUB_CHAMPIONSHIP_ID},
                InternalDB.TABLE_SUB_CHAMPIONSHIP_ID + " = ?",
                new String[] { Integer.toString(id) },
                null, null, null
        ).getCount();

        return (cc > 0);
    }

    public static void unsubscribe(int id) {
        SQLiteDatabase db = InternalDB.helper.getReadableDatabase();
        db.delete(InternalDB.TABLE_SUB_CHAMPIONSHIP, InternalDB.TABLE_SUB_CHAMPIONSHIP_ID + " = ?", new String[] { Integer.toString(id) });
    }


}
