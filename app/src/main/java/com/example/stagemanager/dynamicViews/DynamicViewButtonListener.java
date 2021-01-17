package com.example.stagemanager.dynamicViews;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DynamicViewButtonListener implements View.OnClickListener {

    TextView chTextView, nameTextView, micTestView;
    Button button;
    ArrayList<String> list;
    boolean ishidebutton;

    public DynamicViewButtonListener(TextView chTextView, TextView nameTextView, TextView micTestView, Button button) {
        this.chTextView = chTextView;
        this.nameTextView = nameTextView;
        this.micTestView = micTestView;
        this.button = button;
        ishidebutton = true;
    }

    public DynamicViewButtonListener(TextView nameTextView, Button button, ArrayList<String> list) {
        this.nameTextView = nameTextView;
        this.button = button;
        ishidebutton = false;
        this.list = list;
    }

    @Override
    public void onClick(View v) {

        if(ishidebutton) {
            chTextView.setVisibility(View.GONE);
            nameTextView.setVisibility(View.GONE);
            micTestView.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
        else if (ishidebutton == false){
            list.remove(nameTextView.getText().toString());
            nameTextView.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
    }
}
