package com.example.weatherbroadcastapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExitDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.network_error_dialog_title))
                .setMessage(getString(R.string.network_error_dialog_body))
                .setPositiveButton(
                        getString(R.string.close_app),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().finishAffinity();
                                System.exit(-1);
                            }
                        }
                );
        return builder.create();
    }
}
