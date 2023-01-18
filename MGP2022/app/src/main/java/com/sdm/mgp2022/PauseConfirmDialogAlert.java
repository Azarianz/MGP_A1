package com.sdm.mgp2022;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class PauseConfirmDialogAlert extends  DialogFragment {
    
    public static boolean isShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        isShown = true;
        //Use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm Pause?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GameSystem.Instance.SetIsPaused(true);
                        isShown = false;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
