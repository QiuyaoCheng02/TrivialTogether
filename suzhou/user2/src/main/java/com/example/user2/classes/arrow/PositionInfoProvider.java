package com.example.user2.classes.arrow;

import android.graphics.PointF;

import com.example.user2.classes.AttractionsInfo;

import java.util.HashMap;
import java.util.Map;

public class PositionInfoProvider {
    public static Map<String, PointF> getPositionInfo() {
        Map<String, PointF> positionInfo = new HashMap<>();
        // Add position information here
        positionInfo.put(AttractionsInfo.HANSHANSI, new PointF(829,1275));
        positionInfo.put(AttractionsInfo.HUQIU, new PointF(1259,654));
        positionInfo.put(AttractionsInfo.HUAIHAIJIE, new PointF(571,2147));
        positionInfo.put(AttractionsInfo.LIUYUAN, new PointF(1696,1029));
        positionInfo.put(AttractionsInfo.QILISHANTANG, new PointF(2095,989));

        positionInfo.put(AttractionsInfo.PUYUAN, new PointF(2531,654));
        positionInfo.put(AttractionsInfo.SUZHOUYUANLIN, new PointF(3050,654));
        positionInfo.put(AttractionsInfo.DONGYUAN, new PointF(3354,872));
        positionInfo.put(AttractionsInfo.CANGLANGTING, new PointF(2912,1932));
        positionInfo.put(AttractionsInfo.PANMEN, new PointF(2519,2264));

        positionInfo.put(AttractionsInfo.WANGSHIYUAN, new PointF(3244,1800));
        positionInfo.put(AttractionsInfo.GUANQIANJIE, new PointF(2912,1223));
        positionInfo.put(AttractionsInfo.WANHAO, new PointF(1339,1576));
        positionInfo.put(AttractionsInfo.HUAQIAOFANDIAN, new PointF(2015,1714));
        positionInfo.put(AttractionsInfo.YIPU, new PointF(2310,1149));

        positionInfo.put(AttractionsInfo.RAMADA, new PointF(2454,1309));
        positionInfo.put(AttractionsInfo.CHANGYUAN, new PointF(2519,1576));
        positionInfo.put(AttractionsInfo.SHUANGTA, new PointF(3219,1422));
        positionInfo.put(AttractionsInfo.KUNQU, new PointF(3296,1149));
        positionInfo.put(AttractionsInfo.SHIZILIN, new PointF(3050,805));

        return positionInfo;
    }
}
