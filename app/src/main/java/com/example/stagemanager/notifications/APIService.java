package com.example.stagemanager.notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAkpuO7oQ:APA91bGQobnVJ6hnaFxCgbdlt8-0AT1Q4IxCKz_-YjMz1IacXQIRgp8AWDFMwl8pwhrNGYLp2zu9-Hvf_KY9Duf3EIfLZ1WrvNOIJ6cOzJLi_-QcBl62IvDxBvbvxg0CcFE8B2QGPU3V"
    })

    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
