package com.example.stagemanager.urlReader;

public class ListRow {

    //protected Map<String, String> row;
    String ch, name, micline, user;

    public ListRow(String ch, String name, String micline) {
        /*row = new HashMap<>();
        row.put("ch", ch);
        row.put("name", name);
        row.put("mic", mic);*/
        this.ch = ch;
        this.micline = micline;
        this.name = name;
        this.user = "none";
    }

    public ListRow(String ch, String name, String micline, String user) {
        /*row = new HashMap<>();
        row.put("ch", ch);
        row.put("name", name);
        row.put("mic", mic);*/
        this.ch = ch;
        this.micline = micline;
        this.name = name;
        this.user = user;
    }

    public String getCh() {
        return ch;
    }

    public String getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getMicline() {
        return micline;
    }

    /*public String getCh(){
        return row.get("ch");
    }

    public String getNamee(){
        return row.get("name");
    }

    public String getMic(){
        return row.get("mic");
    }*/
}
