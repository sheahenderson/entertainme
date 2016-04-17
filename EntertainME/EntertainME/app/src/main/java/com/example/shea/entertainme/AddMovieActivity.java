package com.example.shea.entertainme;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddMovieActivity extends AppCompatActivity {
    private UserDBContract.UserDBHelper dbHelper;
    private SQLiteDatabase userDB;
    private EditText titleText;
    private RadioGroup mediaType;
    private RatingBar userRating;
    private Spinner genreSpinner;
    private Button addMediaButton;
    private String selectedGenre = "Other";
    List<String> genres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new UserDBContract.UserDBHelper(this);
        userDB = dbHelper.getWritableDatabase();

        //EditText setup
        titleText = (EditText) findViewById(R.id.titleField);

        //RadioGroup setup
        //mediaType = (RadioGroup) findViewById(R.id.mediaTypeGroup);

        //RatingBar setup
        userRating = (RatingBar) findViewById(R.id.ratingBar);

        //Spinner setup
        /*genreSpinner = (Spinner) findViewById(R.id.genreSpinner);
        genres = new ArrayList<String>();
        genres.add("Action");
        genres.add("Comedy");
        genres.add("Romance");
        genres.add("Horror");
        genres.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genres);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(dataAdapter);
        AdapterView.OnItemSelectedListener onSpinner = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGenre = genres.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGenre = genres.get(genres.size()-1);
            }
        };
        genreSpinner.setOnItemSelectedListener(onSpinner);
        */
        final CheckBox ownership = (CheckBox) findViewById(R.id.ownershipButton);
        final CheckBox wishList = (CheckBox) findViewById(R.id.wishListButton);

        //Button setup
        addMediaButton = (Button) findViewById(R.id.addItemButton);
        addMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredText = titleText.getText().toString();
                String owns = (ownership.isChecked()) ? "yes" : "no";
                String wishes = (wishList.isChecked()) ? "yes" : "no";
                //int selectedType = mediaType.getCheckedRadioButtonId();
                int rating = Math.round(userRating.getRating());
                ContentValues values = new ContentValues();
                //if
                /*if (selectedType == R.id.bookRB) {
                    values.put(UserDBContract.BookEntry.COLUMN_NAME_TITLE, enteredText);
                    values.put(UserDBContract.BookEntry.COLUMN_NAME_GENRE, selectedGenre);
                    values.put(UserDBContract.BookEntry.COLUMN_NAME_RATING, rating);
                    userDB.insert(UserDBContract.BookEntry.TABLE_NAME, null,values);
                }*/
                //else {
                values.put(UserDBContract.MovieEntry.COLUMN_NAME_TITLE, enteredText);
                values.put(UserDBContract.MovieEntry.COLUMN_NAME_GENRE, "default");
                values.put(UserDBContract.MovieEntry.COLUMN_NAME_RATING, rating);
                values.put(UserDBContract.MovieEntry.COLUMN_NAME_OWNERSHIP, owns);
                values.put(UserDBContract.MovieEntry.COLUMN_NAME_WISHLIST, wishes);
                userDB.insertOrThrow(UserDBContract.MovieEntry.TABLE_NAME, null, values);
                //}
                Toast.makeText(AddMovieActivity.this, "Title Added", Toast.LENGTH_LONG).show();
                AddMovieActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStop() {
        userDB.close();
        super.onStop();
    }
}
