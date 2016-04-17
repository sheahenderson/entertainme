package com.example.shea.entertainme;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class AddActivity extends AppCompatActivity {
    private SQLiteDatabase userDB;
    private EditText titleText;
    //private RadioGroup mediaType;
    private RatingBar userRating;
    private int type = Constants.INVALID;
    //private String selectedGenre = "Other";
    //List<String> genres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserDBContract.UserDBHelper dbHelper = new UserDBContract.UserDBHelper(this);
        userDB = dbHelper.getWritableDatabase();

        titleText = (EditText) findViewById(R.id.titleField);

        userRating = (RatingBar) findViewById(R.id.ratingBar);

        final CheckBox ownership = (CheckBox) findViewById(R.id.ownershipButton);
        final CheckBox wishList = (CheckBox) findViewById(R.id.wishListButton);

        //Button setup
        Button addMediaButton = (Button) findViewById(R.id.addItemButton);
        assert addMediaButton != null;
        addMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredText = titleText.getText().toString();
                String owns;
                String wishes;
                try {
                    owns = (ownership.isChecked()) ? "yes" : "no";
                    wishes = (wishList.isChecked()) ? "yes" : "no";
                }
                catch (NullPointerException e) {
                    owns = "no";
                    wishes = "no";
                }
                Bundle extras = getIntent().getExtras();
                int selectedType = Constants.VIEW_MOVIE;
                if (extras != null) {
                    selectedType = extras.getInt(Constants.EXTRAS_ADD);
                }
                int rating = Math.round(userRating.getRating());
                ContentValues values = new ContentValues();
                if (selectedType == Constants.VIEW_BOOK) {
                    values.put(UserDBContract.BookEntry.COLUMN_NAME_TITLE, enteredText);
                    values.put(UserDBContract.BookEntry.COLUMN_NAME_GENRE, "default");
                    values.put(UserDBContract.BookEntry.COLUMN_NAME_RATING, rating);
                    values.put(UserDBContract.BookEntry.COLUMN_NAME_OWNERSHIP, owns);
                    values.put(UserDBContract.BookEntry.COLUMN_NAME_WISHLIST, wishes);
                    userDB.insert(UserDBContract.BookEntry.TABLE_NAME, null,values);
                }
                else {
                    values.put(UserDBContract.MovieEntry.COLUMN_NAME_TITLE, enteredText);
                    values.put(UserDBContract.MovieEntry.COLUMN_NAME_GENRE, "default");
                    values.put(UserDBContract.MovieEntry.COLUMN_NAME_RATING, rating);
                    values.put(UserDBContract.MovieEntry.COLUMN_NAME_OWNERSHIP, owns);
                    values.put(UserDBContract.MovieEntry.COLUMN_NAME_WISHLIST, wishes);
                    userDB.insert(UserDBContract.MovieEntry.TABLE_NAME, null, values);
                }
                Toast.makeText(AddActivity.this, "Title Added", Toast.LENGTH_LONG).show();
                AddActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStop() {
        userDB.close();
        super.onStop();
    }
}
