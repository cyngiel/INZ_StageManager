package com.example.stagemanager.stageCrewCeo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stagemanager.event.CreateEventActivity;
import com.example.stagemanager.GlobalValues;
import com.example.stagemanager.LoginActivity;
import com.example.stagemanager.R;
import com.example.stagemanager.dynamicViews.DynamicView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class StageCrewCeoHomeActivity extends AppCompatActivity {

    private Button ceoHomeCreateBtn;
    GridLayout createEventList;
    DynamicView dnV;
    FloatingActionButton stagefab, refreshFab;

    String userEmail;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_crew_ceo_home);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        firebaseInit();
        stagefab = findViewById(R.id.stagefab);
        refreshFab = findViewById(R.id.refreshFab);
        floatingButtonListener();
        ceoHomeCreateBtn = findViewById(R.id.ceoHomeCreateBtn);
        createEventList = findViewById(R.id.createEventList);
        checkEvents();
        ceoHomeCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
                Bundle b = new Bundle();
                b.putString("userEmail", userEmail);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

    }

    private void checkEvents() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fStore.collection("Events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        final int i = task.getResult().size();
                        String uid = fAuth.getUid();
                        fStore.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                String email;
                                email = task.getResult().getString("Email");
                                setUserEmail(email);
                                getUserEvents(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(StageCrewCeoHomeActivity.this, "Your email nope", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //getUserEvents(i);
                        //Toast.makeText(StageCrewCeoHomeActivity.this, "You have: " + i + "active events", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    private void getUserEvents(int size) {
        DocumentReference df;
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                df = getEvent(i);
                addNextEvent(df);
            }
        }

    }

    private DocumentReference getEvent(int id) {
        DocumentReference df = fStore.collection("Events").document(Integer.toString(id));
        return df;
    }

    private void addNextEvent(final DocumentReference df) {
        dnV = new DynamicView(getApplicationContext());

        new Thread(new Runnable() {
            @Override
            public void run() {
                df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        List<String> emails = (List<String>) task.getResult().get("users");

                        if (emails.contains(userEmail)) {

                            String date = task.getResult().getString("date");
                            String name = date + " | " + task.getResult().getString("name");

                            Button removeButton = dnV.removeButton(getApplicationContext());
                            removeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    removeEvent(df.getId());
                                }
                            });
                            createEventList.addView(removeButton, 0);

                            Button event = dnV.eventButton(getApplicationContext(), name);
                            event.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    openEvent(df.getId());
                                }
                            });

                            createEventList.addView(event, 1);
                        }
                    }


                });
            }


        }).start();

    }

    private void openEvent(String id) {
        GlobalValues.subscribeToTopic = GlobalValues.userLvlStageCeoCode + id;
        FirebaseMessaging.getInstance().subscribeToTopic(GlobalValues.subscribeToTopic);
        Intent intent = new Intent(getApplicationContext(), StageCrewCeoMainActivity.class);
        Bundle b = new Bundle();
        b.putString("id", id);
        b.putString("userEmail", userEmail);
        intent.putExtras(b);
        startActivity(intent);
        //Toast.makeText(StageCrewCeoHomeActivity.this, "opening: " + id , Toast.LENGTH_SHORT).show();
    }

    void removeEvent(String id) {
        fStore.collection("Events").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                createEventList.removeAllViews();
                checkEvents();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StageCrewCeoHomeActivity.this, "deleting error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void firebaseInit() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    void floatingButtonListener() {
        stagefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        refreshFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEventList.removeAllViews();
                checkEvents();
            }
        });

    }

    void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}