package com.example.user2.classes.button;

import android.graphics.Color;
import android.view.View;

import java.util.List;

public class ButtonManager {
    private PinButtonView pinButtonView;
    private List<PinButtonInfo> buttonInfoList;
    private ButtonClickListener buttonClickListener;

    public ButtonManager(PinButtonView pinButtonView) {
        this.pinButtonView = pinButtonView;
        this.buttonInfoList = ButtonInfoProvider.getButtonInfoList();
        this.buttonClickListener = new ButtonClickListener();
    }

    public void addButton(PinButtonInfo buttonInfo) {
        PinButton button = new PinButton(buttonInfo.getPosition(), Color.RED, buttonInfo.getName());
        button.setOnClickListener(buttonClickListener);
        pinButtonView.addPinButton(button);
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getTag() instanceof PinButton) {
                PinButton button = (PinButton) v.getTag();
                // Perform actions specific to the clicked button
                String buttonName = button.getName();
                // Use buttonName to identify different buttons
                // Perform actions specific to the clicked button
            }
        }
    }
}