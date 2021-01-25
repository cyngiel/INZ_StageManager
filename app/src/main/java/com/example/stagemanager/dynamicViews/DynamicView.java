package com.example.stagemanager.dynamicViews;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                        ViewGroup.LayoutParams.WRAP_CONTENT);
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
        final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final Button button = new Button((context));
        button.setLayoutParams(lparams);
        button.setTextSize(20);
        button.setTextColor(Color.BLACK);
        button.setMaxEms(10);
        button.setText(text);
        button.setBackground(context.getDrawable(R.drawable.rounded_corner_orange));
        button.setScaleX(0.8f);
        button.setScaleY(0.7f);
        return button;
    }

    public Button eventButtonWide(Context context, String text) {
        RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lparams.setMargins(0, 8, 0, 8);
        final Button button = new Button((context));
        button.setLayoutParams(lparams);
        button.setTextSize(20);
        button.setTextColor(Color.BLACK);
        button.setMaxEms(8);
        button.setText(text);
        button.setBackground(context.getDrawable(R.drawable.rounded_corner_orange));
        return button;
    }

    public Button editButton(Context context) {
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Button button = new Button((context));
        button.setLayoutParams(lparams);
        button.setTextSize(20);
        button.setTextColor(Color.BLACK);
        button.setMaxEms(8);
        button.setText("assign");
        button.setBackground(context.getDrawable(R.drawable.rounded_corner_orange));
        button.setScaleX(0.7f);
        button.setScaleY(0.6f);
        return button;
    }

    public Spinner editSpinner(Context context){
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Spinner spinner = new Spinner((context));
        spinner.setLayoutParams(lparams);
        spinner.setBackground(context.getDrawable(R.drawable.rounded_corner_spinner));
        spinner.setLayoutMode(Spinner.MODE_DIALOG);
        return spinner;
    }

}
