package nassaty.dev.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Prince on 5/23/2017.
 */

public class SqliteHandler extends SQLiteOpenHelper {

    private static String TAG = SqliteHandler.class.getSimpleName();

    //Static variables
    //Db version
    private static int DATABASE_VERSION = 1;

    //Db name
    private static final String DATABASE_NAME = "android_api";

    //login  table name
    private static final String TABLE_USER = "user";

    //login table columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_UUID = "uuid";
    private static final String KEY_CREATED_AT = "created at";

    public SqliteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
                + KEY_PHONE + "TEXT, " + KEY_UUID + "TEXT, "
                + KEY_CREATED_AT + "TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Login table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_USER);
        onCreate(db);
    }

    public void addUser(String uuid, String name, String phone, String created_at){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UUID, uuid);
        values.put(KEY_NAME, name);
        values.put(KEY_PHONE, phone);
        values.put(KEY_CREATED_AT, created_at);

        long id = db.insert(TABLE_USER, null, values);
        db.close();
        Log.d(TAG, "New user inserted"+id);


    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT * FROM "+TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0){
            user.put("name", cursor.getString(1));
            user.put("phone", cursor.getString(2));
            user.put("password", cursor.getString(3));
            user.put("created_at",cursor.getString(4));
        }

        cursor.close();
        db.close();

        Log.d(TAG, "Fetching user info..."+user.toString());

        return user;

    }

    public void deleteAllUsers(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "All users deleted");
    }



}
