package com.sdm.mgp2022;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class SaveHighScoreDialogAlert extends  DialogFragment {
    
    public static boolean isShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        isShown = true;
        //Use the builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Would you like to save your score?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GameSystem.Instance.SetIsPaused(true);
                        isShown = false;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User cancelled the pause
                        GameSystem.Instance.SetIsPaused(false);
                        isShown = false;
                    }
                });
        //Create the AlertDialog object and return it
        return builder.create();
    }
}
