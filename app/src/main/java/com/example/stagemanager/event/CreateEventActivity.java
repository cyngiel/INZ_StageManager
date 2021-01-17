package com.example.stagemanager.event;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stagemanager.R;
import com.example.stagemanager.dynamicViews.DynamicView;
import com.example.stagemanager.dynamicViews.DynamicViewButtonListener;
import com.example.stagemanager.stageCrewCeo.StageCrewCeoHomeActivity;
import com.example.stagemanager.urlReader.JsonPathUrlReader;
import com.example.stagemanager.urlReader.JsonUrlReaderTaskResults;
import com.example.stagemanager.urlReader.ListRow;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity implements AddParticipantDialog.AddParticipantDialogListener, JsonUrlReaderTaskResults {

    private EditText createName, createUrl;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private Button createTextDate, createBtn;
    private FloatingActionButton createFab;
    private GridLayout createGridLayout;
    DynamicView dnv;
    int eventsSize;

    AsyncTask getJsonTask;
    private ArrayList<String> emails;
    FirebaseFirestore fStore;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            //Toast.makeText(StageCrewMainActivity.this, b.getString("name"), Toast.LENGTH_SHORT).show();
            //newJsonTask(b.getString("name")); old version
            userEmail = b.getString("userEmail");
        }

        linkResourcesToFields();
        floatingButtonListener();
        datePickerInit();
        emails = new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateEventActivity.this, "Creating, please wait...", Toast.LENGTH_SHORT).show();
                connectToBase();
            }
        });

    }

    private ArrayList<ListRow> jsonToArray(JSONObject jsonObject) throws JSONException {
        ArrayList<ListRow> list = new ArrayList<>();
        JSONArray array = jsonObject.getJSONArray("data");
        JSONObject tempObject;

        for (int i = array.length() - 1; i >= 0; i--) {
            tempObject = array.getJSONObject(i);

            String ch = tempObject.getString("ch");
            String name = tempObject.getString("name");
            String micline = tempObject.getString("micline");

            list.add(new ListRow(ch, name, micline));
        }

        return list;
    }

    private void writeToBase(int i, JSONObject jsonObject) throws JSONException {
        DocumentReference df = fStore.collection("Events").document(Integer.toString(i));

        emails.add(userEmail);

        ArrayList<ListRow> list = jsonToArray(jsonObject);

        Map<Object, Object> info = new HashMap<>();
        info.put("name", createName.getText().toString());
        info.put("date", createTextDate.getText().toString());
        info.put("inputlist", list);
        info.put("users", emails);
         df.set(info).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CreateEventActivity.this, "Event Created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), StageCrewCeoHomeActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateEventActivity.this, "failed to create event, DB error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void writeToBase(boolean i) {

        Toast.makeText(CreateEventActivity.this, "Ops, failed to create event", Toast.LENGTH_SHORT).show();
    }

    private void connectToBase() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                fStore.collection("Events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int i = task.getResult().size();
                        eventsSize = i;
                        getJson();
                    }
                });
            }
        }).start();

    }

    private void getJson(){
        String path = "https://script.google.com/macros/s/AKfycby8sDbdO4cgHdU7kayugGoavU2CoSxdzOsqW5rWEnsfsvXPtrz8r3mh/exec?theArg=" + createUrl.getText();
        getJsonTask = new JsonPathUrlReader(CreateEventActivity.this, path).execute();
    }

    private void openDialog() {
        AddParticipantDialog addParticipantDialog = new AddParticipantDialog();
        addParticipantDialog.show(getSupportFragmentManager(), "AddParticipantDialog");
    }

    @Override
    public void getInputText(String email) {
        emails.add(email);
        updateDynLayout(email);

    }

    private void updateDynLayout(String s) {
        dnv = new DynamicView(getApplicationContext());

        TextView nameTextView = dnv.nameTextView(getApplicationContext(), s);

        Button button = dnv.removeButton(getApplicationContext());
        button.setOnClickListener(new DynamicViewButtonListener(nameTextView, button, emails));

        createGridLayout.addView(button, 0);
        createGridLayout.addView(nameTextView, 1);
    }

    ///////////////////////////////////////////////////////////////

    private void linkResourcesToFields() {
        createName = findViewById(R.id.createName);
        createTextDate = findViewById(R.id.createTextDate);
        createGridLayout = findViewById(R.id.createGridLayout);
        createFab = findViewById(R.id.createFab);
        createBtn = findViewById(R.id.createBtn);
        createUrl = findViewById(R.id.createUrl);
    }

    private void datePickerInit() {
        createTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(CreateEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
                        createTextDate.setText(mdayOfMonth + "." + mmonth + "." + myear);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });
    }

    void floatingButtonListener() {
        createFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    @Override
    public void returnTaskResult(JSONObject result) {
        try {
            writeToBase(eventsSize, result);
        } catch (JSONException e) {
            e.printStackTrace();
            writeToBase(false);
        }
    }
}