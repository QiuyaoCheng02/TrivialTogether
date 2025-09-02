package com.example.trival;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class overviewDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = "places_db";
    private static final int DATABASE_VERSION = 1;
    private Context context; // 声明Context变量

        // 在需要发送消息的地方调用此方法


    public void sendDataToFragment() {
        Intent intent = new Intent("com.example.ACTION_DB_DATA");
        intent.putExtra("data", "update");
        context.sendBroadcast(intent);
    }
    public void sendResetToFragment() {
        Intent intent = new Intent("com.example.ACTION_DB_DATA");
        intent.putExtra("data", "reset");
        context.sendBroadcast(intent);
    }

    // 在广播接收器中接收消息并处理
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 提取消息内容
            String label = intent.getStringExtra(OverviewConnectionService.EXTRA_LABEL);
            String message = intent.getStringExtra(OverviewConnectionService.EXTRA_UPDATED_DATA);
            // 在这里处理接收到的消息，例如更新数据库等操作
                        // 处理消息的具体操作
            // 例如，解析消息并更新数据库
            if (label.equals("reset1")) {
                resetDatabase();
            }
            if (label.equals("reset2")) {
                resetDatabase();
            }
            if(label.equals("update1")){
                update1(message);
            }
            if(label.equals("update2")){
                update2(message);
            }
        }
    };

    public void update1(String message) {
        String[] parts = message.split(";");
        if (parts.length == 2) {
            String name = parts[0];
            int selectedOrder = Integer.parseInt(parts[1]);
            int currentOrder=getPlaceOrder(name);
             if (currentOrder == -1) {
                // >=n 的部分 +1
               increaseOrders(selectedOrder,10);
            }
            else if (selectedOrder > currentOrder) {

                // 如果新选择的顺序大于当前顺序，则将数据库中大于当前顺序，小于等于新选择顺序的所有顺序都减1
               decreaseOrders(currentOrder + 1, selectedOrder);
            } else if (selectedOrder < currentOrder) {
                // 如果新选择的顺序小于当前顺序，则将数据库中大于等于新选择顺序，小于当前顺序的所有顺序都加1
                increaseOrders(selectedOrder, currentOrder - 1);
            }
            updatePlaceOrder(name, selectedOrder);
            updatePlaceUser(name,1);

        }else if (parts.length==3) {
            String name = parts[0];
            deletePlaceOrder(name);

        }
    }
    public void update2(String message) {
        String[] parts = message.split(";");
        if (parts.length == 2) {
            String name = parts[0];
            int selectedOrder = Integer.parseInt(parts[1]);
            int currentOrder=getPlaceOrder(name);

            if (currentOrder == -1) {
                // >=n 的部分 +1
                increaseOrders(selectedOrder,10);
            }
            else if (selectedOrder > currentOrder) {

                // 如果新选择的顺序大于当前顺序，则将数据库中大于当前顺序，小于等于新选择顺序的所有顺序都减1
                decreaseOrders(currentOrder + 1, selectedOrder);
            } else if (selectedOrder < currentOrder) {
                // 如果新选择的顺序小于当前顺序，则将数据库中大于等于新选择顺序，小于当前顺序的所有顺序都加1
                increaseOrders(selectedOrder, currentOrder - 1);
            }
            updatePlaceOrder(name, selectedOrder);
            updatePlaceUser(name,2);

        }else if (parts.length==3) {
            String name = parts[0];
            deletePlaceOrder(name);

        }
    }

    private void updatePlaceUser(String name, int user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER, user);
        // 更新数据库中指定地点的信息
        db.update(TABLE_PLACES, values, COLUMN_NAME + " = ?", new String[]{name});

        db.close();
    }




    // 表名和列名
    private static final String TABLE_PLACES = "places";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ORDER = "orderColumn";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_USER = "user";

    // 创建表的SQL语句
    private static final String CREATE_TABLE_PLACES = "CREATE TABLE IF NOT EXISTS " +
            TABLE_PLACES + "(" +
            COLUMN_NAME + " TEXT PRIMARY KEY," +
            COLUMN_ORDER + " INTEGER," +
            COLUMN_TIME + " DOUBLE,"+
            COLUMN_USER + " INTEGER)" ;




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库升级时的操作，这里先简单处理，直接删除旧表重新创建
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        onCreate(db);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        // 创建表
        db.execSQL(CREATE_TABLE_PLACES);
        // 插入初始数据
        insertDefaultPlaces(db);
    }
    public overviewDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    public void registerBroadcastReceiver() {
        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver, new IntentFilter(OverviewConnectionService.ACTION_UPDATE_DATABASE));
    }
    private void insertDefaultPlaces(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        for (int i = 0; i < DEFAULT_PLACES.length; i++) {
            values.clear();
            values.put(COLUMN_NAME, DEFAULT_PLACES[i]);
            values.put(COLUMN_ORDER, DEFAULT_ORDER);
            values.put(COLUMN_USER, DEFAULT_USER);
            values.put(COLUMN_TIME, DEFAULT_TIMES[i]);
            db.insertWithOnConflict(TABLE_PLACES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        }

    }
    public void resetDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES); // 删除数据库表
        onCreate(db); // 重新创建数据库表
        logAllPlaces();
        sendResetToFragment();
        db.close();
    }

    @SuppressLint("Range")
    public String getPlaceWithOrder(int order) {
        String placeName = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_PLACES, new String[]{COLUMN_NAME},
                COLUMN_ORDER + "=?", new String[]{String.valueOf(order)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                placeName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            }
        }
        return placeName;
    }
    @SuppressLint("Range")
    public int getPlaceOrder(String name) {
        int order = -1; // 默认为-1
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_PLACES, new String[]{COLUMN_ORDER},
                COLUMN_NAME + "=?", new String[]{name}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                order = cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER));
            }
        }
        return order;
    }
    @SuppressLint("Range")
    public int getPlaceUser(String name) {
        int user = -1; // 默认为-1
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_PLACES, new String[]{COLUMN_USER},
                COLUMN_NAME + "=?", new String[]{name}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                user = cursor.getInt(cursor.getColumnIndex(COLUMN_USER));
            }
        }
        return user;
    }
    @SuppressLint("Range")
    public double getPlaceTime(String name) {
        double time = -1.0; // 默认为-1
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_PLACES, new String[]{COLUMN_TIME},
                COLUMN_NAME + "=?", new String[]{name}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                time = cursor.getDouble(cursor.getColumnIndex(COLUMN_TIME));
            }
        }
        return time;
    }

    public int getValidPlaceCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_PLACES + " WHERE " + COLUMN_ORDER + " > 0", null)) {
            if (cursor != null) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        }
        return count;
    }

    public Map<String, Integer> getAllPlaceOrders() {

        Map<String, Integer> orderMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_PLACES, new String[]{COLUMN_NAME, COLUMN_ORDER},
                null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    @SuppressLint("Range") int order = cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER));
                    orderMap.put(name, order);
                } while (cursor.moveToNext());
            }
        }
        return orderMap;
    }

    // 将数据库中大于等于 start，小于等于 end 的所有顺序减1
    public void decreaseOrders(int start, int end) {
        SQLiteDatabase db = getWritableDatabase();

        // 执行 SQL UPDATE 语句来减少 COLUMN_ORDER 列的值
        db.execSQL("UPDATE " + TABLE_PLACES +
                " SET " + COLUMN_ORDER + " = " + COLUMN_ORDER + " - 1 " +
                " WHERE " + COLUMN_ORDER + " >= " + start + " AND " + COLUMN_ORDER + " <= " + end);



        // 关闭数据库连接
        db.close();
    }

    // 将数据库中大于等于 start，小于等于 end 的所有顺序加1
    public void increaseOrders(int start, int end) {
        SQLiteDatabase db = getWritableDatabase();

        // 执行 SQL UPDATE 语句来增加 COLUMN_ORDER 列的值
        db.execSQL("UPDATE " + TABLE_PLACES +
                " SET " + COLUMN_ORDER + " = " + COLUMN_ORDER + " + 1 " +
                " WHERE " + COLUMN_ORDER + " >= " + start + " AND " + COLUMN_ORDER + " <= " + end);


        // 关闭数据库连接
        db.close();
    }

    public void updatePlaceOrder(String name, int order) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER, order); // 更新顺序
        db.update(TABLE_PLACES, values, COLUMN_NAME + " = ?", new String[]{name});
        sendDataToFragment();
        logAllPlaces();
        db.close();
    }
    public void deletePlaceOrder(String name) {
        SQLiteDatabase db = getWritableDatabase();
        int orderToUpdate = getPlaceOrder(name); // 获取选中地点的订单号
        if (orderToUpdate != -1) {
            // 更新选中地点的订单号为-1
            updatePlaceOrder(name, -1);

            // 更新所有大于选中订单号的地点的订单号
            decreaseOrders(orderToUpdate + 1, Integer.MAX_VALUE);
        }
        db.close();
    }

    public void logAllPlaces() {
        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.query(TABLE_PLACES, null, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    @SuppressLint("Range") int order = cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER));
                    @SuppressLint("Range") int user = cursor.getInt(cursor.getColumnIndex(COLUMN_USER));

                } while (cursor.moveToNext());
            }
        }
        db.close();
    }
    @Override
    public void close() {
        super.close();
        // 取消注册广播接收器
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
    }
    // 默认景点数据
    private static final String[] DEFAULT_PLACES = {
            AttractionsInfo.HANSHANSI,
            AttractionsInfo.CANGLANGTING,
            AttractionsInfo.GUANQIANJIE,
            AttractionsInfo.DONGYUAN,
            AttractionsInfo.HUQIU,

            AttractionsInfo.PANMEN,
            AttractionsInfo.HUAIHAIJIE,
            AttractionsInfo.LIUYUAN,
            AttractionsInfo.QILISHANTANG,
            AttractionsInfo.WANGSHIYUAN,

            AttractionsInfo.SUZHOUYUANLIN,
            AttractionsInfo.PUYUAN,
            AttractionsInfo.CHANGYUAN,
            AttractionsInfo.WANHAO,
            AttractionsInfo.HUAQIAOFANDIAN,

            AttractionsInfo.RAMADA,
            AttractionsInfo.YIPU,
            AttractionsInfo.SHIZILIN,
            AttractionsInfo.KUNQU,
            AttractionsInfo.SHUANGTA
    };

    private static final double[] DEFAULT_TIMES = {
            1.5, 2.0, 1.0, 1.5, 1.0,
            1.5, 2.0, 1.0, 1.0, 1.5,
            1.5, 2.0, 1.0, 0, 0,
            0, 1.5, 1.0, 2.0, 1.0
    };


    private static final int DEFAULT_ORDER = -1;
    private static final int DEFAULT_USER = -1;


    public class PlaceData {
        private int order;

        private int user;

        public PlaceData(int order,  int user) {
            this.order = order;

            this.user = user;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }
    }

}


