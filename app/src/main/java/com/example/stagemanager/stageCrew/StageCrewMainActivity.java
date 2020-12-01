package com.example.stagemanager.stageCrew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.stagemanager.LoginActivity;
import com.example.stagemanager.R;
import com.example.stagemanager.notifications.Token;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

public class StageCrewMainActivity extends AppCompatActivity {

    private FloatingActionButton stagefab;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    String mUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_crew_main);

        linkResourcesToFields();
        firebaseInit();
        floatingButtonListener();

        mUID = fAuth.getCurrentUser().getUid();
        saveUIDinSharedPreferences();
        updateToken(FirebaseInstanceId.getInstance().getToken());
    }

    void floatingButtonListener(){
        stagefab.setOnClickListener(new View.OnClickListener() {
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

    //////////// notifications
    public void updateToken(String token){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken = new Token(token);
        ref.child(mUID).setValue(mToken);
    }

    void saveUIDinSharedPreferences(){
        SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Current_USERID", mUID);
        editor.apply();
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