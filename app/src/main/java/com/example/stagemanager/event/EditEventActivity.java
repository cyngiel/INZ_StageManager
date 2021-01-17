package com.example.stagemanager.event;

import android.app.DatePickerDialog;
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
import com.example.stagemanager.urlReader.JsonUrlReaderTaskResults;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class EditEventActivity extends AppCompatActivity implements AddParticipantDialog.AddParticipantDialogListener, JsonUrlReaderTaskResults {

    private EditText createName;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private Button createTextDate, createBtn;
    private FloatingActionButton createFab;
    private GridLayout createGridLayout;
    DynamicView dnv;

    AsyncTask getJsonTask;
    private ArrayList<String> emails;
    FirebaseFirestore fStore;
    String id, userEmail, eventName, eventDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_event);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            //Toast.makeText(StageCrewMainActivity.this, b.getString("name"), Toast.LENGTH_SHORT).show();
            //newJsonTask(b.getString("name")); old version
            userEmail = b.getString("userEmail");
            id = b.getString("id");
        }

        linkResourcesToFields();
        floatingButtonListener();
        datePickerInit();
        emails = new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();

        loadData();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditEventActivity.this, "Updating, please wait...", Toast.LENGTH_SHORT).show();
                writeToBase();
            }
        });

    }

    private void loadData() {

        fStore.collection("Events").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                eventName = documentSnapshot.getString("name");
                eventDate = documentSnapshot.getString("date");
                emails = (ArrayList<String>) documentSnapshot.get("users");
                setFields();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditEventActivity.this, "Error", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void setFields() {
        createName.setText(eventName);
        createTextDate.setText(eventDate);

        for (int i = 0; i < emails.size(); i++) {
            String email = emails.get(i);
            if (email.equals(userEmail))
                continue;
            updateDynLayout(email);
        }

    }

    private void writeToBase(){

        try {
            fStore.collection("Events").document(id).update("name", createName.getText().toString());
            fStore.collection("Events").document(id).update("date", createTextDate.getText().toString());
            fStore.collection("Events").document(id).update("users", emails);

            Toast.makeText(EditEventActivity.this, "Event updated", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            writeToBase(false);
        }

    }

    private void writeToBase(boolean i) {

        Toast.makeText(EditEventActivity.this, "Ops, failed to update", Toast.LENGTH_SHORT).show();
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
    }

    private void datePickerInit() {
        createTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(EditEventActivity.this, new DatePickerDialog.OnDateSetListener() {
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
            writeToBase();
        } catch (Exception e) {
            e.printStackTrace();
            writeToBase(false);
        }
    }
}