package com.example.stagemanager.firebaseMessaging;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.util.Log;

import com.example.stagemanager.GlobalValues;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirebaseCloudMessagingInstanceIDService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {

        FirebaseMessaging.getInstance().subscribeToTopic(GlobalValues.subscribeToTopic);
    }
}