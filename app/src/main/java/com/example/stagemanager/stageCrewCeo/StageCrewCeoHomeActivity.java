package com.example.stagemanager.stageCrewCeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.stagemanager.CreateEventActivity;
import com.example.stagemanager.R;
import com.example.stagemanager.foh.FohMainActivity;

public class StageCrewCeoHomeActivity extends AppCompatActivity {

    private Button ceoHomeCreateBtn, ceoHomeStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_crew_ceo_home);

        ceoHomeCreateBtn = findViewById(R.id.ceoHomeCreateBtn);
        ceoHomeStartBtn = findViewById(R.id.ceoHomeStartBtn);

        ceoHomeCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateEventActivity.class));
            }
        });

        ceoHomeStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StageCrewCeoMainActivity.class));
            }
        });
    }
}