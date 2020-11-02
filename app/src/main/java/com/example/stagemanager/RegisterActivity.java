package com.example.stagemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    EditText registerFullName, registerEmail, registerPassword;
    Button registerSignUp;
    Spinner registerSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        linkResourcesToFields();
        spinnerInit();


    }



    void spinnerInit(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.register_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        registerSpinner.setAdapter(adapter);
    }

    void linkResourcesToFields() {
        registerFullName = findViewById(R.id.registerFullName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerSignUp = findViewById(R.id.registerSignUp);
        registerSpinner = (Spinner) findViewById(R.id.registerSpinner);
    }
}
