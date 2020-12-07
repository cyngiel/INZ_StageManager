package com.example.stagemanager.dynamicViews;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DynamicViewButtonListener implements View.OnClickListener {

    TextView chTextView, nameTextView, micTestView;
    Button button;

    public DynamicViewButtonListener(TextView chTextView, TextView nameTextView, TextView micTestView, Button button) {
        this.chTextView = chTextView;
        this.nameTextView = nameTextView;
        this.micTestView = micTestView;
        this.button = button;
    }

    @Override
    public void onClick(View v) {
        chTextView.setVisibility(View.GONE);
        nameTextView.setVisibility(View.GONE);
        micTestView.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
    }
}
