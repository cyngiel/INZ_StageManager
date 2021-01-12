package com.example.stagemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity implements AddParticipantDialog.AddParticipantDialogListener {

    private EditText createName;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private Button createTextDate, createBtn;
    private FloatingActionButton createFab;
    private GridLayout createGridLayout;
    private ArrayList<String> emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        linkResourcesToFields();
        floatingButtonListener();
        datePickerInit();
        emails = new ArrayList<>();

    }

    private void openDialog() {
        AddParticipantDialog addParticipantDialog = new AddParticipantDialog();
        addParticipantDialog.show(getSupportFragmentManager(), "AddParticipantDialog");
    }

    @Override
    public void getInputText(String email) {
        emails.add(email);
        updateDynLayout();
    }

    private void updateDynLayout() {

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