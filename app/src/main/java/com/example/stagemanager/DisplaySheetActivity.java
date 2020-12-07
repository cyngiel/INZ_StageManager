package com.example.stagemanager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stagemanager.dynamicViews.DynamicViewsSheetService;
import com.example.stagemanager.urlReader.JsonUrlReader;
import com.example.stagemanager.urlReader.JsonUrlReaderTaskResults;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

public class DisplaySheetActivity extends AppCompatActivity implements JsonUrlReaderTaskResults {

    GridLayout stageDynLayout;
    ProgressBar progressBar1, progressBar2, progressBar3;
    Button reloadBtn;
    Spinner dispSheetSpinner;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    AsyncTask getJsonTask;
    JSONObject jsonObject;
    DynamicViewsSheetService dynamicViewsSheetService;
    String bandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sheet);

        linkResourcesToFields();
        spinnerInit();
        setProgressBarVis(true);
        firebaseInit();

        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicViewsSheetService.setVisibilityAll(true);
            }
        });

    }

    @Override
    public void returnTaskResult(JSONObject result) {
        jsonObject = result;
        setProgressBarVis(false);

        dynamicViewsSheetService = new DynamicViewsSheetService(stageDynLayout, getApplicationContext(), jsonObject);
        dynamicViewsSheetService.execute();
    }

    void spinnerInit() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.band_names_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dispSheetSpinner.setAdapter(adapter);

        dispSheetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (dynamicViewsSheetService != null) {
                    dynamicViewsSheetService.setVisibilityAll(false);
                    dynamicViewsSheetService.clearView();
                }
                setProgressBarVis(true);
                bandName = dispSheetSpinner.getSelectedItem().toString();
                getJsonTask = new JsonUrlReader(DisplaySheetActivity.this, bandName).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void setProgressBarVis(boolean isVisible) {
        if (isVisible) {
            progressBar1.setVisibility(View.VISIBLE);
            progressBar2.setVisibility(View.VISIBLE);
            progressBar3.setVisibility(View.VISIBLE);
        } else {
            progressBar1.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
            progressBar3.setVisibility(View.GONE);
        }
    }

    void linkResourcesToFields() {
        dispSheetSpinner = findViewById(R.id.dispSheetSpinner);
        stageDynLayout = findViewById(R.id.stageDynLayout);
        progressBar1 = findViewById(R.id.stageprogressBar1);
        progressBar2 = findViewById(R.id.stageprogressBar2);
        progressBar3 = findViewById(R.id.stageprogressBar3);
        reloadBtn = findViewById(R.id.dispRldBtn);
    }

    void firebaseInit() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }
}