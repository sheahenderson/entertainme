package com.example.shea.entertainme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by William on 4/17/2016.
 */
public class AdminDBContract {
    public AdminDBContract() {}

    public static abstract class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "Movies";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_GENRE = "Genre";
    }

    public static abstract class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "Books";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_GENRE = "Genre";
    }

    public static abstract class AdminEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_USERNAME = "Username";
        public static final String COLUMN_NAME_PASSWORD = "Password";
    }

    public static abstract class MatchEntry implements BaseColumns {
        public static final String TABLE_NAME = "MatchLists";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_GENRE = "Genre";
        public static final String COLUMN_NAME_RATING = "Rating";
        public static final String COLUMN_NAME_OTHER_USER = "Recommender";
    }

    //TODO ADD WISHLIST

    private static final String COMMA_SEP = ",";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";

    private static final String SQL_CREATE_MOVIES =
            "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                    MovieEntry._ID + INT_TYPE + " PRIMARY KEY," +
                    MovieEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_GENRE + TEXT_TYPE + COMMA_SEP + " )";

    private static final String SQL_CREATE_BOOKS =
            "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                    BookEntry._ID + INT_TYPE + " PRIMARY KEY," +
                    BookEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    BookEntry.COLUMN_NAME_GENRE + TEXT_TYPE + COMMA_SEP + " )";

    private static final String SQL_CREATE_Admin =
            "CREATE TABLE " + AdminEntry.TABLE_NAME + " (" +
                    AdminEntry._ID + INT_TYPE + COMMA_SEP +
                    AdminEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    AdminEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE + ")";

    private static final String SQL_CREATE_MATCH_LIST =
            "CREATE TABLE " + MatchEntry.TABLE_NAME + " (" +
                    MatchEntry._ID + INT_TYPE + COMMA_SEP +
                    MatchEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    MatchEntry.COLUMN_NAME_OTHER_USER + TEXT_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    MatchEntry.COLUMN_NAME_GENRE + TEXT_TYPE + COMMA_SEP +
                    MatchEntry.COLUMN_NAME_RATING + INT_TYPE + ")";

    private static final String SQL_DELETE_MOVIES =
            "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;
    private static final String SQL_DELETE_BOOKS =
            "DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME;
    private static final String SQL_DELETE_Admin =
            "DROP TABLE IF EXISTS " + AdminEntry.TABLE_NAME;
    private static final String SQL_DELETE_MATCHLIST =
            "DROP TABLE IF EXISTS " + MatchEntry.TABLE_NAME;

    public static class AdminDBHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 5;
        public static final String DATABASE_NAME = "User.db";

        public AdminDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_Admin);
            db.execSQL(SQL_CREATE_BOOKS);
            db.execSQL(SQL_CREATE_MOVIES);
            db.execSQL(SQL_CREATE_MATCH_LIST);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //TODO change this to data migration instead of recreation
            db.execSQL(SQL_DELETE_MOVIES);
            db.execSQL(SQL_DELETE_BOOKS);
            db.execSQL(SQL_DELETE_MATCHLIST);
            db.execSQL(SQL_DELETE_Admin);
            db.execSQL(SQL_CREATE_Admin);
            db.execSQL(SQL_CREATE_MOVIES);
            db.execSQL(SQL_CREATE_BOOKS);
            db.execSQL(SQL_CREATE_MATCH_LIST);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //TODO
        }
    }
}
