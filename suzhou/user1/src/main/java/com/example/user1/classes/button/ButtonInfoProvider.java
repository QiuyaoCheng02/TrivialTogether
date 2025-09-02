package com.example.user1.classes.button;

import android.graphics.PointF;

import com.example.user1.classes.AttractionsInfo;
import com.example.user1.classes.arrow.PositionInfoProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ButtonInfoProvider {
    public static List<PinButtonInfo> getButtonInfoList() {
        List<PinButtonInfo> buttonInfoList = new ArrayList<>();
        Map<String, PointF> positionInfo = PositionInfoProvider.getPositionInfo();

        // Iterate over position info and create PinButtonInfo objects
        for (Map.Entry<String, PointF> entry : positionInfo.entrySet()) {
            String attraction = entry.getKey();
            PointF position = entry.getValue();
            buttonInfoList.add(new PinButtonInfo(position, attraction));
        }

        return buttonInfoList;
    }
}
