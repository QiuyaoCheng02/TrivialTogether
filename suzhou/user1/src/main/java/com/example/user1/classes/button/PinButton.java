package com.example.user1.classes.button;

import android.graphics.PointF;

import android.view.View;

public class PinButton {
    private PointF position;
    private int color;
    private String name;
    private View.OnClickListener onClickListener;

    public PinButton(PointF position, int color, String name) {
        this.position = position;
        this.color = color;
        this.name = name;
    }

    public PointF getPosition() {
        return position;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}

