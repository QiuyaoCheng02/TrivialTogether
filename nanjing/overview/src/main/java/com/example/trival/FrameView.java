package com.example.trival;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class FrameView extends View {

    private Paint mPaint;
    private RectF mFrameRect;

    public FrameView(Context context) {
        super(context);
        init();

    }

    public FrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public FrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(0xFFFF0000); // Red color
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5); // Set stroke width as needed
        mFrameRect = new RectF();
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void refreshFrame(float left, float top, float right, float bottom) {

        if (left < 0) {
            left = 0;
        }
        if (top < 0) {
            top = 0;
        }
        if (right > getWidth()) {
            right = getWidth();
        }
        if (bottom > getHeight()) {
            bottom = getHeight();
        }
        mFrameRect.set(left, top, right, bottom);
        invalidate();
    }

    public void setFrameRect(RectF rect) {
        mFrameRect.set(rect);
        invalidate(); // Trigger redraw
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw red frame
        if (mFrameRect != null) {

            Path path = new Path();
            path.addRect(mFrameRect, Path.Direction.CW);
            canvas.drawPath(path, mPaint);
        }
        // 绘制一个简单的矩形
        int width = getWidth();
        int height = getHeight();
        int left = width / 4;
        int top = height / 4;
        int right = width * 3 / 4;
        int bottom = height * 3 / 4;

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        canvas.drawRect(left, top, right, bottom, paint);
    }
}
