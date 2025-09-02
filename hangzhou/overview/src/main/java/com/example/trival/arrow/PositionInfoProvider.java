package com.example.trival.arrow;

import android.graphics.PointF;

import com.example.trival.AttractionsInfo;


import java.util.HashMap;
import java.util.Map;

public class PositionInfoProvider {
    public static Map<String, PointF> getPositionInfo() {
        Map<String, PointF> positionInfo = new HashMap<>();
        // Add position information here
        positionInfo.put(AttractionsInfo.YUEWANGMIAO, new PointF(1044,1429));
        positionInfo.put(AttractionsInfo.XIHU, new PointF(1612,1604));
        positionInfo.put(AttractionsInfo.SHUANGFENGCHAYUN, new PointF(550,1589));
        positionInfo.put(AttractionsInfo.TONGLIGUQIAO, new PointF(809,1861));
        positionInfo.put(AttractionsInfo.WUGUITAN, new PointF(940,2190));

        positionInfo.put(AttractionsInfo.SANTANYINYUE, new PointF(1428,1992));
        positionInfo.put(AttractionsInfo.XILENGYINSHE, new PointF(1243,1495));
        positionInfo.put(AttractionsInfo.HUANGLONGTUCUI, new PointF(1279,1007));
        positionInfo.put(AttractionsInfo.CHENGHUANGGE, new PointF(2142,2082));
        positionInfo.put(AttractionsInfo.ZHEJIANGMEISHUGUAN, new PointF(1814,2322));

        positionInfo.put(AttractionsInfo.JIEBAI, new PointF(2167,1506));
        positionInfo.put(AttractionsInfo.LEIFENGTA, new PointF(1590,2304));
        positionInfo.put(AttractionsInfo.LIULANGWENYING, new PointF(1892,1964));
        positionInfo.put(AttractionsInfo.FENGHUANGSI, new PointF(2362,1712));
        positionInfo.put(AttractionsInfo.QINGHEFANG, new PointF(2271,1917));

        positionInfo.put(AttractionsInfo.GEDE, new PointF(2690,1739));
        positionInfo.put(AttractionsInfo.GUQIANG, new PointF(3001,1154));
        positionInfo.put(AttractionsInfo.ZHONGHAO, new PointF(3487,1058));
        positionInfo.put(AttractionsInfo.WENDEMU, new PointF(1927,975));
        positionInfo.put(AttractionsInfo.AOTELAISI, new PointF(1838,409));
        // Add more positions here...
        return positionInfo;
    }
}
