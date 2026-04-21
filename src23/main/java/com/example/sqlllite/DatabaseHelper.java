package com.example.sqlllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME= "SQLlite";
    public static final String TABLE_NAME= "users";
    public static final String COULMN_ID= "id";
    public static final String COLUMN_USER_NAME= "username";

    public static final int DB_VERSION= 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table "+ TABLE_NAME + "(" + COULMN_ID +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME +
                "username VARCHAR)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean addUser(String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, name);

        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public Cursor readStoredCalculation() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + ";";
        return sqLiteDatabase.rawQuery(sql, null);
    }
}
