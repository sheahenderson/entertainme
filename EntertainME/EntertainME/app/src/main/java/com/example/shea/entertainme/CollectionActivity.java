package com.example.shea.entertainme;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CollectionActivity extends AppCompatActivity
implements ViewDialog.ViewDialogListener {
    private int desire = Constants.INVALID;
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
                desire = Constants.DESIRE_ADD;
                showViewDialog();
            }
        });
        assert viewButton != null;
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desire = Constants.DESIRE_VIEW;
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
        Intent intent;
        if (retVal != Constants.INVALID) {
            switch(desire) {
                case Constants.DESIRE_ADD:
                    intent = new Intent(CollectionActivity.this, AddActivity.class);
                    intent.putExtra(Constants.EXTRAS_ADD, retVal);
                    startActivity(intent);
                    break;
                case Constants.DESIRE_VIEW:
                    intent = new Intent(CollectionActivity.this, ViewActivity.class);
                    intent.putExtra(Constants.EXTRAS_VIEW, retVal);
                    startActivity(intent);
                    break;
                default:
                    //desire = Constants.INVALID;
                    break;
            }
        }
        desire = Constants.INVALID;
    }
}
