package com.example.stagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stagemanager.admin.AdminMainActivity;
import com.example.stagemanager.foh.FohMainActivity;
import com.example.stagemanager.stageCrew.StageCrewMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button loginSignIn, loginSignUp;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        linkResourcesToFields();
        firebaseInit();

        loginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        loginSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmInput(v);
            }
        });

    }

    void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean validatePassword() {
        String passwordInput = loginPassword.getText().toString();
        if (passwordInput.isEmpty()) {
            loginPassword.setError("Field can't be empty");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = loginEmail.getText().toString();
        if (emailInput.isEmpty()) {
            loginEmail.setError("Field can't be empty");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validatePassword()) {
            return;
        } else {
            loginUser();
        }

//        String input = "Email: " + registerEmailLayout.getEditText().getText().toString();
//        input += "\n";
//        input += "Username: " + registerFullNameLayout.getEditText().getText().toString();
//        input += "\n";
//        input += "Password: " + registerPasswordLayout.getEditText().getText().toString();
//        Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
    }

    void loginUser() {
        fAuth.signInWithEmailAndPassword(loginEmail.getText().toString(), loginPassword.getText().toString()).
                addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        checkUserAccessLvl(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Failed to Sign in", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    void checkUserAccessLvl(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getString("userLvl").equals(GlobalValues.userLvlAdminCode)) {
                    Toast.makeText(LoginActivity.this, "Logged as Admin", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                    finish();
                } else if (documentSnapshot.getString("userLvl").equals(GlobalValues.userLvlFohProdCode)) {
                    Toast.makeText(LoginActivity.this, "Logged as FOH / PROD", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), FohMainActivity.class));
                    finish();
                } else if (documentSnapshot.getString("userLvl").equals(GlobalValues.userLvlStageCeoCode)) {
                    Toast.makeText(LoginActivity.this, "Logged as Stage CEO", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), StageCrewMainActivity.class));
                    finish();
                } else if (documentSnapshot.getString("userLvl").equals(GlobalValues.userLvlStageCrewCode)) {
                    Toast.makeText(LoginActivity.this, "Logged as Stage Crew", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), StageCrewMainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    void firebaseInit() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    void linkResourcesToFields() {
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginSignIn = findViewById(R.id.loginSignIn);
        loginSignUp = findViewById(R.id.loginSignUp);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DocumentReference df = fStore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("userLvl").equals(GlobalValues.userLvlAdminCode)) {
                        Toast.makeText(LoginActivity.this, "Logged as Admin", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AdminMainActivity.class));
                        finish();
                    } else if (documentSnapshot.getString("userLvl").equals(GlobalValues.userLvlFohProdCode)) {
                        Toast.makeText(LoginActivity.this, "Logged as FOH", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), FohMainActivity.class));
                        finish();
                    } else if (documentSnapshot.getString("userLvl").equals(GlobalValues.userLvlStageCrewCode)) {
                        Toast.makeText(LoginActivity.this, "Logged as Stage Crew", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), StageCrewMainActivity.class));
                        finish();
                    } else if (documentSnapshot.getString("userLvl").equals(GlobalValues.userLvlStageCeoCode)) {
                        Toast.makeText(LoginActivity.this, "Logged as Stage CEO", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), StageCrewMainActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }

                }
            });
        }
    }

}
