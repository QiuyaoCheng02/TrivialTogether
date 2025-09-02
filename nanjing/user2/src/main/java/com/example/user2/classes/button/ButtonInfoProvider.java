package com.example.user2.classes.button;

import android.graphics.PointF;

import com.example.user2.classes.AttractionsInfo;

import java.util.ArrayList;
import java.util.List;

public class ButtonInfoProvider {
    public static List<PinButtonInfo> getButtonInfoList() {
        List<PinButtonInfo> buttonInfoList = new ArrayList<>();
        // Add button information here
        buttonInfoList.add(new PinButtonInfo(new PointF(600,694), AttractionsInfo.ZIJINTA));
        buttonInfoList.add(new PinButtonInfo(new PointF(1006,1389), AttractionsInfo.QINGLIANGSHAN));
        buttonInfoList.add(new PinButtonInfo(new PointF(307,2014), AttractionsInfo.JINIANGUAN));

        buttonInfoList.add(new PinButtonInfo(new PointF(1772,990), AttractionsInfo.GULOU));
        buttonInfoList.add(new PinButtonInfo(new PointF(2203,405), AttractionsInfo.YINGZHOU));
        buttonInfoList.add(new PinButtonInfo(new PointF(2263,900), AttractionsInfo.JIMINGSI));

        buttonInfoList.add(new PinButtonInfo(new PointF(3535,671), AttractionsInfo.ZIJINSHAN));
        buttonInfoList.add(new PinButtonInfo(new PointF(3092,1892), AttractionsInfo.MINGGUGONG));
        buttonInfoList.add(new PinButtonInfo(new PointF(2700,955), AttractionsInfo.JIUHUASHAN));

        buttonInfoList.add(new PinButtonInfo(new PointF(2340,1624), AttractionsInfo.ZONGTONGFU));
        buttonInfoList.add(new PinButtonInfo(new PointF(2441,524), AttractionsInfo.XUANWUHU));
        buttonInfoList.add(new PinButtonInfo(new PointF(3382,1758), AttractionsInfo.BOWUYUAN));


        // Add more buttons here...
        return buttonInfoList;
    }
}
