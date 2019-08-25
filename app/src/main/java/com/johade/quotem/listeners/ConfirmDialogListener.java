package com.johade.quotem.listeners;

import androidx.fragment.app.DialogFragment;

public interface ConfirmDialogListener {
    void onDialogConfirm(DialogFragment dialog);
    void onDialogCancel(DialogFragment dialog);
}
