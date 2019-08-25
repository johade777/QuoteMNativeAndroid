package com.johade.quotem.Views_Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.johade.quotem.listeners.ConfirmDialogListener;

public class ConfirmDialog extends DialogFragment {
    private ConfirmDialogListener listener;
    private String message;

    public ConfirmDialog(String message){
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete Quiz?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDialogConfirm(ConfirmDialog.this);
                    }
                })
                .setMessage("Delete quiz " + message + "?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDialogCancel(ConfirmDialog.this);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ConfirmDialogListener) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + " Must Implement ConfirmDialogListener");
        }
    }


}
