package com.example.stagemanager.dynamicViews;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.stagemanager.R;

public class DynamicView {
    Context context;

    public DynamicView(Context context) {
        this.context = context;
    }

    public TextView chTextView(Context context, String text) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView((context));
        textView.setLayoutParams(lparams);
        textView.setTextSize(20);
        textView.setTextColor(context.getResources().getColor(R.color.onSurface));
        textView.setText(" " + text + " ");
        textView.setMaxEms(8);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return textView;
    }

    public TextView nameTextView(Context context, String text) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView((context));
        textView.setLayoutParams(lparams);
        textView.setTextSize(20);
        textView.setTextColor(context.getResources().getColor(R.color.onSurface));
        textView.setText(" " + text + " ");
        textView.setMaxEms(8);
        return textView;
    }

    public TextView micLineTextView(Context context, String text) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView((context));
        textView.setLayoutParams(lparams);
        textView.setTextSize(20);
        textView.setTextColor(context.getResources().getColor(R.color.onSurface));
        textView.setText(" " + text + " ");
        textView.setMaxEms(8);
        return textView;
    }

    public Button hideButton(Context context) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button button = new Button((context));
        button.setLayoutParams(lparams);
        button.setTextSize(20);
        button.setTextColor(Color.BLACK);
        button.setMaxEms(8);
        button.setText("hide");
        button.setBackground(context.getDrawable(R.drawable.rounded_corner_orange));
        button.setScaleX(0.5f);
        button.setScaleY(0.6f);
        return button;
    }

    public Button removeButton(Context context) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button button = new Button((context));
        button.setLayoutParams(lparams);
        button.setTextSize(20);
        button.setTextColor(Color.BLACK);
        button.setMaxEms(8);
        button.setText("remove");
        button.setBackground(context.getDrawable(R.drawable.rounded_corner_orange));
        button.setScaleX(0.7f);
        button.setScaleY(0.6f);
        return button;
    }

    public Button eventButton(Context context, String text) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button button = new Button((context));
        button.setLayoutParams(lparams);
        button.setTextSize(20);
        button.setTextColor(Color.BLACK);
        button.setMaxEms(8);
        button.setText(text);
        button.setBackground(context.getDrawable(R.drawable.rounded_corner_orange));
        button.setScaleX(0.7f);
        button.setScaleY(0.6f);
        return button;
    }

    public Button editButton(Context context) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button button = new Button((context));
        button.setLayoutParams(lparams);
        button.setTextSize(20);
        button.setTextColor(Color.BLACK);
        button.setMaxEms(8);
        button.setText("edit");
        button.setBackground(context.getDrawable(R.drawable.rounded_corner_orange));
        button.setScaleX(0.7f);
        button.setScaleY(0.6f);
        return button;
    }

}
