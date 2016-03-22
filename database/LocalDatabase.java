package com.xchange_place.anon.database;

/**
 * Created by Ryan Fletcher on 4/9/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xchange_place.anon.dataholders.LoginData;

public class LocalDatabase extends SQLiteOpenHelper {

    private static final String TABLE_LOGIN = "table_login";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_LOGGED_IN = "loggedin";
    private static final String COLUMN_SHORTCUT = "shortcut";

    private static final String DATABASE_NAME = "local_database.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_LOGIN + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_LOGGED_IN + "integer, "
                + COLUMN_SHORTCUT + " text);";

    public LocalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(LocalDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
    }


    public void insertLoginData(LoginData loginData){
        // Gets the data repository in write mode
        SQLiteDatabase db =this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOGGED_IN, loginData.isLoggedIn());
        cv.put(COLUMN_SHORTCUT, loginData.getShortcut());

        // Inserts the map of values into the database
        db.insert(TABLE_LOGIN, null, cv);
    }

    public LoginData readLoginData(){
        SQLiteDatabase db = this.getReadableDatabase();

    // Define a projection that specifies which columns from the database
    // you will actually use after this query.
        String[] projection = {
                COLUMN_LOGGED_IN,
                COLUMN_SHORTCUT
        };

        Cursor c = db.query(
                TABLE_LOGIN,    // The table to query
                projection,     // The columns to return
                null,           // The columns for the WHERE clause
                null,           // The values for the WHERE clause
                null,           // don't group the rows
                null,           // don't filter by row groups
                null            // The sort order
        );

        // Create a LoginData instance to store the login data
        LoginData loginData = new LoginData();

        // Move cursor to first row
        c.moveToFirst();

        // Add data to LoginData instance
        loginData.setLoggedIn(c.getInt( c.getColumnIndex(COLUMN_LOGGED_IN)));
        loginData.setShortcut(c.getString(c.getColumnIndex(COLUMN_SHORTCUT)));

        // Close cursor to avoid memory leaks
        c.close();

        return loginData;
        }

    public void updateLoginData(LoginData loginData){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOGGED_IN, loginData.isLoggedIn());
        cv.put(COLUMN_SHORTCUT, loginData.getShortcut());

        // Which row to update, based on the ID
        String selection = COLUMN_SHORTCUT + " LIKE ?";
        String[] selectionArgs = { loginData.getShortcut() };

       db.update(TABLE_LOGIN, cv, selection, selectionArgs);
    }
}
