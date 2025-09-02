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
        dialogInfoList.add(new DialogInfo(R.drawable.hanshansi, "岳王庙", "1.5", AttractionsInfo.YUEWANGMIAO, new PointF(500,500), Introduction.YUEWANGMIAO));
        dialogInfoList.add(new DialogInfo(R.drawable.canglangting, "浙江美术馆", "2.0", AttractionsInfo.ZHEJIANGMEISHUGUAN, new PointF(500,500), Introduction.ZHEJIANGMEISHUGUAN));
        dialogInfoList.add(new DialogInfo(R.drawable.guanqianjie, "城隍阁", "1.0", AttractionsInfo.CHENGHUANGGE, new PointF(500,500), Introduction.CHENGHUANGGE));
        dialogInfoList.add(new DialogInfo(R.drawable.dongyuan, "雷峰塔", "1.5", AttractionsInfo.LEIFENGTA, new PointF(500,500), Introduction.LEIFENGTA));
        dialogInfoList.add(new DialogInfo(R.drawable.huqiu, "西湖", "1.0", AttractionsInfo.XIHU, new PointF(500,500), Introduction.XIHU));

        dialogInfoList.add(new DialogInfo(R.drawable.aotelaisi, "奥特莱斯", "1.5", AttractionsInfo.AOTELAISI, new PointF(500,500), Introduction.AOTELAISI));
        dialogInfoList.add(new DialogInfo(R.drawable.jiebai, "杭州解百", "1.5", AttractionsInfo.JIEBAI, new PointF(500,500), Introduction.JIEBAI));
        dialogInfoList.add(new DialogInfo(R.drawable.huaihaijie, "双峰插云", "2.0", AttractionsInfo.SHUANGFENGCHAYUN, new PointF(500,500), Introduction.SHUANGFENGCHAYUN));
        dialogInfoList.add(new DialogInfo(R.drawable.liuyuan, "通利古桥", "1.0", AttractionsInfo.TONGLIGUQIAO, new PointF(500,500), Introduction.TONGLIGUQIAO));
        dialogInfoList.add(new DialogInfo(R.drawable.qilishantang, "乌龟谭", "1.0", AttractionsInfo.WUGUITAN, new PointF(500,500), Introduction.WUGUITAN));

        dialogInfoList.add(new DialogInfo(R.drawable.wangshiyuan, "柳浪闻莺", "1.5", AttractionsInfo.LIULANGWENYING, new PointF(500,500), Introduction.LIULANGWENYING));
        dialogInfoList.add(new DialogInfo(R.drawable.gede, "歌德酒店", "0", AttractionsInfo.GEDE, new PointF(500,500), Introduction.GEDE));
        dialogInfoList.add(new DialogInfo(R.drawable.puyuan, "三潭印月", "1.0", AttractionsInfo.SANTANYINYUE, new PointF(500,500), Introduction.SANTANYINYUE));
        dialogInfoList.add(new DialogInfo(R.drawable.qinghefang, "清河坊", "1.5", AttractionsInfo.QINGHEFANG, new PointF(500,500), Introduction.QINGHEFANG));
        dialogInfoList.add(new DialogInfo(R.drawable.huanglongtucui, "黄龙吐翠", "1.5", AttractionsInfo.HUANGLONGTUCUI, new PointF(500,500), Introduction.HUANGLONGTUCUI));

        dialogInfoList.add(new DialogInfo(R.drawable.wendemu, "温德姆酒店", "0", AttractionsInfo.WENDEMU, new PointF(500,500), Introduction.WENDEMU));
        dialogInfoList.add(new DialogInfo(R.drawable.zhonghao, "中豪大酒店", "0", AttractionsInfo.ZHONGHAO, new PointF(500,500), Introduction.ZHONGHAO));
        dialogInfoList.add(new DialogInfo(R.drawable.guqiang, "古城墙陈列馆", "1.5", AttractionsInfo.GUQIANG, new PointF(500,500), Introduction.GUQIANG));
        dialogInfoList.add(new DialogInfo(R.drawable.fenghuangsi, "凤凰寺", "1.0", AttractionsInfo.FENGHUANGSI, new PointF(500,500), Introduction.FENGHUANGSI));
        dialogInfoList.add(new DialogInfo(R.drawable.xilingyinshe, "西泠印社", "0.5", AttractionsInfo.XILENGYINSHE, new PointF(500,500), Introduction.XILENGYINSHE));

        return dialogInfoList;
    }
    // 介绍信息
    public class Introduction {
        public static final String YUEWANGMIAO = "岳王庙是中国著名的古建筑之一，位于杭州市区，建于南宋，是纪念岳飞的地方。";
        public static final String ZHEJIANGMEISHUGUAN = "浙江美术馆主要代表国家承担美术作品和美术文献的展览、陈列、征集、收藏，并利用美术和美术馆资源开展学术研究、教育推广、对外交流和公共文化服务，2015年入选为第二批国家重点美术馆。";
        public static final String CHENGHUANGGE = "城隍阁坐落在中华人民共和国浙江省杭州市新西湖十景吴山天风景区，景区内保存有为元朝和明朝建筑，为历史文化古迹，总面积1000亩。";
        public static final String LEIFENGTA = "雷峰塔是杭州的标志性建筑之一，登塔可以俯瞰整个西湖风景，是旅游者不容错过的地方。";
        public static final String XIHU = "西湖是中国著名的风景名胜区之一，被誉为“天堂在西、苏杭在东”，以其优美的自然风光吸引着无数游客前来观赏。";
        public static final String AOTELAISI = "奥特莱斯是一家知名的购物中心，提供各种时尚品牌和优质商品，是购物爱好者的天堂。";
        public static final String JIEBAI = "杭州解百是一家综合性商场，汇集了服装、美妆、家居等各种品类的商品，满足了人们的各种购物需求。";
        public static final String SHUANGFENGCHAYUN = "南高峰与北高峰，地势高耸俯瞰西湖，流云霞鹤，气象万千，古时均为僧人所占。山巅建佛塔，遥相对峙，迥然高于群峰之上。春秋佳日，岚翠雾白，塔尖入云，时隐时显，远望若仙境一般。南高峰、北高峰，是古时候西湖群山中喧盛一时的佛教名山，山顶都建有佛寺、佛塔。春秋佳日，岚翠雾白，塔尖时隐时显，自西湖舟中远观，景观独标一格。南宋时，两峰插云成名并跻身西湖十景之列。";
        public static final String TONGLIGUQIAO = "通利古桥是一座历史悠久的古建筑, 位于小新桥北面百余米。位于菉葭巷东端横卧平江河的通利桥与平江路上跨平江河支流柳枝河的朱马交桥相衔而成的“双桥”景观。桥面由六条并列的石梁组成，其中两条为宽约40厘米的武康石，应是宋代遗物。桥台立条石排柱，桥栏亦为简洁的通长条石构筑。通利桥两边的桥台并不相等，西桥台有长长的缓坡引桥，远远望去有一种不对称的美。";
        public static final String WUGUITAN = "乌龟潭景区东靠杨公堤，西临三台山路（三台梦迹景区），北至空军杭州疗养院，南到八盘岭路。2003年建成，展示西湖地区历史上典型自然地貌和湿地生态景观。该景区通过拓展水面，设景观小岛，并以木曲桥与水岸相连，于水岸边筑茅草亭、茅草屋等能与湿地景观融为一体的景观建筑小品，四季皆能成景。主要有于谦祠及永福桥两处景点。";
        public static final String LIULANGWENYING = "柳浪闻莺是西湖的一处美丽景点，每年春天，柳树吐翠，小鸟鸣啭，给人们带来了无限的春意。";
        public static final String GEDE = "歌德酒店是一家三星级酒店，提供舒适的住宿环境和优质的服务，是商务旅行者的理想选择。";
        public static final String SANTANYINYUE = "三潭印月是西湖的一个景点，夜晚时分，月光倒映在湖面上，宛如三个明亮的月亮，非常美丽。";
        public static final String QINGHEFANG = "清河坊是一条古老的商业街，保留了许多传统建筑和特色小吃，是体验老杭州风情的好地方。";
        public static final String HUANGLONGTUCUI = "黄龙吐翠是一处以翠竹为主题的景点，绿色的竹子与蓝天白云相映成趣，给人一种清新的感觉。西湖北山栖霞岭北麓，茂林修竹深处，隐藏着颇具道教洞天福地气象的黄龙洞古迹。";
        public static final String WENDEMU = "温德姆酒店是一家知名的国际连锁酒店，提供豪华舒适的住宿体验，深受游客喜爱。";
        public static final String ZHONGHAO = "中豪大酒店是一家高档的四星级商务酒店，地理位置优越，设施齐全，是商务人士的首选。";
        public static final String GUQIANG = "杭州古城墙陈列馆全长不过66米，但是这座仿古城墙里藏着的老杭州的历史却超过1400年。陈列馆位于环城东路和庆春路交叉口的贴沙河边，坐落于古庆春门的旧址之上，贴沙河乃是昔日杭州的护城河。";
        public static final String FENGHUANGSI = "凤凰寺为中国东南沿海伊斯兰教四大清真古寺之一（另三处为扬州仙鹤寺、泉州清净寺和广州怀圣寺），位于浙江省杭州市上城区中山中路西侧223号。该寺约始建于唐或宋，原名真教寺，宋末遭毁，元代得西域伊斯兰教大师阿老丁（Ala al-Din）捐资重建。民国十七年（1928）原照壁、寺门、望月楼和长廊等因道路拓宽拆除。1953年大修，至今保存较好。因其建筑群形似凤凰，故清代时又称凤凰寺。";
        public static final String XILENGYINSHE = "西泠印社，位于中国浙江省杭州市西湖孤山西南麓、西泠桥旁，是中国研究金石篆刻的一个百年学术团体，有“天下第一名社”之称，今为全国重点文物保护单位。西泠印社的金石篆刻技艺是国家级非物质文化遗产。";
    }

}
