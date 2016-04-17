package com.example.shea.entertainme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by MBlock on 4/3/2016.
 */
public class ViewDialog extends DialogFragment {

    public static ViewDialog newInstance(String title) {
        ViewDialog f = new ViewDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        f.setArguments(args);
        return f;
    }

    public interface ViewDialogListener {
        public void onItemClick(DialogFragment dialog, int retVal);
    }

    ViewDialogListener vListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            vListener = (ViewDialogListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ViewDialogListener");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        if (title == null || title.isEmpty()) {
            title = "What would you like to view?";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setItems(Constants.VIEW_OPTIONS, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                vListener.onItemClick(ViewDialog.this, Constants.VIEW_MOVIE);
                                break;
                            case 1:
                                vListener.onItemClick(ViewDialog.this, Constants.VIEW_BOOK);
                                break;
                            default:
                                vListener.onItemClick(ViewDialog.this, Constants.INVALID);
                                break;
                        }
                    }
                });
        return builder.create();
    }
}
