package com.example.stagemanager.stageCrewCeo;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stagemanager.DisplaySheetActivity;
import com.example.stagemanager.GlobalValues;
import com.example.stagemanager.LoginActivity;
import com.example.stagemanager.NextBandActivity;
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

public class StageCrewCeoMainActivity extends AppCompatActivity {

    private FloatingActionButton stagefab;

    Button ceoConfirmStageBtn, ceoConfirmStageAllBtn, ceoNextStageBtn, ceoAbortStageBtn, ceoAbortStageAllBtn, ceoCustomMsgBtn, ceoDispSheet, ceoTimerBtn;
    Chronometer ceoChronometer;

    boolean isCounting;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_crew_ceo_main);

        linkResourcesToFields();
        firebaseInit();
        floatingButtonListener();
        buttonsListeners();

    }


    private void buttonsListeners() {
        ceoConfirmStageAllBtn.setVisibility(View.GONE);
        ceoAbortStageAllBtn.setVisibility(View.GONE);
        ceoConfirmStageBtn.setVisibility(View.GONE);
        ceoAbortStageBtn.setVisibility(View.GONE);
        //ceoNextStageBtn.setVisibility(View.GONE);

        final DocumentReference df = fStore.collection("Users").document(fAuth.getUid());
        new Thread(new Runnable() {
            @Override
            public void run() {
                df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String name = task.getResult().getString(GlobalValues.fs_fieldName);

                        ceoConfirmStageAllBtn.setOnClickListener(new FCMonClickListenerSender("STAGE CREW READY", "from- " + name, getApplicationContext(), GlobalValues.userLvlFohProdCode));
                        ceoConfirmStageAllBtn.setVisibility(View.VISIBLE);

                        ceoConfirmStageBtn.setOnClickListener(new FCMonClickListenerSender("STAGE READY", "You can rest now\nCEO- " + name, getApplicationContext(), GlobalValues.userLvlStageCrewCode));
                        ceoConfirmStageBtn.setVisibility(View.VISIBLE);

                        ceoAbortStageAllBtn.setOnClickListener(new FCMonClickListenerSender("!STAGE CREW EMERGENCY!", "from CEO- " + name, getApplicationContext(), GlobalValues.userLvlFohProdCode));
                        ceoAbortStageAllBtn.setVisibility(View.VISIBLE);

                        ceoAbortStageBtn.setOnClickListener(new FCMonClickListenerSender("!STAGE CREW EMERGENCY!", "Crew to the stage ASAP\nCEO- " + name, getApplicationContext(), GlobalValues.userLvlFohProdCode));
                        ceoAbortStageBtn.setVisibility(View.VISIBLE);
                    }
                });
            }


        }).start();

        ceoCustomMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomMessageActivity.class));
            }
        });

        ceoDispSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DisplaySheetActivity.class));
            }
        });

        ceoTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCounting) {
                    ceoChronometer.stop();
                    ceoTimerBtn.setText("start timer");
                    isCounting = false;

                } else {
                    ceoChronometer.setBase(SystemClock.elapsedRealtime());
                    ceoChronometer.start();
                    ceoTimerBtn.setText("stop timer");
                    isCounting = true;
                }


            }
        });

        ceoNextStageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NextBandActivity.class));
            }
        });
    }

    /////// timer

    public void updateTimer() {

    }

    void initTimer() {

    }

    ////////////// init
    void linkResourcesToFields() {
        stagefab = findViewById(R.id.stagefab);
        ceoNextStageBtn = findViewById(R.id.ceoNextStageBtn);
        ceoAbortStageBtn = findViewById(R.id.ceoAbortStageBtn);
        ceoAbortStageAllBtn = findViewById(R.id.ceoAbortStageAllBtn);
        ceoConfirmStageAllBtn = findViewById(R.id.ceoConfirmStageAllBtn);
        ceoConfirmStageBtn = findViewById(R.id.ceoConfirmStageBtn);
        ceoCustomMsgBtn = findViewById(R.id.ceoCustomMsgBtn);
        ceoDispSheet = findViewById(R.id.ceoDispSheet);
        ceoTimerBtn = findViewById(R.id.ceoTimerBtn);
        ceoChronometer = findViewById(R.id.ceoChronometer);
//        stageNotifyTestBtn = findViewById(R.id.stageNotifyTestBtn);
    }

    void floatingButtonListener() {
        stagefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    void firebaseInit() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

}