package com.example.stagemanager.firebaseMessaging;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.stagemanager.GlobalValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCMonClickListenerSender implements View.OnClickListener {

    String topic[], title, message, name;
    Context context;
    String TAG = "fcm";
    EditText titleEdit, messageEdit;
    Spinner spinner;
    boolean getFromEditText, nextBand;

    public FCMonClickListenerSender(String title, String message, Context context, String... topics) {
        topic = new String[topics.length];
        for (int i = 0; i < topics.length; i++) {
            this.topic[i] = "/topics/" + topics[i];
        }

        this.title = title;
        this.message = message;
        this.context = context;

        getFromEditText = false;
        nextBand = false;
    }

    public FCMonClickListenerSender(EditText title, EditText message, String name, Context context, Spinner spinner) {
        this.spinner = spinner;
        this.name = name;
        this.context = context;
        this.messageEdit = message;
        this.titleEdit = title;
        topic = new String[1];
        topic[0] = "";

        getFromEditText = true;
        nextBand = false;
    }

    public FCMonClickListenerSender(Context context, Spinner spinner, String... topics) {
        this.spinner = spinner;
        this.context = context;
        topic = new String[topics.length];
        for (int i = 0; i < topics.length; i++) {
            this.topic[i] = "/topics/" + topics[i];
        }

        nextBand = true;
        getFromEditText = false;
    }

    void send(int i) throws JSONException {
        JSONObject notification = new JSONObject();
        JSONObject notificationBody = new JSONObject();

        if (getFromEditText) {
            notificationBody.put("title", titleEdit.getText().toString());
            notificationBody.put("message", messageEdit.getText().toString() + "\nFrom: " + name);
            topic[0] = "/topics/" + GlobalValues.getUserLvlCode(spinner.getSelectedItem().toString());
            titleEdit.setText("");
            messageEdit.setText("");
        } else if (nextBand) {
            notificationBody.put("title", "nextband");
            notificationBody.put("message", spinner.getSelectedItem().toString());

        } else {
            notificationBody.put("title", title);
            notificationBody.put("message", message);
        }

        notification.put("to", topic[i]);
        notification.put("data", notificationBody);

        sendNotification(notification);
    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GlobalValues.fcm_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", GlobalValues.fcm_serverKey);
                params.put("Content-Type", GlobalValues.fcm_contentType);
                return params;
            }
        };
        FirebaseCloudMessagingSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        try {
            for (int i = 0; i < topic.length; i++) {
                send(i);
            }
            Toast.makeText(context, "message sent", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(context, "message error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
