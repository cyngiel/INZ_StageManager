package com.example.stagemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stagemanager.dynamicViews.DynamicViewsLineupService;
import com.example.stagemanager.dynamicViews.DynamicViewsSheetService;
import com.example.stagemanager.urlReader.AsyncUrlReader;

import org.json.JSONException;
import org.json.JSONObject;

public class LineupInfoActivity extends AppCompatActivity {

    Spinner spinner;
    GridLayout lineupDynLayout;
    DynamicViewsLineupService dynamicViewsLineupService;
    ProgressBar progressBar1, progressBar2;

    AsyncUrlReader asyncUrlReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineup_info);


        linkResourcesToFields();
        spinnerInit();
        setProgressBarVis(true);

    }

    public void returnTaskResults(JSONObject jsonObject) {
        setProgressBarVis(false);
        dynamicViewsLineupService = new DynamicViewsLineupService(lineupDynLayout, getApplicationContext(), jsonObject);
        dynamicViewsLineupService.execute();
    }

    void setProgressBarVis(boolean isVisible) {
        if (isVisible) {
            progressBar1.setVisibility(View.VISIBLE);
            progressBar2.setVisibility(View.VISIBLE);
        } else {
            progressBar1.setVisibility(View.GONE);
            progressBar2.setVisibility(View.GONE);
        }
    }

    ////////////////////
    private void linkResourcesToFields() {
        spinner = findViewById(R.id.lineupSpinner);
        lineupDynLayout = findViewById(R.id.lineupDynLayout);
        progressBar1 = findViewById(R.id.lineupProgressBar1);
        progressBar2 = findViewById(R.id.lineupProgressBar2);
    }

    void spinnerInit() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.lineup_day_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (dynamicViewsLineupService != null) {
                    dynamicViewsLineupService.setVisibilityAll(false);
                    dynamicViewsLineupService.clearView();
                }
                setProgressBarVis(true);
                String dayName = spinner.getSelectedItem().toString();

                switch (dayName) {
                    case "Friday":
                        dayName = "fr";
                        executeAsyncreader(dayName);
                        break;
                    case "Saturday am":
                        dayName = "satam";
                        executeAsyncreader(dayName);
                        break;
                    case "Saturday pm":
                        dayName = "satpm";
                        executeAsyncreader(dayName);
                        break;
                    case "Sunday":
                        dayName = "sun";
                        executeAsyncreader(dayName);
                        break;
                    default:

                }

            }

            private void executeAsyncreader(String dayName) {
                asyncUrlReader = new AsyncUrlReader(LineupInfoActivity.this);
                asyncUrlReader.execute(dayName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}