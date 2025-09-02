package com.example.user1.classes.dialogs;

import android.graphics.PointF;

import com.example.user1.R;
import com.example.user1.classes.AttractionsInfo;

import java.util.ArrayList;
import java.util.List;

public class DialogInfoProvider {
    public static List<DialogInfo> getDialogInfoList() {
        List<DialogInfo> dialogInfoList = new ArrayList<>();

        // Add your dialog info objects here
        dialogInfoList.add(new DialogInfo(R.drawable.hanshansi, "寒山寺", "1.5", AttractionsInfo.HANSHANSI, new PointF(500,500), Introduction.HANSHANSI));
        dialogInfoList.add(new DialogInfo(R.drawable.canglangting, "沧浪亭", "2.0", AttractionsInfo.CANGLANGTING, new PointF(500,500), Introduction.CANGLANGTING));
        dialogInfoList.add(new DialogInfo(R.drawable.guanqianjie, "观前街", "1.0", AttractionsInfo.GUANQIANJIE, new PointF(500,500), Introduction.GUANQIANJIE));
        dialogInfoList.add(new DialogInfo(R.drawable.dongyuan, "东园", "1.5", AttractionsInfo.DONGYUAN, new PointF(500,500), Introduction.DONGYUAN));
        dialogInfoList.add(new DialogInfo(R.drawable.huqiu, "虎丘", "1.0", AttractionsInfo.HUQIU, new PointF(500,500), Introduction.HUQIU));

        dialogInfoList.add(new DialogInfo(R.drawable.panmen, "盘门", "1.5", AttractionsInfo.PANMEN, new PointF(500,500), Introduction.PANMEN));
        dialogInfoList.add(new DialogInfo(R.drawable.huaihaijie, "淮海街", "2.0", AttractionsInfo.HUAIHAIJIE, new PointF(500,500), Introduction.HUAIHAIJIE));
        dialogInfoList.add(new DialogInfo(R.drawable.liuyuan, "留园", "1.0", AttractionsInfo.LIUYUAN, new PointF(500,500), Introduction.LIUYUAN));
        dialogInfoList.add(new DialogInfo(R.drawable.qilishantang, "七里山塘", "1.0", AttractionsInfo.QILISHANTANG, new PointF(500,500), Introduction.QILISHANTANG));
        dialogInfoList.add(new DialogInfo(R.drawable.wangshiyuan, "网师园", "1.5", AttractionsInfo.WANGSHIYUAN, new PointF(500,500), Introduction.WANGSHIYUAN));

        dialogInfoList.add(new DialogInfo(R.drawable.suzhouyuanlin, "苏州园林", "1.5", AttractionsInfo.SUZHOUYUANLIN, new PointF(500,500), Introduction.SUZHOUYUANLIN));
        dialogInfoList.add(new DialogInfo(R.drawable.puyuan, "朴园", "2.0", AttractionsInfo.PUYUAN, new PointF(500,500), Introduction.PUYUAN));
        dialogInfoList.add(new DialogInfo(R.drawable.changyuan, "畅园", "1.0", AttractionsInfo.CHANGYUAN, new PointF(500,500), Introduction.CHANGYUAN));
        dialogInfoList.add(new DialogInfo(R.drawable.wanhao, "万豪酒店", "0", AttractionsInfo.WANHAO, new PointF(500,500), Introduction.WANHAO));
        dialogInfoList.add(new DialogInfo(R.drawable.huaqiao, "华侨饭店", "0", AttractionsInfo.HUAQIAOFANDIAN, new PointF(500,500), Introduction.HUAQIAOFANDIAN));

        dialogInfoList.add(new DialogInfo(R.drawable.ramda, "Ramda", "0", AttractionsInfo.RAMADA, new PointF(500,500), Introduction.RAMADA));
        dialogInfoList.add(new DialogInfo(R.drawable.yipu, "艺圃", "1.5", AttractionsInfo.YIPU, new PointF(500,500), Introduction.YIPU));
        dialogInfoList.add(new DialogInfo(R.drawable.shizilin, "狮子林", "1.0", AttractionsInfo.SHIZILIN, new PointF(500,500), Introduction.SHIZILIN));
        dialogInfoList.add(new DialogInfo(R.drawable.kunqu, "昆曲博物馆", "2.0", AttractionsInfo.KUNQU, new PointF(500,500), Introduction.KUNQU));
        dialogInfoList.add(new DialogInfo(R.drawable.shuangta, "双塔", "1.0", AttractionsInfo.SHUANGTA, new PointF(500,500), Introduction.SHUANGTA));

        // Add more dialog info objects as needed

        return dialogInfoList;
    }

