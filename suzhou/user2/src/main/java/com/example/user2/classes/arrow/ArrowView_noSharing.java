package com.example.user2.classes.arrow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.user1.R;
import com.example.user2.classes.DBhelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArrowView_noSharing extends View {
    private List<ArrowView.Arrow> arrowList = new ArrayList<>();

    private float mScale = 1.0f;
    private PointF mCenter = new PointF(0.5f, 0.5f);
    private Map<String, PointF> positionInfo;
    private Paint paint;
    private Path path;

    private List<ArrowView.Flag> flagList = new ArrayList<>();
    private Paint flagPaint;
    private Paint polePaint;
    private Paint textPaint;
    private int user;
    private int number;
    private static final int FLAGPOLE_LENGTH = 200;


    public ArrowView_noSharing(Context context) {
        super(context);
        init();
    }

    public ArrowView_noSharing(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArrowView_noSharing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setScale(float scale) {
        mScale = scale;
        invalidate(); // Trigger redraw
    }

    public void setCenter(PointF center) {
        mCenter = center;
        invalidate(); // Trigger redraw
    }

    public void addArrow(ArrowView.Arrow arrow) {
        arrowList.add(arrow);
        invalidate(); // Trigger redraw
    }

    public void addArrow(String startPointName, String endPointName) {
        PointF startPoint = positionInfo.get(startPointName);
        PointF endPoint = positionInfo.get(endPointName);
        if (startPoint != null && endPoint != null) {
            ArrowView.Arrow arrow = new ArrowView.Arrow(startPoint, endPoint, Color.BLACK); // You can set arrow color as per your requirement
            addArrow(arrow);
        }
    }

    public void setPositionInfo(Map<String, PointF> positionInfo) {
        this.positionInfo = positionInfo;
    }


    //flag
    public void addFlag(ArrowView.Flag flag) {
        flagList.add(flag);
        invalidate(); // 触发重绘
    }

    public void addFlag(String name, int order, int user) {
        PointF pointF=positionInfo.get(name);
        ArrowView.Flag flag = new ArrowView.Flag(pointF, order, user);
        addFlag(flag);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (ArrowView.Arrow arrow : arrowList) {
            // Calculate arrow position relative to the view's position and scale
            float viewStartX = (arrow.getStartPoint().x - mCenter.x) * mScale + getWidth() / 2;
            float viewStartY = (arrow.getStartPoint().y - mCenter.y) * mScale + getHeight() / 2;
            float viewEndX = (arrow.getEndPoint().x - mCenter.x) * mScale + getWidth() / 2;
            float viewEndY = (arrow.getEndPoint().y - mCenter.y) * mScale + getHeight() / 2;

            // Draw arrow at the calculated position
            drawArrow(canvas, viewStartX, viewStartY, viewEndX, viewEndY, arrow.getColor());
        }

        for (ArrowView.Flag flag : flagList) {
            // 计算旗杆的起始点位置
            float poleStartX = (flag.getPointF().x - mCenter.x) * mScale + getWidth() / 2;
            float poleStartY = (flag.getPointF().y - mCenter.y) * mScale + getHeight() / 2;

            // 计算旗杆的终点位置
            float poleEndX = poleStartX;
            float poleEndY = poleStartY - FLAGPOLE_LENGTH;

            drawFlag(canvas, poleEndX, poleStartY, flag.getOrder(),flag.getUser());
        }
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE); // 设置箭头颜色为黑色
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        path = new Path();

        flagPaint = new Paint();
        flagPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.yellow)); // 初始颜色为红色
        polePaint = new Paint();
        polePaint.setColor(Color.BLACK);
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        number = 0; // 初始数字为0
    }

    private void drawArrow(Canvas canvas, float startX, float startY, float endX, float endY, int user) {
        int arrowSize = (int) (40 * mScale); // 根据缩放比例调整箭头大小
        paint.setStrokeWidth(10 * mScale); // 根据缩放比例调整线段宽度

        // 计算箭头指向目的地的角度
        float angle = (float) Math.atan2(endY - startY, endX - startX);

        // 绘制箭头的三角形部分
        path.reset();
        path.moveTo(endX - arrowSize * (float) Math.cos(angle - Math.PI / 6), endY - arrowSize * (float) Math.sin(angle - Math.PI / 6)); // 左下角
        path.lineTo(endX, endY); // 顶部
        path.lineTo(endX - arrowSize * (float) Math.cos(angle + Math.PI / 6), endY - arrowSize * (float) Math.sin(angle + Math.PI / 6)); // 右下角
        path.close(); // 封闭路径

        canvas.drawPath(path, paint);

        // 绘制箭头的竖线
        canvas.drawLine(startX, startY, endX - (float) (arrowSize * 0.8 * Math.cos(angle)), endY - (float) (arrowSize * 0.8 * Math.sin(angle)), paint);
    }

    public void updateArrow(Context context) {
        DBhelper dbHelper = new DBhelper(context);
        Map<String, Integer> orderMap = dbHelper.getAllPlaceOrders(); // 获取所有景点及其顺序的映射

        // 清空现有的箭头列表
        arrowList.clear();

        // 遍历数据库中的顺序信息，添加箭头
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String startPointName = entry.getKey();
            int startOrder = entry.getValue();
            int startUser=dbHelper.getPlaceUser(startPointName);
            // 如果顺序为-1，表示该景点未被添加到旅游计划里，不需要绘制箭头
            if (startOrder == -1) {
                continue;
            }

            // 查询下一个顺序的景点
            String endPointName = dbHelper.getPlaceWithOrder(startOrder + 1);

            // 如果下一个景点存在，则添加箭头
            if (endPointName != null&&startUser==2) {

                int endUser=dbHelper.getPlaceUser(endPointName);
                if(endUser==2){
                    addArrow(startPointName, endPointName);
                }

            }
        }

        // 通知视图重绘
        invalidate();
    }
    public void drawFlag(Canvas canvas, float poleEndX, float poleStartY, int order, int user) {
        // 计算缩放后的旗子和旗杆的大小
        float scaledFlagpoleLength = FLAGPOLE_LENGTH * mScale;
        float scaledFlagWidth = scaledFlagpoleLength / 3;
        float scaledFlagHeight = scaledFlagWidth * 4 / 3;

        // 计算旗子的左上角和右下角坐标
        float flagLeft = poleEndX + scaledFlagHeight; // 调整为从旗杆左侧开始
        float flagTop = poleStartY + scaledFlagWidth-scaledFlagpoleLength;
        float flagRight = poleEndX; // 调整为与旗杆相连
        float flagBottom = poleStartY-scaledFlagpoleLength;
        if(user==2){
            flagPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.yellow));
            textPaint.setColor(Color.BLACK);

        }if(user==1){
            //user==2时不显示

        }
        // 绘制旗子
        canvas.drawRect(flagLeft, flagTop, flagRight, flagBottom, flagPaint);

        // 绘制旗子上的数字
        String text = String.valueOf(order);
        float textWidth = textPaint.measureText(text); // 不需要乘以缩放比例
        float textHeight = textPaint.getTextSize() /2; // 不需要乘以缩放比例
        float textX = flagLeft + (flagRight - flagLeft) / 2 - textWidth / 2;  // 文字的水平位置与旗子的中心对齐
        float textY = flagTop + (flagBottom - flagTop) / 2 + textHeight / 2; // 文字的垂直位置与旗子的中心对齐
        textPaint.setTextSize(50 * mScale);

        canvas.drawText(text, textX, textY, textPaint);
        // 绘制旗杆
        polePaint.setStrokeWidth(6 * mScale); // 根据缩放比例调整旗杆的宽度
        canvas.drawLine(poleEndX, poleStartY, poleEndX, poleStartY - scaledFlagpoleLength, polePaint);

    }

    public void updateFlag(Context context) {
        DBhelper dbHelper = new DBhelper(context);
        Map<String, Integer> orderMap = dbHelper.getAllPlaceOrders(); // 获取所有景点及其顺序的映射

        flagList.clear();


        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String startPointName = entry.getKey();
            int startOrder = entry.getValue();
            int user=dbHelper.getPlaceUser(startPointName);
            // 如果顺序为-1，表示该景点未被添加到旅游计划里，不需要绘制箭头
            if (startOrder == -1) {
                continue;
            }
            if(user==2){
                addFlag(startPointName,startOrder,user);
            }

            // 查询下一个顺序的景点
        }

        // 通知视图重绘
        invalidate();
    }


    public static class Arrow {
        private PointF startPoint;
        private PointF endPoint;
        private int color;

        public Arrow(PointF startPoint, PointF endPoint, int color) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.color = color;
        }

        public PointF getStartPoint() {
            return startPoint;
        }

        public PointF getEndPoint() {
            return endPoint;
        }

        public int getColor() {
            return color;
        }
    }

    public static class Flag{
        private PointF pointF;
        private int order;
        private int user;
        public Flag(PointF pointF, int order, int user){
            this.pointF=pointF;
            this.order=order;
            this.user=user;
        }

        public PointF getPointF() {
            return pointF;
        }

        public int getOrder() {
            return order;
        }

        public int getUser() {
            return user;
        }
    }
}

