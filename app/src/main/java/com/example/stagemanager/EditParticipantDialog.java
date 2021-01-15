package com.example.stagemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditParticipantDialog extends AppCompatDialogFragment {

    private EditText addEmailDialog;
    private EditParticipantDialog.EditParticipantDialogListener listener;

    private Spinner editSpinner;
    Context context;

    String id;
    int ch;
    ArrayList<String> emails;

    FirebaseFirestore fStore;

    public EditParticipantDialog(String documentID, int ch, Context context) {
        this.id = documentID;
        this.ch = ch;
        this.context = context;
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
        View view = inflater.inflate(R.layout.edit_participant_dialog, null);

        editSpinner = view.findViewById(R.id.editSpinner);
        fStore = FirebaseFirestore.getInstance();

        getFromBase();

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

    void getUsers(List<String> emails){

        final List<String> spinnerArray = emails;
        this.emails = (ArrayList<String>) emails;

       ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerArray);
    }

    void getFromBase(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                fStore.collection("Events").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        List<String> users = (List<String>) task.getResult().get("users");

                        getUsers(users);
                    }
                });
            }


        }).start();
    }

    public interface EditParticipantDialogListener{
        void updateView();
    }
}
