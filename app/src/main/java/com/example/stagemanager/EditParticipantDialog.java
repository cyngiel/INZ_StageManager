package com.example.stagemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditParticipantDialog extends AppCompatDialogFragment {

    private EditText addEmailDialog;
    private EditParticipantDialog.EditParticipantDialogListener listener;

    String documentID;
    int ch;

    public EditParticipantDialog(String documentID, int ch) {
        this.documentID = documentID;
        this.ch = ch;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (EditParticipantDialog.EditParticipantDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_participant_dialog, null);

        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        writeToBase();
                        listener.updateView();
                    }
                });

        addEmailDialog = view.findViewById(R.id.addEmailDialog);
        return builder.create();
    }

    void writeToBase(){

    }

    public interface EditParticipantDialogListener{
        void updateView();
    }
}
