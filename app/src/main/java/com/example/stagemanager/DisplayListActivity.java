package com.example.stagemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.stagemanager.dynamicViews.DynamicViewsSheetService;
import com.example.stagemanager.stageCrewCeo.AssignTaskActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayListActivity extends AppCompatActivity {

    GridLayout assaignGrid;
    ProgressBar progressBar1, progressBar2, progressBar3;

    AsyncTask getJsonTask;
    JSONObject jsonObject;
    DynamicViewsSheetService dynamicViewsSheetService;

    String id;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        firebaseInit();
        linkResourcesToFields();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            id = b.getString("id");
            newJsonTaskFromDB(id);
        }
    }

    private void newJsonTaskFromDB(String id) {
        fStore.collection("Events").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                List<HashMap<String, String>> list = (List<HashMap<String, String>>) task.getResult().get("inputlist");

                JSONArray array = new JSONArray();
                try {

                    for (int i = 0; i < list.size(); i++) {
                        String ch = Integer.toString(i + 1);
                        String name = list.get(i).get("name");
                        String mic = list.get(i).get("micline");
                        JSONObject js = new JSONObject();
                        js.put("ch", ch);
                        js.put("name", name);
                        js.put("micline", mic);

                        array.put(js);
                    }

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("data", array);
                    returnTaskResult(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(DisplayListActivity.this, "loading inputlist failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void returnTaskResult(JSONObject result) {
        jsonObject = result;
        setProgressBarVis(false);
        dynamicViewsSheetService = new DynamicViewsSheetService(assaignGrid, getApplicationContext(), jsonObject);
        dynamicViewsSheetService.executeMinus();
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

    private void linkResourcesToFields() {
        progressBar1 = findViewById(R.id.assignProgressBar1);
        progressBar2 = findViewById(R.id.assignProgressBar2);
        progressBar3 = findViewById(R.id.assignProgressBar3);
        assaignGrid = findViewById(R.id.assaignGrid);
    }

    void firebaseInit() {
        fStore = FirebaseFirestore.getInstance();
    }
}