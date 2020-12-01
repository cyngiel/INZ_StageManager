package com.example.stagemanager.stageCrew;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stagemanager.DynamicViewsSheetService;
import com.example.stagemanager.JsonUrlReader;
import com.example.stagemanager.LoginActivity;
import com.example.stagemanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

public class StageCrewMainActivity extends AppCompatActivity {

    private FloatingActionButton stagefab;
    GridLayout stageDynLayout;
    ProgressBar progressBar1, progressBar2, progressBar3;
    Button reloadBtn;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    AsyncTask getJsonTask;
    JSONObject jsonObject;
    DynamicViewsSheetService dynamicViewsSheetService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_crew_main);

        getJsonTask = new JsonUrlReader(this).execute();

        linkResourcesToFields();
        setProgressBarVis(true);
        firebaseInit();
        floatingButtonListener();

        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicViewsSheetService.setVisibilityAll(true);
            }
        });

    }

    public void returnTaskResult(JSONObject result) {
        jsonObject = result;
        setProgressBarVis(false);
        dynamicViewsSheetService = new DynamicViewsSheetService(stageDynLayout, getApplicationContext(), jsonObject);
        dynamicViewsSheetService.execute();
    }

    void setProgressBarVis(boolean isVisible) {
        if(isVisible){
            progressBar1.setVisibility(View.VISIBLE);
            progressBar2.setVisibility(View.VISIBLE);
            progressBar3.setVisibility(View.VISIBLE);
        }
        else {
            progressBar1.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            progressBar3.setVisibility(View.GONE);
        }
    }

    ////////////// init
    void linkResourcesToFields() {
        stagefab = findViewById(R.id.stagefab);
        stageDynLayout = findViewById(R.id.stageDynLayout);
        progressBar1 = findViewById(R.id.stageprogressBar1);
        progressBar2 = findViewById(R.id.stageprogressBar2);
        progressBar3 = findViewById(R.id.stageprogressBar3);
        reloadBtn = findViewById(R.id.stageRldBtn);
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