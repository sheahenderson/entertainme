package com.example.shea.entertainme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EntertainMeLoginInterface extends AppCompatActivity {
    private UserDBContract.UserDBHelper dbHelper;
    private SQLiteDatabase userDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertain_me_login_interface);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new UserDBContract.UserDBHelper(this);
        userDB = dbHelper.getReadableDatabase();

        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final Button registerButton = (Button) findViewById(R.id.registerButton);
        final EditText usernameField = (EditText) findViewById(R.id.usernameField);
        final EditText passwordField = (EditText) findViewById(R.id.passwordField);

        assert loginButton != null;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get username and password from on device db and set as check value
                String adminPassword = "1900mixalot";
                String enteredName = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                if (enteredName.equalsIgnoreCase("admin") && password.equalsIgnoreCase(adminPassword)) {
                    Intent nextScreen = new Intent(v.getContext(), AdminHomeActivity.class);
                    startActivityForResult(nextScreen, 0);
                }
                else if (enteredName.equalsIgnoreCase(getUserName()) && password.equalsIgnoreCase(getPassword())) {
                    Intent nextScreen = new Intent(v.getContext(), CollectionActivity.class);
                    startActivityForResult(nextScreen, 0);
                }
                else {
                    Toast.makeText(EntertainMeLoginInterface.this, "Username/Password Combo not recognized", Toast.LENGTH_SHORT).show();
                }
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        assert registerButton != null;
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(v.getContext(), RegistrationActivity.class);
                startActivityForResult(nextScreen, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entertain_me_login_interface, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getUserName() {
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
            c.moveToNext();
            return c.getString(0);
        }
        else {
            Toast.makeText(EntertainMeLoginInterface.this, "No users exist in database", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private String getPassword() {
        Cursor c;
        String[] usernameProjection = new String[] {
                UserDBContract.UserEntry.COLUMN_NAME_PASSWORD
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
            c.moveToNext();
            return c.getString(0);
        }
        else {
            //Toast.makeText(EntertainMeLoginInterface.this, "TTTHHPPT!!!", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
