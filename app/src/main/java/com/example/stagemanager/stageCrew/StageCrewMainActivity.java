package com.example.stagemanager.stageCrew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.stagemanager.LoginActivity;
import com.example.stagemanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class StageCrewMainActivity extends AppCompatActivity {

    private FloatingActionButton stagefab;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_crew_main);

        linkResourcesToFields();
        firebaseInit();
        floatingButtonListener();

    }

    void floatingButtonListener(){
        FloatingActionButton fab = findViewById(R.id.stagefab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }

    void getFromGoogle() {


    }

    ////////////// init
    void linkResourcesToFields(){
        stagefab = findViewById(R.id.stagefab);
    }

    void firebaseInit() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }
}