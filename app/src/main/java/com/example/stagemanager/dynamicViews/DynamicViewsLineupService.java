package com.example.stagemanager.dynamicViews;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DynamicViewsLineupService {

    GridLayout layout;
    Context context;
    DynamicView dnV;
    JSONObject jsonObject;

    ArrayList<TextView> times, names;

    public DynamicViewsLineupService(GridLayout layout, Context context, JSONObject jsonObject) {
        this.layout = layout;
        this.context = context;
        this.jsonObject = jsonObject;

        times = new ArrayList<>();
        names = new ArrayList<>();
    }

    private void dispTasks() {
        try {
            JSONArray array = jsonObject.getJSONArray("schedule");
            if (array.length() > 0) {
                for (int i = array.length() - 1; i >= 0; i--) {
                    addNextTaskLabel(array.getJSONObject(i));
                }
            } else
                Toast.makeText(context, "There is no schedule", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addNextTaskLabel(final JSONObject jsonObject) throws JSONException {
        dnV = new DynamicView(context);
        TextView timeTextView = dnV.chTextView(context, jsonObject.getString("time"));
        layout.addView(timeTextView, 2);
        times.add(timeTextView);

        TextView nameTextView = dnV.nameTextView(context, jsonObject.getString("name"));
        layout.addView(nameTextView, 3);
        names.add(nameTextView);

    }

    public void setVisibilityAll(boolean isVisible) {
        if (isVisible)
            for (int i = 0; i < times.size(); i++) {
                times.get(i).setVisibility(View.VISIBLE);
                names.get(i).setVisibility(View.VISIBLE);
            }
        else
            for (int i = 0; i < times.size(); i++) {
                times.get(i).setVisibility(View.GONE);
                names.get(i).setVisibility(View.GONE);
            }
    }

    public void execute() {
        dispTasks();
    }

    public void clearView(){
        times.removeAll(times);
        names.removeAll(names);
    }
}
