package com.example.stagemanager;

public class GlobalValues {

    public static final String userLvlStageCrewCode = "3";
    public static final String userLvlStageCeoCode = "2";
    public static final String userLvlFohProdCode = "1";
    public static final String userLvlAdminCode = "0";

    public static String subscribeToTopic;

    public static final String fcm_API = "https://fcm.googleapis.com/fcm/send";
    public static final String fcm_serverKey = "key=" + "AAAAkpuO7oQ:APA91bGQobnVJ6hnaFxCgbdlt8-0AT1Q4IxCKz_-YjMz1IacXQIRgp8AWDFMwl8pwhrNGYLp2zu9-Hvf_KY9Duf3EIfLZ1WrvNOIJ6cOzJLi_-QcBl62IvDxBvbvxg0CcFE8B2QGPU3V";
    public static final String fcm_contentType = "application/json";

    public static final String fs_fieldName = "FullName";
    public static final String fs_fieldEmail = "Email";

    public static String getUserLvlCode(String userLvl) {
        if (userLvl.equals("Stage Crew")) {
            return userLvlStageCrewCode;
        } else if (userLvl.equals("Stage CEO")) {
            return userLvlStageCeoCode;
        } else if (userLvl.equals("FOH / PROD")) {
            return userLvlFohProdCode;
        } else if (userLvl.equals("Admin")) {
            return userLvlAdminCode;
        } else
            return "";
    }

}
