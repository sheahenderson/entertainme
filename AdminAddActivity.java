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

public class AdminAddActivity extends AppCompatActivity {
    private AdminDBContract.AdminDBHelper dbHelper;
    private SQLiteDatabase AdminDB;
    private EditText titleText;
    private RadioGroup mediaType;
    private EditText genreText;
    private Button addMediaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new AdminDBContract.AdminDBHelper(this);
        AdminDB = dbHelper.getWritableDatabase();
        titleText = (EditText) findViewById(R.id.titleField);
        mediaType = (RadioGroup) findViewById(R.id.mediaTypeGroup);
        genreText = (EditText) findViewById(R.id.genreField);

        addMediaButton = (Button) findViewById(R.id.addItemButton);
        addMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredText = titleText.getText().toString();
                int selectedType = mediaType.getCheckedRadioButtonId();
                String selectedGenre = genreText.getText().toString();
                ContentValues values = new ContentValues();
                //if
                if (selectedType == R.id.bookRB) {
                    values.put(AdminDBContract.BookEntry.COLUMN_NAME_TITLE, enteredText);
                    values.put(AdminDBContract.BookEntry.COLUMN_NAME_GENRE, selectedGenre);
                    AdminDB.insert(AdminDBContract.BookEntry.TABLE_NAME, null,values);
                }
                else {
                    values.put(AdminDBContract.MovieEntry.COLUMN_NAME_TITLE, enteredText);
                    values.put(AdminDBContract.MovieEntry.COLUMN_NAME_GENRE, selectedGenre);
                    AdminDB.insertOrThrow(AdminDBContract.MovieEntry.TABLE_NAME, null, values);
                }
                Toast.makeText(AdminAddActivity.this, "Title Added", Toast.LENGTH_LONG).show();
                AdminAddActivity.this.finish();
            }
        });
    }

}
