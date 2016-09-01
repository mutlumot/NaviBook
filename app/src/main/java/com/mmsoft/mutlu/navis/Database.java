package com.mmsoft.mutlu.navis;

/**
 * Created by MUTLU on 18/12/2015.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper{

    private static final String DATABASE = "locations.sqlite";
    private static final int SURUM =  1;

    public Database(Context con){
        super(con,DATABASE,null,SURUM);

    }
    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE location (id INTEGER PRIMARY KEY AUTOINCREMENT, address TEXT, latitude DOUBLE,longitude DOUBLE)");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXIST location");
        onCreate(db);

    }

}