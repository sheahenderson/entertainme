package com.example.shea.entertainme;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    protected UserDBContract.UserDBHelper helper;
    protected SQLiteDatabase userDB;
    protected EditText titleText;
    protected RatingBar rBar;
    protected CheckBox ownership;
    protected CheckBox wishList;
    protected int type;
    protected int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        ID = extras.getInt(Constants.EXTRAS_EDIT);
        type = extras.getInt(Constants.EXTRAS_TYPE);
        titleText = (EditText) findViewById(R.id.titleDisplay);
        rBar = (RatingBar) findViewById(R.id.ratingBar2);

        ownership = (CheckBox) findViewById(R.id.ownershipButton);
        wishList = (CheckBox) findViewById(R.id.wishListButton);

        Button submitButton = (Button) findViewById(R.id.submitButton);

        helper = new UserDBContract.UserDBHelper(this);
        userDB = helper.getWritableDatabase();
        Cursor c = helper.query(userDB, type, false, "_ID = " + Integer.toString(ID));

        if (c.getCount() > 0) {
            c.moveToNext();
            assert titleText != null;
            titleText.setText(c.getString(c.getColumnIndex(helper.getTitleColumnName(type))));
            rBar.setRating((float)c.getInt(c.getColumnIndex(helper.getRatingColumnName(type))));
            String own = c.getString(c.getColumnIndex(helper.getOwnershipColumnName(type)));
            String wish = c.getString(c.getColumnIndex(helper.getWishlistColumnName(type)));
            ownership.setChecked(own.equalsIgnoreCase("yes"));
            wishList.setChecked(wish.equalsIgnoreCase("yes"));
        }

        assert submitButton != null;
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String owns = (ownership.isChecked()) ? "yes" : "no";
                String wishes = (wishList.isChecked()) ? "yes" : "no";
                int rating = Math.round(rBar.getRating());
                ContentValues values = new ContentValues();
                values.put(UserDBContract.BookEntry.COLUMN_NAME_RATING, rating);
                values.put(UserDBContract.BookEntry.COLUMN_NAME_OWNERSHIP, owns);
                values.put(UserDBContract.BookEntry.COLUMN_NAME_WISHLIST, wishes);
                userDB.update(helper.getTableName(type), values, "_ID = " + ID, null);
                Toast.makeText(EditActivity.this, "Title Updated", Toast.LENGTH_LONG).show();
                EditActivity.this.finish();
            }
        });
    }

    @Override
    protected void onStop() {
        userDB.close();
        super.onStop();
    }

}
