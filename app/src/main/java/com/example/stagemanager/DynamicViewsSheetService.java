package com.example.stagemanager;

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

public class DynamicViewsSheetService {

    GridLayout layout;
    Context context;
    DynamicViews dnV;
    JSONObject jsonObject;

    ArrayList<TextView> chs, names, micLines;
    ArrayList<Button> buttons;

    public DynamicViewsSheetService(GridLayout layout, Context context, JSONObject jsonObject) {
        this.layout = layout;
        this.context = context;
        this.jsonObject = jsonObject;

        chs = new ArrayList<>();
        names = new ArrayList<>();
        micLines = new ArrayList<>();
        buttons = new ArrayList<>();
    }

    private void dispTasks() {
        try {
            JSONArray array = jsonObject.getJSONArray("data");
            if (array.length() > 0) {
                for (int i = array.length() - 1; i >= 0; i--) {
                    addNextTaskLabel(array.getJSONObject(i));
                }
            } else
                Toast.makeText(context, "There is no tasks", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addNextTaskLabel(final JSONObject jsonObject) throws JSONException {
        dnV = new DynamicViews(context);
        TextView chTextView = dnV.chTextView(context, jsonObject.getString("ch"));
        layout.addView(chTextView, 4);
        chs.add(chTextView);

        TextView nameTextView = dnV.nameTextView(context, jsonObject.getString("name"));
        layout.addView(nameTextView, 5);
        names.add(nameTextView);

        TextView micTextView = dnV.chTextView(context, jsonObject.getString("micline"));
        layout.addView(micTextView, 6);
        micLines.add(micTextView);

        Button button = dnV.hideButton(context);
        button.setOnClickListener(new DynamicViewButtonListener(chTextView, nameTextView, micTextView, button));
        layout.addView(button, 7);
        buttons.add(button);

    }

    public void setVisibilityAll(boolean isVisible) {
        if (isVisible)
            for (int i = 0; i < chs.size(); i++) {
                chs.get(i).setVisibility(View.VISIBLE);
                names.get(i).setVisibility(View.VISIBLE);
                micLines.get(i).setVisibility(View.VISIBLE);
                buttons.get(i).setVisibility(View.VISIBLE);
            }
        else
            for (int i = 0; i < chs.size(); i++) {
                chs.get(i).setVisibility(View.GONE);
                names.get(i).setVisibility(View.GONE);
                micLines.get(i).setVisibility(View.GONE);
                buttons.get(i).setVisibility(View.GONE);
            }
    }

    public void execute() {
        dispTasks();
    }

    public void executeUpdate() {
        dispTasks();
    }
}
