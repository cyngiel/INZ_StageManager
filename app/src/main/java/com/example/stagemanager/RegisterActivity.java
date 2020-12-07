package com.example.stagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerFullName, registerEmail, registerPassword;
    private Button registerSignUp;
    private Spinner registerSpinner;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        linkResourcesToFields();
        spinnerInit();
        firebaseInit();

        registerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButtonCreateAccountHandle(v);
            }
        });
    }

    // validation & register ////////////////////////////////////////////////////
    public void registerButtonCreateAccountHandle(View v) {
        confirmInput(v);
    }

    private boolean validateEmail() {
        String emailInput = registerEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            registerEmail.setError("Field can't be empty");
            return false;
        } else if (!emailInput.contains("@") || !emailInput.contains(".")) {
            registerEmail.setError("Invalid e-mail");
            return false;
        } else {
            registerEmail.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = registerFullName.getText().toString().trim();
        if (usernameInput.isEmpty()) {
            registerFullName.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            registerFullName.setError("Username too long");
            return false;
        } else {
            registerFullName.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = registerPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            registerPassword.setError("Field can't be empty");
            return false;
        } else if (passwordInput.length() < 6) {
            registerPassword.setError("Password too short");
            return false;
        } else {
            registerPassword.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validateUsername() | !validatePassword()) {
            return;
        } else {
            createUser();
        }

//        String input = "Email: " + registerEmailLayout.getEditText().getText().toString();
//        input += "\n";
//        input += "Username: " + registerFullNameLayout.getEditText().getText().toString();
//        input += "\n";
//        input += "Password: " + registerPasswordLayout.getEditText().getText().toString();
//        Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
    }

    // create user
    private void createUser() {
        fAuth.createUserWithEmailAndPassword(registerEmail.getText().toString(), registerPassword.getText().toString()).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = fAuth.getCurrentUser();
                        Toast.makeText(RegisterActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        DocumentReference df = fStore.collection("Users").document(user.getUid());
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("FullName", registerFullName.getText().toString());
                        userInfo.put("Email", registerEmail.getText().toString());
                        userInfo.put("Password", registerPassword.getText().toString());
                        userInfo.put("userLvl", GlobalValues.getUserLvlCode(registerSpinner.getSelectedItem().toString()));
                        //fStore.collection("Users").add(userInfo);
                        df.set(userInfo);

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

    }

// on start init //////////////////////////////////////////////////////////////

    void spinnerInit() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.register_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        registerSpinner.setAdapter(adapter);
    }

    void firebaseInit() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    void linkResourcesToFields() {
        registerFullName = findViewById(R.id.registerFullName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerSignUp = findViewById(R.id.registerSignUp);
        registerSpinner = findViewById(R.id.registerSpinner);
    }

}
