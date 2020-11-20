package com.example.stagemanager;

public class GlobalValues {

    public static String userLvlStageCrewCode = "3";
    public static String userLvlStageCeoCode = "2";
    public static String userLvlFohProdCode = "1";
    public static String userLvlAdminCode = "0";

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
