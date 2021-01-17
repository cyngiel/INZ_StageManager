package com.example.stagemanager.dynamicViews;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.stagemanager.stageCrewCeo.AssignParticipantDialog;

public class DynamicViewEditTaskButtonListener implements View.OnClickListener {

    String documentID;
    int i;
    Context context;
    FragmentManager fragmentManager;

    public DynamicViewEditTaskButtonListener(String documentID, int i, Context context, FragmentManager fragmentManager) {
        this.documentID = documentID;
        this.i = i;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onClick(View v) {
        AssignParticipantDialog AssignParticipantDialog = new AssignParticipantDialog(documentID, i, context);
        AssignParticipantDialog.show(fragmentManager, "EditParticipantDialog");
    }
}
