package com.example.user2.classes.button;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.user2.classes.AttractionsInfo;
import com.example.user2.classes.LargeBitmapView;

import java.util.ArrayList;
import java.util.List;

public class PinButtonView extends View {

    private List<PinButton> pinButtonList = new ArrayList<>();
    private ButtonClickListener buttonClickListener;
    private float mScale = 1.0f;
    private PointF mCenter = new PointF(0.5f, 0.5f);
    float buttonWidth = 200 * mScale;
    float buttonHeight = 100 * mScale;
    private LargeBitmapView largeBitmapView;
    public PinButtonView(Context context) {
        super(context);
    }

    public PinButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    public void setScale(float scale) {
        mScale = scale;
        invalidate(); // Trigger redraw
    }

    public void setCenter(PointF center) {
        mCenter = center;
        invalidate(); // Trigger redraw
    }

    public void addPinButton(PinButton pinButton) {
        pinButtonList.add(pinButton);
        invalidate(); // Trigger redraw
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (PinButton pinButton : pinButtonList) {
            // Calculate button position relative to the image position and scale
            float viewX = (pinButton.getPosition().x - mCenter.x) * mScale + getWidth() / 2;
            float viewY = (pinButton.getPosition().y - mCenter.y) * mScale + getHeight() / 2;
            if(pinButton.getName().equals(AttractionsInfo.QILISHANTANG)||pinButton.getName().equals(AttractionsInfo.HANSHANSI)||pinButton.getName().equals(AttractionsInfo.SUZHOUYUANLIN)
                    ||pinButton.getName().equals(AttractionsInfo.WANHAO)||pinButton.getName().equals(AttractionsInfo.KUNQU)||pinButton.getName().equals(AttractionsInfo.RAMADA)){
                float buttonWidth = 300 * mScale;
                float buttonHeight = 100 * mScale;

                // Draw button at the calculated position with adjusted size
                drawButton(canvas, viewX, viewY, buttonWidth, buttonHeight, Color.RED, 0);
            }else{
                // Calculate button size relative to the image scale
                float buttonWidth = 200 * mScale;
                float buttonHeight = 100 * mScale;

                // Draw button at the calculated position with adjusted size
                drawButton(canvas, viewX, viewY, buttonWidth, buttonHeight, Color.RED, 0);
            }



        }
    }

    private void drawButton(Canvas canvas, float x, float y, float width, float height, int color, int alpha) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(alpha); // 设置透明度

        // 计算矩形的左上角和右下角坐标
        float left = x;
        float top = y - height; // 将左下角y坐标减去高度得到左上角y坐标
        float right = x + width;
        float bottom = y;

        canvas.drawRect(left, top, right, bottom, paint); // Draw a button rectangle
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        // 如果是多点触摸手势，直接返回
        if (event.getPointerCount() > 1) {
            return true;
        }

        // 处理单点触摸手势
        if (action == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();
            handleSingleTouch(touchX, touchY);
        }

        return true;
    }

    private void handleSingleTouch(float touchX, float touchY) {
        // 遍历所有的按钮，检查触摸点是否在某个按钮的范围内
        for (PinButton pinButton : pinButtonList) {
            // 计算按钮位置和大小，考虑大图的缩放和偏移量
            float viewX = (pinButton.getPosition().x - mCenter.x) * mScale + getWidth() / 2;
            float viewY = (pinButton.getPosition().y - mCenter.y) * mScale + getHeight() / 2;
            float buttonWidth, buttonHeight;
            if(pinButton.getName().equals(AttractionsInfo.QILISHANTANG)||pinButton.getName().equals(AttractionsInfo.HANSHANSI)||pinButton.getName().equals(AttractionsInfo.SUZHOUYUANLIN)
                    ||pinButton.getName().equals(AttractionsInfo.WANHAO)||pinButton.getName().equals(AttractionsInfo.KUNQU)||pinButton.getName().equals(AttractionsInfo.RAMADA)){
                buttonWidth = 300 * mScale;
                buttonHeight = 100 * mScale;

            } else {
                buttonWidth = 200 * mScale;
                buttonHeight = 100 * mScale;

            }
            // 检查触摸点是否在按钮的范围内
            if (isTouchInsideButton(touchX, touchY, viewX, viewY, buttonWidth, buttonHeight)) {
                // 如果触摸点在按钮内，处理按钮的点击事件
                handleButtonTouch(pinButton);
                return; // 返回，避免处理多个按钮的点击事件
            }
        }
    }
    // 检查触摸点是否在按钮的范围内
    private boolean isTouchInsideButton(float touchX, float touchY, float buttonX, float buttonY, float buttonWidth, float buttonHeight) {
        // 检查触摸点是否在按钮的范围内
        return touchX >= buttonX && touchX <= buttonX + buttonWidth && touchY >= buttonY - buttonHeight && touchY <= buttonY;
    }


    // 处理按钮的点击事件
    private void handleButtonTouch(PinButton pinButton) {
        // 处理按钮的点击事件，例如显示相应的对话框等

        // 创建一个临时的 View 对象来传递给 onClick 方法
        View tempView = new View(getContext());
        tempView.setTag(pinButton); // 将 PinButton 对象设置为标记
        // 调用 ButtonClickListener 的 onClick 方法，并传递相应的 View
        buttonClickListener.onClick(tempView);

    }
    // AsyncTask to perform drawing operations in background
    private static class DrawingTask extends AsyncTask<Void, Void, Void> {
        private PinButtonView view;
        private List<PinButton> buttons;

        DrawingTask(PinButtonView view, List<PinButton> buttons) {
            this.view = view;
            this.buttons = buttons;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Simulate long-running drawing operations
            // Here you can perform complex calculations or data loading
            // For demonstration purposes, we just sleep for a while
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Update UI on the main thread after background task is finished
            for (PinButton button : buttons) {
                view.addPinButton(button);
            }
        }
    }

    // Method to start drawing task
    public void startDrawingTask(List<PinButton> buttons) {
        new DrawingTask(this, buttons).execute();
    }
}