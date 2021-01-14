package com.example.stagemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stagemanager.dynamicViews.DynamicView;
import com.example.stagemanager.dynamicViews.DynamicViewButtonListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity implements AddParticipantDialog.AddParticipantDialogListener {

    private EditText createName;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private Button createTextDate, createBtn;
    private FloatingActionButton createFab;
    private GridLayout createGridLayout;
    DynamicView dnv;

    private ArrayList<String> emails;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        linkResourcesToFields();
        floatingButtonListener();
        datePickerInit();
        emails = new ArrayList<>();
        fStore = FirebaseFirestore.getInstance();

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToBase();
            }
        });

    }

    private void writeToBase(int i){
        i += 1;
        DocumentReference df = fStore.collection("Events").document(Integer.toString(i));

        Map<Object, Object> info = new HashMap<>();
        info.put("date", createTextDate.getText().toString());
        info.put("inputlist", emails);
        info.put("name", createName.getText().toString());
        info.put("users", emails);
        df.set(info);

        Toast.makeText(CreateEventActivity.this, "Event Created", Toast.LENGTH_SHORT).show();
    }

    private void connectToBase(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                fStore.collection("Events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int i = task.getResult().size();
                        writeToBase(i);
                    }
                });
            }
        }).start();
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

}