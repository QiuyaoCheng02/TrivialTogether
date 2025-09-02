package com.example.user1.classes.button;

import android.graphics.PointF;

public class PinButtonInfo {
    private PointF position;
    private String name;

    public PinButtonInfo(PointF position, String name) {
        this.position = position;
        this.name = name;
    }

    public PointF getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}
