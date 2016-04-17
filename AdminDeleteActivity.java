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

/**
 * Created by William on 4/17/2016.
 */
public class AdminDeleteActivity {
    private AdminDBContract.AdminDBHelper dbHelper;
    private SQLiteDatabase AdminDB;
    private UserDBContract.UserDBHelper user_dbHelper;
    private SQLiteDatabase userDB;
    private EditText titleText;
    private RadioGroup mediaType;
    private Button deleteMediaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new AdminDBContract.AdminDBHelper(this);
        AdminDB = dbHelper.getWritableDatabase();
        user_dbHelper = new UserDBContract.UserDBHelper(this);
        userDB = user_dbHelper.getWritableDatabase();
        titleText = (EditText) findViewById(R.id.titleField);
        mediaType = (RadioGroup) findViewById(R.id.mediaTypeGroup);
        deleteMediaButton = (Button) findViewById(R.id.deleteItemButton);
        deleteMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredText = titleText.getText().toString();
                int selectedType = mediaType.getCheckedRadioButtonId();

                ContentValues values = new ContentValues();
                //if
                if (selectedType == R.id.bookRB) {
                    AdminDB.delete(AdminDBContract.BookEntry.TABLE_NAME, AdminDBContract.BookEntry.COLUMN_NAME_TITLE + "=" + enteredText, null);
                    userDB.delete(UserDBContract.BookEntry.TABLE_NAME, UserDBContract.BookEntry.COLUMN_NAME_TITLE + "=" + enteredText, null);
                }
                else {
                    AdminDB.delete(AdminDBContract.MovieEntry.TABLE_NAME, AdminDBContract.MovieEntry.COLUMN_NAME_TITLE + "=" + enteredText, null);
                    userDB.delete(UserDBContract.MovieEntry.TABLE_NAME, UserDBContract.MovieEntry.COLUMN_NAME_TITLE + "=" + enteredText, null);
                }
                Toast.makeText(AdminAddActivity.this, "Title Removed", Toast.LENGTH_LONG).show();
                AdminAddActivity.this.finish();
            }
        });

}
