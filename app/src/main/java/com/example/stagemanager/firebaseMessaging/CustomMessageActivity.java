package com.example.stagemanager.firebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.stagemanager.GlobalValues;
import com.example.stagemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class CustomMessageActivity extends AppCompatActivity {

    Button customSendBtn;
    EditText customedtMessage, customedtTitle;
    Spinner customSpinner;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_message);

        linkResToFields();
        firebaseInit();
        spinnerInit();

        customSendBtn.setVisibility(View.GONE);
        final DocumentReference df = fStore.collection("Users").document(fAuth.getUid());
        new Thread(new Runnable() {
            @Override
            public void run() {
                df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        String name = task.getResult().getString(GlobalValues.fs_fieldName);
                        customSendBtn.setOnClickListener(new FCMonClickListenerSender(customedtTitle, customedtMessage, name, getApplicationContext(), customSpinner));
                        customSendBtn.setVisibility(View.VISIBLE);
                    }
                });
            }


        }).start();

    }

    void spinnerInit() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.register_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customSpinner.setAdapter(adapter);
    }

    private void linkResToFields() {
        customedtTitle = findViewById(R.id.customedtTitle);
        customedtMessage = findViewById(R.id.customedtMessage);
        customSendBtn = findViewById(R.id.customSendBtn);
        customSpinner = findViewById(R.id.customSpinner);
    }

    void firebaseInit() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }
}