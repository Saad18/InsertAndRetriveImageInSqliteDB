package com.example.insertandretriveimageinsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

import androidx.annotation.Nullable;

public class DatabseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "image.db";
    private static final String TABLE_NAME = "imagetable";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_NAME = "imagename";
    private static final String IMAGE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ _ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_NAME+" BLOB NOT NULL "+")";



    public DatabseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(IMAGE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    public void insertToDb(byte[] image){
        SQLiteDatabase liteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,image);
        liteDatabase.insert(TABLE_NAME,null,contentValues);

    }

    Cursor showData(){
        String show_all = "select * From "+TABLE_NAME;
        SQLiteDatabase liteDatabase = getReadableDatabase();
        return liteDatabase.rawQuery(show_all,null);
    }
}
