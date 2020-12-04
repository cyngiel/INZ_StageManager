package com.example.stagemanager.firebaseMessaging;

import android.content.Context;
import android.util.Log;
import android.view.View;
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

    String topic, title, message;
    Context context;
    String TAG = "fcm";

    public FCMonClickListenerSender(String topic, String title, String message, Context context) {
        this.topic = "/topics/" + topic;
        this.title = title;
        this.message = message;
        this.context = context;
    }

    void send() throws JSONException {
        JSONObject notification = new JSONObject();
        JSONObject notificationBody = new JSONObject();
        notificationBody.put("title", title);
        notificationBody.put("message", message);

        notification.put("to", topic);
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
            send();
            Toast.makeText(context, "Confirmation sent", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            Toast.makeText(context, "notification error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
