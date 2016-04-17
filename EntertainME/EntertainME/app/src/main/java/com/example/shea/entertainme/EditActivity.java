package com.example.shea.entertainme;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        int ID = extras.getInt(Constants.EXTRAS_EDIT);
        int type = extras.getInt(Constants.EXTRAS_TYPE);
        EditText titleText = (EditText) findViewById(R.id.titleDisplay);
        RatingBar rBar = (RatingBar) findViewById(R.id.ratingBar2);

        final CheckBox ownership = (CheckBox) findViewById(R.id.ownershipButton);
        final CheckBox wishList = (CheckBox) findViewById(R.id.wishListButton);

        Button submitButton = (Button) findViewById(R.id.submitButton);

        UserDBContract.UserDBHelper helper = new UserDBContract.UserDBHelper(this);
        SQLiteDatabase userDB = helper.getWritableDatabase();
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
    }

}
