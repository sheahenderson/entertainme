package com.example.shea.entertainme;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CollectionActivity extends AppCompatActivity
implements ViewDialog.ViewDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button viewButton = (Button) findViewById(R.id.viewCollectionButton);
        Button addItem = (Button) findViewById(R.id.editCollectionButton);
        assert addItem != null;
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(v.getContext(), AddMovieActivity.class);
                startActivityForResult(nextScreen, 0);
            }
        });
        assert viewButton != null;
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent nextScreen = new Intent(v.getContext(), ViewActivity.class);
                //startActivityForResult(nextScreen, 0);
                showViewDialog();
            }
        });
    }

    public void showViewDialog() {
        ViewDialog getTypeDialog = new ViewDialog();
        getTypeDialog.show(getFragmentManager(), "ViewDialog");
    }

    @Override
    public void onItemClick(DialogFragment dialog, int retVal) {
        if (retVal != Constants.INVALID) {
            Intent intent = new Intent(CollectionActivity.this, ViewActivity.class);
            intent.putExtra(Constants.EXTRAS_VIEW, retVal);
            startActivity(intent);
        }
    }
}
