package com.example.user2.classes.arrow;

import android.graphics.PointF;

import com.example.user2.classes.AttractionsInfo;

import java.util.HashMap;
import java.util.Map;

public class PositionInfoProvider {
    public static Map<String, PointF> getPositionInfo() {
        Map<String, PointF> positionInfo = new HashMap<>();
        // Add position information here
        positionInfo.put(AttractionsInfo.ZIJINTA, new PointF(633,784));
        positionInfo.put(AttractionsInfo.QINGLIANGSHAN, new PointF(1045,1483));
        positionInfo.put(AttractionsInfo.JINIANGUAN, new PointF(344,2017));
        positionInfo.put(AttractionsInfo.GULOU, new PointF(1809,1094));
        positionInfo.put(AttractionsInfo.YINGZHOU, new PointF(2239,504));
        positionInfo.put(AttractionsInfo.JIMINGSI, new PointF(2296,993));
        positionInfo.put(AttractionsInfo.ZONGTONGFU, new PointF(2373,1724));
        positionInfo.put(AttractionsInfo.XUANWUHU, new PointF(2474,624));
        positionInfo.put(AttractionsInfo.JIUHUASHAN, new PointF(2737,1048));
        positionInfo.put(AttractionsInfo.ZIJINSHAN, new PointF(3570,784));
        positionInfo.put(AttractionsInfo.BOWUYUAN, new PointF(3421,1861));
        positionInfo.put(AttractionsInfo.MINGGUGONG, new PointF(3129,1981));

        // Add more positions here...
        return positionInfo;
    }
}
