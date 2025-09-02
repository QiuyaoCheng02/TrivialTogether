package com.example.user2.classes.dialogs;

import android.graphics.PointF;

import com.example.user1.R;
import com.example.user2.classes.AttractionsInfo;

import java.util.ArrayList;
import java.util.List;

public class DialogInfoProvider {
    public static List<DialogInfo> getDialogInfoList() {
        List<DialogInfo> dialogInfoList = new ArrayList<>();

        // Add your dialog info objects here
        dialogInfoList.add(new DialogInfo(R.drawable.hanshansi, "紫金塔", "1.5", AttractionsInfo.ZIJINTA, new PointF(500,500)));
        dialogInfoList.add(new DialogInfo(R.drawable.canglangting, "九华山公园", "2.0", AttractionsInfo.JIUHUASHAN,new PointF(500,500)));
        dialogInfoList.add(new DialogInfo(R.drawable.guanqianjie, "明故宫", "1.0", AttractionsInfo.MINGGUGONG,new PointF(500,500)));

        dialogInfoList.add(new DialogInfo(R.drawable.dongyuan, "玄武湖公园", "1.5", AttractionsInfo.XUANWUHU,new PointF(500,500)));
        dialogInfoList.add(new DialogInfo(R.drawable.huqiu, "清凉山公园", "1.0", AttractionsInfo.QINGLIANGSHAN,new PointF(500,500)));
        dialogInfoList.add(new DialogInfo(R.drawable.panmen, "紫金山天文台", "1.5", AttractionsInfo.ZIJINSHAN,new PointF(500,500)));

        dialogInfoList.add(new DialogInfo(R.drawable.huaihaijie, "南京大屠杀纪念馆", "2.0", AttractionsInfo.JINIANGUAN,new PointF(500,500)));
        dialogInfoList.add(new DialogInfo(R.drawable.liuyuan, "鼓楼公园", "1.0", AttractionsInfo.GULOU,new PointF(500,500)));
        dialogInfoList.add(new DialogInfo(R.drawable.qilishantang, "樱洲", "1.0", AttractionsInfo.YINGZHOU,new PointF(500,500)));

        dialogInfoList.add(new DialogInfo(R.drawable.wangshiyuan, "南京博物院", "1.5", AttractionsInfo.BOWUYUAN,new PointF(500,500)));
        dialogInfoList.add(new DialogInfo(R.drawable.suzhouyuanlin, "总统府", "1.5", AttractionsInfo.ZONGTONGFU,new PointF(500,500)));
        dialogInfoList.add(new DialogInfo(R.drawable.puyuan, "鸡鸣寺", "2.0", AttractionsInfo.JIMINGSI,new PointF(500,500)));

        // Add more dialog info objects as needed

        return dialogInfoList;
    }
}
