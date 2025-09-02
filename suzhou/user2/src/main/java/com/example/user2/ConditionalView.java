package com.example.user2;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.user1.R;

public class ConditionalView extends View {

    private Paint paint;
    private RectF FrameRect;
    private float deviceAScale;
    private float deviceBScale;
    private PointF deviceACenter;
    private PointF deviceBCenter;
    private float deviceBViewLeft;
    private float deviceBViewTop;
    private float deviceBViewRight;
    private float deviceBViewBottom;
    private float mScale = 1.0f;
    private PointF mCenter = new PointF(0.5f, 0.5f);

    public ConditionalView(Context context) {
        super(context);
        init();

    }
    public ConditionalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ConditionalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void setScale(float scale) {
        mScale = scale;
        invalidate();
    }

    public void setCenter(PointF center) {
        mCenter = center;
        invalidate();
    }
    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(128);
        FrameRect = new RectF();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw red frame
        if (FrameRect != null) {
            Path path = new Path();
            path.addRect(FrameRect, Path.Direction.CW);
            canvas.drawPath(path, paint);
        }

    }


    public void refreshFrame(float deviceBViewLeft, float deviceBViewTop, float deviceBViewRight, float deviceBViewBottom){

        this.deviceBViewLeft=(deviceBViewLeft - mCenter.x) * mScale+getWidth()/2;
        this.deviceBViewTop=(deviceBViewTop - mCenter.y) * mScale+getHeight()/2;
        this.deviceBViewRight=(deviceBViewRight - mCenter.x) * mScale+getWidth()/2;
        this.deviceBViewBottom=(deviceBViewBottom - mCenter.y) * mScale+getHeight()/2;
             refreshFrame1();

    }

    public void refreshFrame1() {
        FrameRect.set(deviceBViewLeft,deviceBViewTop,deviceBViewRight,deviceBViewBottom);
        invalidate();
    }
}

