package com.example.user1;

import static android.content.ContentValues.TAG;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.user1.classes.DBhelper;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User1ConnectionService extends Service {
    public static final String ACTION_UPDATE_DATABASE = "com.example.ACTION_UPDATE_DATABASE";
    private WebSocketClient webSocketClient;
    public static final String EXTRA_LABEL = "label";
    public static final String EXTRA_CONTENT = "content";
    private Map<String, WebSocket> userConnections = new ConcurrentHashMap<>();

    public static final String ACTION_MESSAGE_RECEIVED = "com.example.app.ACTION_MESSAGE_RECEIVED";
    public static final String EXTRA_MESSAGE = "message";

     public static final String EXTRA_UPDATED_DATA = "updated_data";

    private Intent drawFrameIntent;
    private DBhelper mDBService;
    @Override
    public void onCreate() {
        super.onCreate();


        connectWebSocket();
    }
    // 在 User1ConnectionService 类中定义广播接收器



    private void connectWebSocket() {
        URI uri;
        try {
            // 替换为你的 WebSocket 服务器地址
            uri = new URI("ws://172.20.10.2:8080");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri, new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i("WebSocket", "User1 Connection opened");
                sendUserIdToServer();

            }

            @Override
            public void onMessage(String message) {
                Log.i("WebSocket", "User1 Received message: " + message);
                // 处理收到的消息
                String[] parts = message.split(":");
                if (parts.length == 2) {
                    String label = parts[0];
                    String content = parts[1];


                    // 发送广播
                    if(label.equals("newCenter2")){
                        sendUpdatedCenterBroadcast(label,content);
                    }
                    if(label.equals("conditional2")){
                        sendUpdatedCenterBroadcast(label,content);
                    }
                    if(label.equals("update2")){
                        sendUpdatedDataBroadcast(label, content);
                    }
                    if(label.equals("reset2")){
                        sendUpdatedDataBroadcast(label, content);
                    }
                    if(label.equals("Conflict")){
                        sendUpdatedCenterBroadcast(label,content);
                    }

                }
            }
            private void sendUpdatedCenterBroadcast(final String label, final String content) {
                // 创建意图
                Intent intent = new Intent("com.example.ACTION_DRAW_FRAME");

                // 添加额外的信息
                intent.putExtra(User1ConnectionService.EXTRA_LABEL, label);
                intent.putExtra(User1ConnectionService.EXTRA_UPDATED_DATA, content);
                // 发送局部广播
                LocalBroadcastManager.getInstance(User1ConnectionService.this).sendBroadcast(intent);
            }


            private void sendUpdatedDataBroadcast(final String label, final String content) {
                // 创建意图
                Intent intent = new Intent(User1ConnectionService.ACTION_UPDATE_DATABASE);
                // 添加额外的信息
                intent.putExtra(User1ConnectionService.EXTRA_LABEL, label);
                intent.putExtra(User1ConnectionService.EXTRA_UPDATED_DATA, content);
                // 发送局部广播
                LocalBroadcastManager.getInstance(User1ConnectionService.this).sendBroadcast(intent);
            }


            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i("WebSocket", "User1 Connection closed");
                // 在连接关闭时执行的操作
            }

            @Override
            public void onError(Exception ex) {
                Log.e("WebSocket", "User1 Error: " + ex.getMessage());
                ex.printStackTrace();  // 打印异常堆栈信息
            }
            private void sendUserIdToServer() {
                // 获取用户ID，可以是用户登录后从服务器获取的身份标识
                String userId = "User ID:User1";  // 替换为实际的用户ID
                if (webSocketClient != null && webSocketClient.isOpen()) {
                    webSocketClient.send(userId);
                }
            }

        };

        webSocketClient.connect();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public void sendMessageWithLabel(String label, String message) {


        if (webSocketClient != null && webSocketClient.isOpen()) {
            String data = label + ":" + message; // 在消息前添加标签

            webSocketClient.send(data);
        }
    }

    public class LocalBinder extends Binder {
        public User1ConnectionService getService() {
            return User1ConnectionService.this;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        webSocketClient.close();


    }
}
