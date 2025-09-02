package com.example.user2.classes.button;

import android.graphics.PointF;

import com.example.user2.classes.AttractionsInfo;

import java.util.ArrayList;
import java.util.List;

public class ButtonInfoProvider {
    public static List<PinButtonInfo> getButtonInfoList() {
        List<PinButtonInfo> buttonInfoList = new ArrayList<>();
        // Add button information here
        buttonInfoList.add(new PinButtonInfo(new PointF(1044,1429), AttractionsInfo.YUEWANGMIAO));
        buttonInfoList.add(new PinButtonInfo(new PointF(1612,1604), AttractionsInfo.XIHU));
        buttonInfoList.add(new PinButtonInfo(new PointF(550,1589), AttractionsInfo.SHUANGFENGCHAYUN));
        buttonInfoList.add(new PinButtonInfo(new PointF(809,1861), AttractionsInfo.TONGLIGUQIAO));
        buttonInfoList.add(new PinButtonInfo(new PointF(940,2190), AttractionsInfo.WUGUITAN));

        buttonInfoList.add(new PinButtonInfo(new PointF(1428,1992), AttractionsInfo.SANTANYINYUE));
        buttonInfoList.add(new PinButtonInfo(new PointF(1243,1495), AttractionsInfo.XILENGYINSHE));
        buttonInfoList.add(new PinButtonInfo(new PointF(1279,1007), AttractionsInfo.HUANGLONGTUCUI));
        buttonInfoList.add(new PinButtonInfo(new PointF(2142,2082), AttractionsInfo.CHENGHUANGGE));
        buttonInfoList.add(new PinButtonInfo(new PointF(1814,2322), AttractionsInfo.ZHEJIANGMEISHUGUAN));

        buttonInfoList.add(new PinButtonInfo(new PointF(2167,1506), AttractionsInfo.JIEBAI));
        buttonInfoList.add(new PinButtonInfo(new PointF(1590,2304), AttractionsInfo.LEIFENGTA));
        buttonInfoList.add(new PinButtonInfo(new PointF(1892,1964), AttractionsInfo.LIULANGWENYING));
        buttonInfoList.add(new PinButtonInfo(new PointF(2362,1712), AttractionsInfo.FENGHUANGSI));
        buttonInfoList.add(new PinButtonInfo(new PointF(2271,1917), AttractionsInfo.QINGHEFANG));

        buttonInfoList.add(new PinButtonInfo(new PointF(2690,1739), AttractionsInfo.GEDE));
        buttonInfoList.add(new PinButtonInfo(new PointF(3001,1154), AttractionsInfo.GUQIANG));
        buttonInfoList.add(new PinButtonInfo(new PointF(3487,1058), AttractionsInfo.ZHONGHAO));
        buttonInfoList.add(new PinButtonInfo(new PointF(1927,975), AttractionsInfo.WENDEMU));
        buttonInfoList.add(new PinButtonInfo(new PointF(1838,409), AttractionsInfo.AOTELAISI));
        // Add more buttons here...  // Add more buttons here...
        return buttonInfoList;
    }
}
