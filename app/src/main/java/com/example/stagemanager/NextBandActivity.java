package com.example.stagemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.stagemanager.firebaseMessaging.FCMonClickListenerSender;

public class NextBandActivity extends AppCompatActivity {

    Spinner nextBandSpinner;
    Button nextBandSendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_band);

        linkResToFields();
        spinnerInit();

        nextBandSendBtn.setOnClickListener(new FCMonClickListenerSender(getApplicationContext(), nextBandSpinner, GlobalValues.userLvlStageCrewCode, GlobalValues.userLvlFohProdCode));
    }

    private void linkResToFields() {
        nextBandSpinner = findViewById(R.id.nextBandSpinner);
        nextBandSendBtn = findViewById(R.id.nextBandSendBtn);
    }

    void spinnerInit() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.band_names_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nextBandSpinner.setAdapter(adapter);
    }
}