package com.example.shea.entertainme;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ViewActivity extends ListActivity {
    public static ArrayList<Integer> idArray = new ArrayList<Integer>();

    private UserDBContract.UserDBHelper helper;
    private SQLiteDatabase userDB;
    ArrayAdapter<String> mAdapter;

    private String[] MOVIE_PROJECTION = new String[] {
            UserDBContract.MovieEntry._ID,
            UserDBContract.MovieEntry.COLUMN_NAME_TITLE,
            UserDBContract.MovieEntry.COLUMN_NAME_RATING,
            UserDBContract.MovieEntry.COLUMN_NAME_GENRE};

    /*private String[] BOOK_PROJECTION = new String[] {
            UserDBContract.BookEntry._ID,
            UserDBContract.BookEntry.COLUMN_NAME_TITLE,
            UserDBContract.BookEntry.COLUMN_NAME_RATING,
            UserDBContract.BookEntry.COLUMN_NAME_GENRE
    };*/

    private String movieSortOrder = UserDBContract.MovieEntry.COLUMN_NAME_TITLE + " DESC";
    //private String bookSortOrder = UserDBContract.BookEntry.COLUMN_NAME_TITLE + " DESC";

    private Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        helper = new UserDBContract.UserDBHelper(this);
        userDB = helper.getReadableDatabase();
        Bundle extras = getIntent().getExtras();
        int requestedView = Constants.VIEW_MOVIE;
        /*if (extras != null) {
            requestedView = extras.getInt(Constants.EXTRAS_VIEW);
        }*/
        displayRequest(requestedView);
    }

    @Override
    protected void onListItemClick(ListView I, View v, int position, long id) {

    }

    private void displayRequest(int request) {
        switch (request) {
            case Constants.VIEW_MOVIE:
                c = userDB.query(
                        UserDBContract.MovieEntry.TABLE_NAME,
                        MOVIE_PROJECTION,
                        null,
                        null,
                        null,
                        null,
                        movieSortOrder
                );
                break;
            /*case Constants.VIEW_BOOK:
                c = userDB.query(
                        UserDBContract.BookEntry.TABLE_NAME,
                        BOOK_PROJECTION,
                        null,
                        null,
                        null,
                        null,
                        bookSortOrder
                );
                break;*/
            case Constants.VIEW_REC:
                //TODO
                break;
            default:
                //TODO
                break;
        }
        String[] dbArray = createDBList();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dbArray);
        setListAdapter(mAdapter);
    }

    private String[] createDBList() {
        List<String> result = new ArrayList<String>();
        if (c.getCount() >= 0) {
            //Log.v("View", String.valueOf(c.getCount()));
            while (c.moveToNext()) {
                String row = "";
                for (int i = 0; i < c.getColumnCount(); i++) {
                    if (c.getColumnName(i) == UserDBContract.MovieEntry._ID || c.getColumnName(i) == UserDBContract.BookEntry._ID) {
                        idArray.add(c.getInt(i));
                    }
                    else {
                        row += c.getColumnName(i) + ": " + c.getString(i) + "\n";
                    }
                }
                result.add(row);
            }
        }
        return result.toArray(new String[result.size()]);
    }

}
