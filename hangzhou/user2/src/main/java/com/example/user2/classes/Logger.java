package com.example.user2.classes;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static final String TAG = "Logger";
    private static final String LOG_DIRECTORY = "MyAppLogs";

    public static void logToFile(String message) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

        String currentDateAndTime = sdf.format(new Date());

        String fileName = "/log_" + currentDateAndTime + ".txt";

        File logFile = new File(Environment.getExternalStorageDirectory() +fileName);

        File file = new File(Environment.getExternalStorageDirectory(), "hangzhou2.txt");

                boolean success = true;
        if (!file.exists()) {
            success =file.createNewFile();
        }
        if (success) {
            Log.d("TAG", "Folder created successfully");
        } else {
            Log.d("TAG", "Failed to create folder");
        }


        try {
            // 创建日志文件（如果不存在）
            if (!file.exists()) {
                file.createNewFile();
            }
            // 使用追加模式打开文件，这样可以将日志追加到文件末尾
            FileWriter writer = new FileWriter(file, true);
            // 写入日志消息和时间戳
            writer.append(currentDateAndTime + " - " + message + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Log.e(TAG, "Error writing to file: " + e.getMessage());
        }
    }

    public static String getSDPath(Context context) {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist) {
            if (Build.VERSION.SDK_INT >= 29) {
                //Android10之后
                sdDir = context.getExternalFilesDir(null);//获取应用所在根目录/Android/data/your.app.name/file/ 也可以根据沙盒机制传入自己想传的参数，存放在指定目录
            } else {
                sdDir = Environment.getExternalStorageDirectory();// 获取SD卡根目录
            }
        } else {
            sdDir = Environment.getRootDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }
}