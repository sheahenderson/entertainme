package com.example.shea.entertainme;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends ListActivity {
    public static ArrayList<Integer> idArray = new ArrayList<>();
    private boolean first = true;
    private UserDBContract.UserDBHelper helper;
    private SQLiteDatabase userDB;
    private int requestedView;
    ArrayAdapter<String> mAdapter;

    private String movieSortOrder = UserDBContract.MovieEntry.COLUMN_NAME_TITLE + " DESC";
    private String bookSortOrder = UserDBContract.BookEntry.COLUMN_NAME_TITLE + " DESC";

    private Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        helper = new UserDBContract.UserDBHelper(this);
        userDB = helper.getReadableDatabase();
        Bundle extras = getIntent().getExtras();
        requestedView = Constants.VIEW_MOVIE;
        if (extras != null) {
            requestedView = extras.getInt(Constants.EXTRAS_VIEW);
        }
        displayRequest(requestedView);
        first = false;
    }

    @Override
    protected void onStop() {
        userDB.close();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onListItemClick(ListView I, View v, int position, long id) {
        Intent intent = new Intent(ViewActivity.this, EditActivity.class);
        intent.putExtra(Constants.EXTRAS_EDIT, idArray.get(position));
        intent.putExtra(Constants.EXTRAS_TYPE, requestedView);
        startActivityForResult(intent, 0);
    }

    private void displayRequest(int request) {
        c = helper.query(userDB, request, false);
        String[] dbArray = createDBList();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dbArray);
        setListAdapter(mAdapter);
    }

    private String[] createDBList() {
        List<String> result = new ArrayList<>();
        if (c.getCount() >= 0) {
            //Log.v("View", String.valueOf(c.getCount()));
            while (c.moveToNext()) {
                String row = "";
                for (int i = 0; i < c.getColumnCount(); i++) {
                    if (c.getColumnName(i).equals(UserDBContract.MovieEntry._ID) || c.getColumnName(i).equals(UserDBContract.BookEntry._ID)) {
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
