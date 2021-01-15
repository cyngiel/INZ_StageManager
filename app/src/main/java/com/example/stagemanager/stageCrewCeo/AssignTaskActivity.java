package com.example.stagemanager.stageCrewCeo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stagemanager.AddParticipantDialog;
import com.example.stagemanager.EditParticipantDialog;
import com.example.stagemanager.R;
import com.example.stagemanager.dynamicViews.DynamicViewsSheetService;
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

public class AssignTaskActivity extends AppCompatActivity implements EditParticipantDialog.EditParticipantDialogListener{

    GridLayout stageDynLayout;
    ProgressBar progressBar1, progressBar2, progressBar3;

    AsyncTask getJsonTask;
    JSONObject jsonObject;
    DynamicViewsSheetService dynamicViewsSheetService;

    String id;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    ArrayList<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            id = b.getString("id");
        }

        linkResourcesToFields();
        getEvent(id);


    }


    private void getEvent(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fStore.collection("Events").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        users = (ArrayList<String>) task.getResult().get("users");
                    }
                });
            }
        }).start();
    }

    private void newJsonTaskFromDB(String name) {
        fStore.collection("Events").document(name).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                List<HashMap<String, String>> list = (List<HashMap<String, String>>) task.getResult().get("inputlist");

                JSONArray array = new JSONArray();
                try {

                    for (int i = 0; i < list.size(); i++) {
                        String ch = Integer.toString(i + 1);
                        String name = list.get(i).get("name");
                        String mic = list.get(i).get("micline");
                        String user = list.get(i).get("user");
                        JSONObject js = new JSONObject();
                        js.put("ch", ch);
                        js.put("name", name);
                        js.put("micline", mic);
                        js.put("user", user);

                        array.put(js);
                    }

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("data", array);
                    returnTaskResult(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AssignTaskActivity.this, "loading inputlist failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void returnTaskResult(JSONObject result) {
        jsonObject = result;
        setProgressBarVis(false);
        dynamicViewsSheetService = new DynamicViewsSheetService(stageDynLayout, getApplicationContext(), jsonObject, getSupportFragmentManager(), id);
        dynamicViewsSheetService.executeExtended();
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
    }

    void firebaseInit() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    @Override
    public void updateView() {
        stageDynLayout.removeAllViews();
        getEvent(id);
    }
}