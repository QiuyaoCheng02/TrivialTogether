package com.example.trival;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ThumbnailView extends View {

    private Paint mPaint;
    private Paint yPaint;
    private RectF mFrameRect;
    private RectF yFrameRect;
    private Bitmap mThumbnailBitmap;
    private List<MyRectF> frameRectList;
    private List<MyRectF> mframeRectList;
    public String timestamp;
    public ThumbnailView(Context context) {
        super(context);
        init();
    }

    public ThumbnailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThumbnailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private static final float MIN_DISTANCE_BETWEEN_FRAMES = 1; // 设置矩形之间的最小距离

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED); // Red color
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5); // Set stroke width as needed
        mFrameRect = new RectF();

        yPaint = new Paint();
        yPaint.setColor(ContextCompat.getColor(this.getContext(), R.color.yellow));
        yPaint.setStyle(Paint.Style.STROKE);
        yPaint.setStrokeWidth(5); // Set stroke width as needed
        yFrameRect = new RectF();
        frameRectList = new ArrayList<>();
        mframeRectList = new ArrayList<>();
    }

    public void setThumbnailBitmap(Bitmap bitmap) {
        mThumbnailBitmap = bitmap;
        invalidate(); // Trigger redraw
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
        MyRectF newFrameRect = new MyRectF(left, top, right, bottom);

        long currentTimeMillis = System.currentTimeMillis();

        // 将时间戳添加到矩形对象中
        newFrameRect.timestamp = String.valueOf(currentTimeMillis);
        // 如果集合为空，或者与最后一个矩形的距离大于等于指定阈值，则添加新的矩形
        if (mframeRectList.isEmpty() || distanceBetweenRects(mframeRectList.get(mframeRectList.size() - 1), newFrameRect) >= MIN_DISTANCE_BETWEEN_FRAMES) {
            mframeRectList.add(newFrameRect);
        }

        invalidate();
    }
    private float distanceBetweenRects(RectF rect1, RectF rect2) {
        float centerX1 = rect1.centerX();
        float centerY1 = rect1.centerY();
        float centerX2 = rect2.centerX();
        float centerY2 = rect2.centerY();
        return (float) Math.sqrt(Math.pow(centerX2 - centerX1, 2) + Math.pow(centerY2 - centerY1, 2));
    }
    public void yRrefreshFrame(float left, float top, float right, float bottom) {

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
        yFrameRect.set(left, top, right, bottom);
        MyRectF newFrameRect = new MyRectF(left, top, right, bottom);

        // 获取当前时间
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        int second = currentTime.get(Calendar.SECOND);

        // 将时间戳添加到矩形对象中
        newFrameRect.timestamp = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second);

        // 如果集合为空，或者与最后一个矩形的距离大于等于指定阈值，则添加新的矩形
        if (frameRectList.isEmpty() || distanceBetweenRects(frameRectList.get(frameRectList.size() - 1), newFrameRect) >= MIN_DISTANCE_BETWEEN_FRAMES) {
            frameRectList.add(newFrameRect);
        }

        invalidate();
    }
    public void setFrameRect(RectF rect) {
        mFrameRect.set(rect);
        yFrameRect.set(rect);
        invalidate(); // Trigger redraw
    }

    public void saveThumbnailImage() {

        // 获取要设置为背景的 drawable 图片资源
        Drawable backgroundDrawable = getResources().getDrawable(R.drawable.nanjing);

        // 确定要设置的特定图片大小
        int bitmapWidth = backgroundDrawable.getIntrinsicWidth();
        int bitmapHeight = backgroundDrawable.getIntrinsicHeight();

      /*  // 创建新的 Bitmap 对象，指定特定图片大小
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        // 创建新的 Canvas 对象，将 Bitmap 作为绘制的目标
        Canvas canvas = new Canvas(bitmap);

        // 在 Canvas 上绘制背景图片
        backgroundDrawable.setBounds(0, 0, bitmapWidth, bitmapHeight);
        backgroundDrawable.draw(canvas);
*/  // 创建新的 Bitmap 对象
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        // 创建新的 Canvas 对象，将 Bitmap 作为绘制的目标
        Canvas canvas = new Canvas(bitmap);

        // 在 Canvas 上绘制背景或其他内容（如果需要）
        canvas.drawColor(Color.WHITE); // 绘制白色背景

        // 绘制所有方框
        for (RectF frameRect : mframeRectList) {
            if (frameRect != null) {
                Path path = new Path();
                path.addRect(frameRect, Path.Direction.CW);
                canvas.drawPath(path, mPaint);
            }
        }

        // 保存生成的位图到系统中
        saveBitmapToFile(bitmap);
    }

    public void saveLog(){

       saveToFile(mframeRectList);
       saveToFile1(frameRectList);

    }

    private void saveBitmapToFile(Bitmap bitmap) {

        // 创建保存图片的文件
        File file = new File(Environment.getExternalStorageDirectory(), "thumbnail_image.png");

        try {
            // 创建文件输出流
            FileOutputStream fos = new FileOutputStream(file);

            // 将 Bitmap 压缩为 PNG 格式并写入输出流
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            // 关闭输出流
            fos.flush();
            fos.close();
                   } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveToFile(List<MyRectF> frameRectList) {
        // 检查要保存到的文件路径
        File file = new File(Environment.getExternalStorageDirectory(), "user1_example.txt");

        try {
            // 创建文件的输出流
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

            // 遍历 frameRectList 中的每个 MyRectF，并将其坐标信息和时间戳写入文件
            for (MyRectF rect : frameRectList) {
                String timestamp = rect.timestamp != null ? rect.timestamp : "No timestamp"; // 如果没有时间戳，则写入默认值
                writer.write(rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom + "," + timestamp);
                writer.newLine(); // 写入换行符，方便下次读取
            }

            // 关闭输出流
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToFile1(List<MyRectF> frameRectList) {
        // 检查要保存到的文件路径
        File file = new File(Environment.getExternalStorageDirectory(), "user1_example.txt");

        try {
            // 创建文件的输出流
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

            // 遍历 frameRectList 中的每个 MyRectF，并将其坐标信息和时间戳写入文件
            for (MyRectF rect : frameRectList) {
                String timestamp = rect.timestamp != null ? rect.timestamp : "No timestamp"; // 如果没有时间戳，则写入默认值
                writer.write(rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom + "," + timestamp);
                writer.newLine();
            }

            // 关闭输出流
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw background image
        if (mThumbnailBitmap != null) {
            // Resize the bitmap to match the view size
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(mThumbnailBitmap, getWidth(), getHeight(), true);
            canvas.drawBitmap(scaledBitmap, 0, 0, null);
        }

        // Draw red frame
        if (mFrameRect != null) {
            Path path = new Path();
            path.addRect(mFrameRect, Path.Direction.CW);
            canvas.drawPath(path, mPaint);
        }
        if (yFrameRect != null) {
            Path path = new Path();
            path.addRect(yFrameRect, Path.Direction.CW);
            canvas.drawPath(path, yPaint);
        }


        // Print the contents of the frameRectList

    }
    public class MyRectF extends RectF {
        public String timestamp;

        public MyRectF(float left, float top, float right, float bottom) {
            super(left, top, right, bottom);
        }
    }


}
