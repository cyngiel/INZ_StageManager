package com.example.stagemanager;

public class GlobalValues {

    public static String userLvlStageCrewCode = "3";
    public static String userLvlStageCeoCode = "2";
    public static String userLvlStageFohProdCode = "1";
    public static String userLvlStageAdminCode = "0";

    public static String getUserLvlCode(String userLvl) {
        if (userLvl.equals("Stage Crew")) {
            return userLvlStageCrewCode;
        } else if (userLvl.equals("Stage CEO")) {
            return userLvlStageCeoCode;
        } else if (userLvl.equals("FOH / PROD")) {
            return userLvlStageFohProdCode;
        } else if (userLvl.equals("Admin")) {
            return userLvlStageAdminCode;
        } else
            return "";
    }

}