    public class Introduction {
        public static final String HANSHANSI = "寒山寺是苏州的一处著名古刹，建于南朝宋大明年间。寒山寺风景秀丽，以碧水翠山、古木参天而著称。";
        public static final String CANGLANGTING = "沧浪亭是苏州的一处历史古迹，建于五代时期，至今已有千年历史。沧浪亭周围风光秀丽，是游览苏州的著名景点之一。";
        public static final String GUANQIANJIE = "观前街是苏州的一条历史老街，有着悠久的历史和丰富的文化底蕴。街道两旁保留了许多古建筑，是苏州旅游的热门景点之一。";
        public static final String DONGYUAN = "东园是苏州的一处园林景点，建于清代乾隆年间。东园以其精美的园林设计和古色古香的建筑而著称。";
        public static final String HUQIU = "虎丘是苏州的一处名胜古迹，景区内有着著名的虎丘塔和游览名胜。虎丘风景秀丽，是游览苏州的主要景点之一。";

        public static final String PANMEN = "盘门是苏州的标志性建筑之一，是中国四大古城门之一，也是苏州古城墙的组成部分之一。";
        public static final String HUAIHAIJIE = "淮海街是苏州的商业中心之一，拥有众多购物商场、餐厅和娱乐场所，是游客购物和观光的热门地点之一。";
        public static final String LIUYUAN = "留园是苏州的一处园林景点，以其精致的园林设计和美丽的景色而闻名。留园也是中国古典园林的代表之一。";
        public static final String QILISHANTANG = "七里山塘是苏州的一条古老河道，两岸有着众多历史建筑和文化景点，是苏州旅游的热门景点之一。";
        public static final String WANGSHIYUAN = "网师园，建于南宋时期（1127年到1279年），原本只是取名叫“渔隐”的小型私人花园，经过多次修建，清朝乾隆时期（1736年到1796年），改名为网师园。位于苏州东南方的十全街阔头巷中。";

        public static final String SUZHOUYUANLIN = "苏州园林是苏州的一大特色，以其独特的园林建筑和精致的园林景观而闻名于世。";
        public static final String PUYUAN = "朴园位于苏州市姑苏区校场桥路8号，地处桃花坞历史文化片区，1991年1月被列为苏州市文物保护单位，是苏州为数不多的一座民国式园林。该园原为汪氏别墅，建于民国21年（1932年）左右，项目占地面积6354平方米，园内现存建筑共13处，其中保留下来的原有建筑有四处。";
        public static final String CHANGYUAN = "苏州畅园是一处始建于清代的古典园林建筑。园位于住宅东侧，面积约1亩左右。从园门步入，即是桂花厅，厅后小院中原植有桂树、梧桐。小院对面是桐华书屋，穿过书屋，全园景物尽在眼前。畅园位于苏州城西庙堂巷22号，面积约一亩余。畅园为苏州小型园林的典型，建于清末，园主姓潘。该园造园手法细腻，面积虽小而布局巧妙，园景丰富而多层次，具有精致玲珑特色，是代表中国传统文化、民族特色的永久性珍品。";
        public static final String WANHAO = "万豪酒店是苏州的一家高端酒店，提供豪华的住宿和优质的服务，是游客休息和度假的理想场所之一。";
        public static final String HUAQIAOFANDIAN = "华侨饭店是苏州的一家知名饭店，提供住宿，各种美食和舒适的就餐环境，是游客品尝苏式美食的好去处。";

        public static final String RAMADA = "Ramada酒店是苏州的一家知名酒店，提供豪华的住宿和一流的服务，是游客休息和度假的理想场所之一。";
        public static final String YIPU = "艺圃是明代宅邸园林，位于中国江苏省苏州市姑苏区文衙弄5号。全园占地仅为五亩，以池水为中心，池岸低平。池北以建筑为主，池南以假山景观为主，园景开朗，风格质朴，较多地保存了明末清初建园时格局，是典型的文人园林。";
        public static final String SHIZILIN = "狮子林位于中国江苏省苏州市姑苏区园林路，毗邻太平天国忠王府和拙政园，占地1.1公顷，开放面积0.88公顷，是苏州四大名园之一。";
        public static final String KUNQU = "昆曲博物馆是苏州的一处文化景点，展示了苏州昆曲艺术的历史和传统，是昆曲爱好者的朝圣地。";
        public static final String SHUANGTA = "唐咸通二年（811年）始建般若院，吴越时改称罗汉院。这是罗汉院双塔及正殿遗址的前身。在北宋太平兴国七年（982年），王文罕三兄弟建双塔。清咸丰十年（1860年）庚申之劫中罗汉院正殿毁于战火，仅存遗址。1996年，罗汉院双塔及正殿遗址被列为全国文物重点的保护单位。";
    }

}
