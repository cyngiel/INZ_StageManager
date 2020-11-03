package com.example.stagemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout loginEmailLayout, loginPasswordLayout;
    private Button loginSignIn, loginSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linkResourcesToFields();

        loginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

    }

    void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    void linkResourcesToFields() {
        loginEmailLayout = findViewById(R.id.loginEmailLayout);
        loginPasswordLayout = findViewById(R.id.loginPasswordLayout);
        loginSignIn = findViewById(R.id.loginSignIn);
        loginSignUp = findViewById(R.id.loginSignUp);
    }
}
