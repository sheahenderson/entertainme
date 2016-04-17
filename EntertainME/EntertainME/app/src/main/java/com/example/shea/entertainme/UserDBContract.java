package com.example.shea.entertainme;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by MBlock on 3/17/2016.
 */
public final class UserDBContract {

    public UserDBContract() {}

    public static abstract class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "Movies";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_GENRE = "Genre";
        public static final String COLUMN_NAME_RATING = "Rating";
        public static final String COLUMN_NAME_OWNERSHIP = "Ownership";
        public static final String COLUMN_NAME_WISHLIST = "Wishlist";
    }

    public static abstract class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "Books";
        public static final String COLUMN_NAME_TITLE = "Title";
        public static final String COLUMN_NAME_GENRE = "Genre";
        public static final String COLUMN_NAME_RATING = "Rating";
        public static final String COLUMN_NAME_OWNERSHIP = "Ownership";
        public static final String COLUMN_NAME_WISHLIST = "Wishlist";
    }

    public static abstract class UserEntry implements BaseColumns {
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
                    MovieEntry.COLUMN_NAME_GENRE + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_RATING + INT_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_OWNERSHIP + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_WISHLIST + TEXT_TYPE + " )";

    private static final String SQL_CREATE_BOOKS =
            "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                    BookEntry._ID + INT_TYPE + " PRIMARY KEY," +
                    BookEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    BookEntry.COLUMN_NAME_GENRE + TEXT_TYPE + COMMA_SEP +
                    BookEntry.COLUMN_NAME_RATING + INT_TYPE + COMMA_SEP +
                    BookEntry.COLUMN_NAME_OWNERSHIP + TEXT_TYPE + COMMA_SEP +
                    BookEntry.COLUMN_NAME_WISHLIST + TEXT_TYPE + " )";

    private static final String SQL_CREATE_USER =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + INT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    UserEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE + ")";

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
    private static final String SQL_DELETE_USER =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;
    private static final String SQL_DELETE_MATCHLIST =
            "DROP TABLE IF EXISTS " + MatchEntry.TABLE_NAME;

    public static final String[] MOVIE_PROJECTION = new String[] {
            UserDBContract.MovieEntry._ID,
            UserDBContract.MovieEntry.COLUMN_NAME_TITLE,
            UserDBContract.MovieEntry.COLUMN_NAME_RATING,
            UserDBContract.MovieEntry.COLUMN_NAME_GENRE,
            UserDBContract.MovieEntry.COLUMN_NAME_OWNERSHIP,
            UserDBContract.MovieEntry.COLUMN_NAME_WISHLIST
    };

    public static final String[] BOOK_PROJECTION = new String[] {
            UserDBContract.BookEntry._ID,
            UserDBContract.BookEntry.COLUMN_NAME_TITLE,
            UserDBContract.BookEntry.COLUMN_NAME_RATING,
            UserDBContract.BookEntry.COLUMN_NAME_GENRE,
            UserDBContract.BookEntry.COLUMN_NAME_OWNERSHIP,
            UserDBContract.BookEntry.COLUMN_NAME_WISHLIST
    };

    public static class UserDBHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 5;
        public static final String DATABASE_NAME = "User.db";

        public UserDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_USER);
            db.execSQL(SQL_CREATE_BOOKS);
            db.execSQL(SQL_CREATE_MOVIES);
            db.execSQL(SQL_CREATE_MATCH_LIST);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //TODO change this to data migration instead of recreation
            db.execSQL(SQL_DELETE_MOVIES);
            db.execSQL(SQL_DELETE_BOOKS);
            db.execSQL(SQL_DELETE_MATCHLIST);
            db.execSQL(SQL_DELETE_USER);
            db.execSQL(SQL_CREATE_USER);
            db.execSQL(SQL_CREATE_MOVIES);
            db.execSQL(SQL_CREATE_BOOKS);
            db.execSQL(SQL_CREATE_MATCH_LIST);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //TODO
        }

        public String getTitleColumnName(int type) {
            switch (type) {
                case Constants.VIEW_MOVIE:
                    return MovieEntry.COLUMN_NAME_TITLE;
                case Constants.VIEW_BOOK:
                    return BookEntry.COLUMN_NAME_TITLE;
                default:
                    return null;
            }
        }

        public String getRatingColumnName(int type) {
            switch (type) {
                case Constants.VIEW_MOVIE:
                    return MovieEntry.COLUMN_NAME_RATING;
                case Constants.VIEW_BOOK:
                    return BookEntry.COLUMN_NAME_RATING;
                default:
                    return null;
            }
        }

        public String getGenreColumnName(int type) {
            switch (type) {
                case Constants.VIEW_MOVIE:
                    return MovieEntry.COLUMN_NAME_GENRE;
                case Constants.VIEW_BOOK:
                    return BookEntry.COLUMN_NAME_GENRE;
                default:
                    return null;
            }
        }

        public String getOwnershipColumnName(int type) {
            switch (type) {
                case Constants.VIEW_MOVIE:
                    return MovieEntry.COLUMN_NAME_OWNERSHIP;
                case Constants.VIEW_BOOK:
                    return BookEntry.COLUMN_NAME_OWNERSHIP;
                default:
                    return null;
            }
        }

        public String getWishlistColumnName(int type) {
            switch (type) {
                case Constants.VIEW_MOVIE:
                    return MovieEntry.COLUMN_NAME_WISHLIST;
                case Constants.VIEW_BOOK:
                    return BookEntry.COLUMN_NAME_WISHLIST;
                default:
                    return null;
            }
        }

        public String getTableName(int type) {
            switch (type) {
                case Constants.VIEW_MOVIE:
                    return MovieEntry.TABLE_NAME;
                case Constants.VIEW_BOOK:
                    return BookEntry.TABLE_NAME;
                default:
                    return null;
            }
        }

        public Cursor query(SQLiteDatabase db, int type) {
             return query(db, type, true);
        }

        public Cursor query(SQLiteDatabase db, int type, boolean ascending) {
            return query(db, type, ascending, null);
        }

        public Cursor query(SQLiteDatabase db, int type, boolean ascending, String selection) {
            String sortOrder;
            switch (type) {
                case Constants.VIEW_MOVIE:
                    sortOrder = (ascending) ? MovieEntry.COLUMN_NAME_TITLE + " ASC" : MovieEntry.COLUMN_NAME_TITLE + " DESC";
                    return db.query(
                            MovieEntry.TABLE_NAME,
                            MOVIE_PROJECTION,
                            selection,
                            null,
                            null,
                            null,
                            sortOrder
                    );
                case Constants.VIEW_BOOK:
                    sortOrder = (ascending) ? BookEntry.COLUMN_NAME_TITLE + " ASC" : BookEntry.COLUMN_NAME_TITLE + " DESC";
                    return db.query(
                            BookEntry.TABLE_NAME,
                            BOOK_PROJECTION,
                            selection,
                            null,
                            null,
                            null,
                            sortOrder
                    );
                case Constants.VIEW_REC:
                    //TODO:
                    return null;
                default:
                    return null;
            }

        }
    }
}


