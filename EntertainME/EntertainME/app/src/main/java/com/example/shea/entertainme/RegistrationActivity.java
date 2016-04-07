package com.example.shea.entertainme;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    private UserDBContract.UserDBHelper dbHelper;
    private SQLiteDatabase userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        dbHelper = new UserDBContract.UserDBHelper(this);
        userDB = dbHelper.getWritableDatabase();
        final EditText userNameField = (EditText) findViewById(R.id.userNameField);
        final EditText passwordField = (EditText) findViewById(R.id.passwordField);
        final EditText confirmPassword = (EditText) findViewById(R.id.confirmPasswordField);
        final Button submitButton = (Button) findViewById(R.id.registerNowButton);

        assert submitButton != null;
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = "";
                String password = "";
                String confirm = "";
                //boolean emptyField = false;
                try {
                    username = userNameField.getText().toString();
                    password = passwordField.getText().toString();
                    confirm = confirmPassword.getText().toString();
                }
                catch(NullPointerException e) {
                    //emptyField = true;
                    Toast.makeText(RegistrationActivity.this, "Please complete all fields to register", Toast.LENGTH_SHORT).show();
                    RegistrationActivity.this.finish();
                }

                if (!password.equalsIgnoreCase(confirm) && !password.equalsIgnoreCase("")) {
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    RegistrationActivity.this.finish();
                }
                else if (!usernameUnique(username) && !username.equalsIgnoreCase("")) {
                    Toast.makeText(RegistrationActivity.this, "That username is already in use", Toast.LENGTH_SHORT).show();
                    RegistrationActivity.this.finish();
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put(UserDBContract.UserEntry.COLUMN_NAME_USERNAME, username);
                    values.put(UserDBContract.UserEntry.COLUMN_NAME_PASSWORD, password);
                    userDB.insert(UserDBContract.UserEntry.TABLE_NAME, null, values);
                    Toast.makeText(RegistrationActivity.this, "Registration complete, please login to continue", Toast.LENGTH_SHORT).show();
                    RegistrationActivity.this.finish();
                }
            }
        });
    }

    private boolean usernameUnique(String name)  {
        Cursor c;
        String[] usernameProjection = new String[] {
                UserDBContract.UserEntry.COLUMN_NAME_USERNAME
        };
        c = userDB.query(
                UserDBContract.UserEntry.TABLE_NAME,
                usernameProjection,
                null,
                null,
                null,
                null,
                null
        );
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                Log.i("Registration", c.getString(0));
                Log.i("Name", name);
                if (name.equalsIgnoreCase(c.getString(0))) {
                    return false;
                }
            }
        }
        return true;
    }

}
