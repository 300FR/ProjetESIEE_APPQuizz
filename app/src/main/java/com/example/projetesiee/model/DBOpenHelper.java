package com.example.projetesiee.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String USER_TABLE_NAME = "user";

    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_BIRTHDAY = "birthday";
    public static final String USER_COLUMN_LAST_LOGIN = "last_login";
    public static final String USER_COLUMN_BEST_SCORE = "best_score";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table "+USER_TABLE_NAME+" (" +
                        USER_COLUMN_USERNAME +"text primary key, "+
                        USER_COLUMN_BIRTHDAY +" text, "+
                        USER_COLUMN_LAST_LOGIN +" text, "+
                        USER_COLUMN_BEST_SCORE +" text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }


    public HashMap<String,String> getLeaderboard(){
        HashMap<String,String> map = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+USER_TABLE_NAME, null );
        res.moveToFirst();

        int bestScoreIndex = res.getColumnIndex(USER_COLUMN_BEST_SCORE);
        int usernameIndex = res.getColumnIndex(USER_COLUMN_USERNAME);
        while(!res.isAfterLast()){
            map.put(
                    res.getString(bestScoreIndex),
                    res.getString(usernameIndex)
            );
            res.moveToNext();
        }
        res.close();
        return map;
    }

    public boolean insertUser (String username, String birthday) {
        SQLiteDatabase db = this.getWritableDatabase();
        String currentTime = Calendar.getInstance().getTime().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_USERNAME, username);
        contentValues.put(USER_COLUMN_BIRTHDAY, birthday);
        contentValues.put(USER_COLUMN_BEST_SCORE, "0");
        contentValues.put(USER_COLUMN_LAST_LOGIN, currentTime);
        return (db.insert(USER_TABLE_NAME, null, contentValues)!=-1);
    }

    public boolean insertUser (String username, String birthday, String bestScore) {
        SQLiteDatabase db = this.getWritableDatabase();
        String currentTime = Calendar.getInstance().getTime().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_USERNAME, username);
        contentValues.put(USER_COLUMN_BIRTHDAY, birthday);
        contentValues.put(USER_COLUMN_BEST_SCORE, bestScore);
        contentValues.put(USER_COLUMN_LAST_LOGIN, currentTime);
        return (db.insert(USER_TABLE_NAME, null, contentValues)!=-1);
    }

    public Cursor getData(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+USER_TABLE_NAME+" where "+USER_COLUMN_USERNAME+"="+username+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, USER_TABLE_NAME);
        return numRows;
    }

    public boolean editUser(User user, String username, String birthday){
        SQLiteDatabase db = this.getWritableDatabase();
        if(insertUser(username, birthday, String.valueOf(user.getBestScore()) )) {
            db.delete(USER_TABLE_NAME, USER_COLUMN_USERNAME + "=?", new String[]{user.getUsername()});
            return  true;
        }
        return false;
    }

    public boolean updateUserBestScore(String username, int Score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_BEST_SCORE, username);
        db.update(USER_TABLE_NAME, contentValues, USER_COLUMN_USERNAME+" = ? ", new String[] { username } );
        return true;
    }

    public boolean updateUser (String username, String birthday, String bestScore, String currentTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_USERNAME, username);
        contentValues.put(USER_COLUMN_BIRTHDAY, birthday);
        contentValues.put(USER_COLUMN_BEST_SCORE, bestScore);
        contentValues.put(USER_COLUMN_LAST_LOGIN, currentTime);
        db.update(USER_TABLE_NAME, contentValues, USER_COLUMN_USERNAME+" = ? ", new String[] { username } );
        return true;
    }

    public int deleteUser (User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE_NAME, USER_COLUMN_USERNAME + "=?", new String[]{user.getUsername()});
    }

    public ArrayList<String> getAllUsers() {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        int usernameIndex = res.getColumnIndex(USER_COLUMN_USERNAME);

        while(!res.isAfterLast()){
            array_list.add(res.getString(usernameIndex));
            res.moveToNext();
        }
        res.close();
        return array_list;
    }

    public User getUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+USER_TABLE_NAME+" where "+USER_COLUMN_USERNAME+"="+username, null );
        if(res.moveToFirst()){
            int birthdayIndex = res.getColumnIndex(USER_COLUMN_BIRTHDAY);
            int bestScoreIndex = res.getColumnIndex(USER_COLUMN_BEST_SCORE);

            String birthday = res.getString(birthdayIndex);
            int bestScore = Integer.parseInt(res.getString(bestScoreIndex));
            res.close();
            return new User(username, bestScore, birthday);
        }
        return null;
    }

    public User getLastUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select USER_COLUMN_NAME from USER_TABLE_NAME order by USER_COLUMN_LAST_LOGIN desc limit 1", null );
        if(res.moveToFirst()){
            int usernameIndex = res.getColumnIndex(USER_COLUMN_USERNAME);
            int birthdayIndex = res.getColumnIndex(USER_COLUMN_BIRTHDAY);
            int bestScoreIndex = res.getColumnIndex(USER_COLUMN_BEST_SCORE);

            String username = res.getString(usernameIndex);
            String birthday = res.getString(birthdayIndex);
            int bestScore = Integer.parseInt(res.getString(bestScoreIndex));
            res.close();
            return new User(username, bestScore, birthday);
        }
        return null;
    }
}