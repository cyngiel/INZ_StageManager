package com.example.stagemanager.foh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stagemanager.DisplaySheetActivity;
import com.example.stagemanager.GlobalValues;
import com.example.stagemanager.LoginActivity;
import com.example.stagemanager.R;
import com.example.stagemanager.firebaseMessaging.CustomMessageActivity;
import com.example.stagemanager.firebaseMessaging.FCMonClickListenerSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FohMainActivity extends AppCompatActivity {

    private FloatingActionButton fohfab;
    private Button fohConfirmFohAllBtn, fohAbortAllBtn, fohCustomMsgBtn, fohDispSheet;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foh_main);
        linkResourcesToFields();
        firebaseInit();
        floatingButtonListener();
        buttonsListeners();

    }

    private void buttonsListeners() {
        fohConfirmFohAllBtn.setVisibility(View.GONE);
        fohAbortAllBtn.setVisibility(View.GONE);

        final DocumentReference df = fStore.collection("Users").document(fAuth.getUid());
        new Thread(new Runnable() {
            @Override
            public void run() {
                df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String name = task.getResult().getString(GlobalValues.fs_fieldName);

                        fohConfirmFohAllBtn.setOnClickListener(new FCMonClickListenerSender("FOH READY", "from: " + name, getApplicationContext(), GlobalValues.userLvlFohProdCode, GlobalValues.userLvlStageCeoCode, GlobalValues.userLvlStageCrewCode));
                        fohConfirmFohAllBtn.setVisibility(View.VISIBLE);

                        fohAbortAllBtn.setOnClickListener(new FCMonClickListenerSender("!FOH / PROD EMERGENCY!", "from: " + name, getApplicationContext(), GlobalValues.userLvlFohProdCode, GlobalValues.userLvlStageCeoCode, GlobalValues.userLvlStageCrewCode));
                        fohAbortAllBtn.setVisibility(View.VISIBLE);

                    }
                });
            }


        }).start();

        fohCustomMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomMessageActivity.class));
            }
        });

        fohDispSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DisplaySheetActivity.class));
            }
        });

    }


    void floatingButtonListener() {
        FloatingActionButton fab = findViewById(R.id.fohfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    void linkResourcesToFields() {
        fohfab = findViewById(R.id.fohfab);
        fohCustomMsgBtn = findViewById(R.id.fohCustomMsgBtn);
        fohAbortAllBtn = findViewById(R.id.fohAbortAllBtn);
        fohConfirmFohAllBtn = findViewById(R.id.fohConfirmFohAllBtn);
        fohDispSheet = findViewById(R.id.fohDispSheet);
    }

    void firebaseInit() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }
}