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
    public static final int QUIZ = 0;
    public static final int QUESTION = 1;
    public static enum AlertType{
        QUIZ,
        QUESTION;
    }
    private ConfirmDialogListener listener;
    private String message;
    private AlertType type;

    public ConfirmDialog(String message, AlertType type){
        this.message = message;
        this.type = type;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String alertType;
        if(this.type == AlertType.QUESTION){
            alertType = "Question";
        }else{
            alertType = "Quiz";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete " + alertType + "?")
                .setPositiveButton("Delete", (dialogInterface, i) -> listener.onDialogConfirm(ConfirmDialog.this))
                .setMessage("Delete " + alertType + " " + message + "?")
                .setNegativeButton("Cancel", (dialogInterface, i) -> listener.onDialogCancel(ConfirmDialog.this));
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
