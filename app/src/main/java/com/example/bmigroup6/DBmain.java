package com.example.bmigroup6;

import static android.provider.BaseColumns._ID;

import static com.example.bmigroup6.Constants.BMI;
import static com.example.bmigroup6.Constants.RANK;
import static com.example.bmigroup6.Constants.TABLE_NAME;
import static com.example.bmigroup6.Constants.TIME;
import static com.example.bmigroup6.Constants.WEIGHT;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class DBmain extends SQLiteOpenHelper {
    public DBmain(Context ctx) {
        super(ctx, "event.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + _ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TIME + " TEXT NOT NULL, "
                + WEIGHT + " TEXT NOT NULL, "
                + BMI + " TEXT NOT NULL,"
                + RANK + " TEXT NOT NULL);"  );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS events");
        onCreate(db);
    }
}
