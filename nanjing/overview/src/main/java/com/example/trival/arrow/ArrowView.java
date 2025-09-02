package com.example.trival.arrow;

import static android.content.ContentValues.TAG;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.trival.R;
import com.example.trival.overviewDBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArrowView extends View {
    private List<Arrow> arrowList = new ArrayList<>();
    private float mScale = (float) 0.574;
    private PointF mCenter = new PointF(0.5f, 0.5f);
    private Map<String, PointF> positionInfo;
    private Paint paint;
    private Path path;

    private List<Flag> flagList = new ArrayList<>();
    private Paint flagPaint;
    private Paint polePaint;
    private Paint textPaint;
    private int user;
    private int number;
    private static final int FLAGPOLE_LENGTH = 200;

    private double allocatedTimeInHours = 0; // Track allocated time in hours
    private double remainingTimeInHours = 7; // Total available time in hours
    private static final int TOTAL_TIME_IN_HOURS = 7; // Total available time in hours

    private Paint allocatedTimePaint;
    private Paint remainingTimePaint;
    private RectF allocatedProgressBarRect;
    private RectF remainingProgressBarRect;
    private Paint timePaint;
    private float cornerRadius = 20; // 圆角矩形的圆角半径
    private int shadowColor = Color.parseColor("#44000000"); // 阴影颜色
    private float shadowRadius = 10; // 阴影半径
    private float shadowDx = 0; // 阴影水平偏移
    private float shadowDy = 5; // 阴影垂直偏移

    private float elevation = 10; // 阴影的高度
    private ObjectAnimator elevationAnimator;
    public overviewDBHelper dbHelper;

    public ArrowView(Context context) {

        super(context);
        dbHelper = new overviewDBHelper(context);
        init();
    }

    public ArrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dbHelper = new overviewDBHelper(context);
        init();
    }

    public ArrowView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        dbHelper = new overviewDBHelper(context);
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

    public void addArrow(Arrow arrow) {
        arrowList.add(arrow);
        invalidate(); // Trigger redraw
    }

    public void addArrow(String startPointName, String endPointName) {
        PointF startPoint = positionInfo.get(startPointName);
        PointF endPoint = positionInfo.get(endPointName);
        if (startPoint != null && endPoint != null) {
            Arrow arrow = new Arrow(startPoint, endPoint, Color.BLACK); // You can set arrow color as per your requirement
            addArrow(arrow);
        }
    }

    public void setPositionInfo(Map<String, PointF> positionInfo) {
        this.positionInfo = positionInfo;
    }


    //flag
    public void addFlag(Flag flag) {
        flagList.add(flag);
        invalidate(); // 触发重绘
    }

    public void addFlag(String name, int order, int user, double time) {
        PointF pointF=positionInfo.get(name);
        Flag flag = new Flag(pointF, order, user, time);
        addFlag(flag);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Arrow arrow : arrowList) {
            // Calculate arrow position relative to the view's position and scale
            float viewStartX = (arrow.getStartPoint().x - mCenter.x) * mScale ;
            float viewStartY = (arrow.getStartPoint().y - mCenter.y) * mScale ;
            float viewEndX = (arrow.getEndPoint().x - mCenter.x) * mScale ;
            float viewEndY = (arrow.getEndPoint().y - mCenter.y) * mScale ;

            // Draw arrow at the calculated position
            drawArrow(canvas, viewStartX, viewStartY, viewEndX, viewEndY, arrow.getColor());
        }
        for (Flag flag : flagList) {
            // 计算旗杆的起始点位置
            float poleStartX = (flag.getPointF().x - mCenter.x) * mScale;
            float poleStartY = (flag.getPointF().y - mCenter.y) * mScale ;

            // 计算旗杆的终点位置
            float poleEndX = poleStartX;
            float poleEndY = poleStartY - FLAGPOLE_LENGTH;
                        drawFlag(canvas, poleEndX, poleStartY, flag.getOrder(), flag.getUser());
        }
        // Draw progress bars
        drawProgressBar(canvas, allocatedTimePaint);
        drawProgressBar(canvas, remainingTimePaint);

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

        allocatedTimePaint = new Paint();
        allocatedTimePaint.setColor(Color.WHITE);
        allocatedTimePaint.setStyle(Paint.Style.FILL);
        allocatedTimePaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor); // 添加阴影

        // Initialize paint for remaining time
        remainingTimePaint = new Paint();
        remainingTimePaint.setColor(ContextCompat.getColor(this.getContext(), R.color.blue));
        remainingTimePaint.setStyle(Paint.Style.FILL);
        remainingTimePaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor); // 添加阴影


        timePaint = new Paint();
        timePaint.setColor(Color.BLACK);
        timePaint.setTextSize(50);
    }

    private void drawProgressBar(Canvas canvas,Paint paint) {
        // Calculate progress bar dimensions
        String text="";
        float totalWidth = getWidth();
        float totalHeight = 100;
        float allocatedWidth = totalWidth * ((float) allocatedTimeInHours / TOTAL_TIME_IN_HOURS);
        float remainingWidth = totalWidth - allocatedWidth;
        /*progressBarRect.set(0, getHeight()-100, getWidth(), getHeight());
        canvas.drawRoundRect(progressBarRect, cornerRadius, cornerRadius, paint);*/
        canvas.drawRect(0, getHeight()+100, getWidth(), getHeight()-100, allocatedTimePaint);

        // Draw remaining time portion
        canvas.drawRect(allocatedWidth, getHeight()+100, getWidth(), getHeight()-100, remainingTimePaint);


        if(remainingTimeInHours==1){
            text=remainingTimeInHours+" hour left";
        }else{
            text=remainingTimeInHours+" hours left";
        }

        // Calculate text width and height
        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);
        float textWidth = paint.measureText(text);
        float textHeight = textBounds.height();
        // Calculate text position
        float textX = (getWidth() - textWidth*2) / 2;
        float textY = getHeight()-textHeight*3 ;

        canvas.drawText(text, textX, textY, timePaint);
    }



    private void drawArrow(Canvas canvas, float startX, float startY, float endX, float endY, int color) {
        // Your arrow drawing logic remains the same as before

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

        Map<String, Integer> orderMap = dbHelper.getAllPlaceOrders(); // 获取所有景点及其顺序的映射

        // 清空现有的箭头列表
        arrowList.clear();

        // 遍历数据库中的顺序信息，添加箭头
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String startPointName = entry.getKey();
            int startOrder = entry.getValue();

            // 如果顺序为-1，表示该景点未被添加到旅游计划里，不需要绘制箭头
            if (startOrder == -1) {
                continue;
            }

            // 查询下一个顺序的景点
            String endPointName = dbHelper.getPlaceWithOrder(startOrder + 1);

            // 如果下一个景点存在，则添加箭头
            if (endPointName != null) {
                addArrow(startPointName, endPointName);
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
        if(user==1){
            flagPaint.setColor(Color.RED);
            textPaint.setColor(Color.WHITE);
        }if(user==2){
            flagPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.yellow));
            textPaint.setColor(Color.BLACK);
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
        //overviewDBHelper dbHelper = new overviewDBHelper(context);
        Map<String, Integer> orderMap = dbHelper.getAllPlaceOrders(); // 获取所有景点及其顺序的映射

        flagList.clear();
        allocatedTimeInHours=0.0;

        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            String startPointName = entry.getKey();
            int startOrder = entry.getValue();
            int user=dbHelper.getPlaceUser(startPointName);
            double time=dbHelper.getPlaceTime(startPointName);
            // 如果顺序为-1，表示该景点未被添加到旅游计划里，不需要绘制箭头
            if (startOrder == -1) {
                continue;
            }

            addFlag(startPointName,startOrder,user,time);
            allocatedTimeInHours+=time;
            // 查询下一个顺序的景点
            String endPointName = dbHelper.getPlaceWithOrder(startOrder + 1);

            // 如果下一个景点存在，则添加箭头
            if (endPointName != null) {
                addArrow(startPointName, endPointName);
            }
        }
        updateTimes(allocatedTimeInHours);
        // 通知视图重绘
        invalidate();
    }
    public void updateTimes(double allocatedTimeInHours) {
        this.allocatedTimeInHours = allocatedTimeInHours;
        this.remainingTimeInHours = TOTAL_TIME_IN_HOURS - allocatedTimeInHours;

        // Ensure remaining time does not go below 0
        remainingTimeInHours = Math.max(remainingTimeInHours, 0);

        invalidate(); // Trigger redraw
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
        private double time;
        public Flag(PointF pointF, int order, int user, double time){
            this.pointF=pointF;
            this.order=order;
            this.user=user;
            this.time=time;
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

        public double getTime() {
            return time;
        }
    }
}