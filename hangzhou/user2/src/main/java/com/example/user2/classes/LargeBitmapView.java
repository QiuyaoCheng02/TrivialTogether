package com.example.user2.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;



public class LargeBitmapView extends SubsamplingScaleImageView {

    private Bitmap bitmapToDraw;

    public LargeBitmapView(Context context) {
        this(context, null);
    }

    public LargeBitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBitmapToDraw(Bitmap bitmap) {
        bitmapToDraw = bitmap;
        invalidate(); // Trigger redraw
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw bitmap
        if (bitmapToDraw != null) {
            canvas.drawBitmap(bitmapToDraw, 0, 0, null);
        }
    }
}