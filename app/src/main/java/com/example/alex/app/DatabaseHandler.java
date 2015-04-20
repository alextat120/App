package com.example.alex.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alex on 4/12/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scoreDatabase";
    private static final String TABLE_SCORES = "scores";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String Key_TIME = "time";
    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORE_TABLE = "CREATE TABLE "+TABLE_SCORES+"("+
                KEY_ID+ " INTEGER PRIMARY KEY,"+KEY_NAME+" TEXT,"
                + Key_TIME + " TEXT"+ ")";
        db.execSQL(CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SCORES);
        onCreate(db);
    }
    public void addScore(Score score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,score.getName());
        values.put(Key_TIME,score.getTime());
        db.insert(TABLE_SCORES,null,values);
        db.close();
    }
    public Score getScore(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_SCORES + " WHERE " + DATABASE_NAME + " =  \"" + KEY_NAME + "\"";

        Cursor cursor = db.query(TABLE_SCORES,new String[]{ KEY_ID, KEY_NAME,Key_TIME},KEY_ID + "=?",
            new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Score score = new Score(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        return score;
    }
    public int getScoreCount(){
        String countQuery = "SELECT * From "+TABLE_SCORES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();
        return cursor.getCount();
    }
    public void deleteScore(Score score){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCORES, KEY_ID + " = ?",
        new String[] { String.valueOf(score.getID()) });
        db.close();
    }

}
