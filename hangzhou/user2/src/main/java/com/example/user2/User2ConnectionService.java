package com.example.user2;

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

import com.example.user2.classes.DBhelper;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class User2ConnectionService extends Service {
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
                drawFrameIntent = new Intent("com.example.ACTION_DRAW_FRAME");

        connectWebSocket();
    }
    // 在 User2ConnectionService 类中定义广播接收器


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
                Log.i("WebSocket", "User2 Connection opened");
                sendUserIdToServer();
            }

            @Override
            public void onMessage(String message) {
                Log.i("WebSocket", "User2 Received message: " + message);
                // 处理收到的消息
                String[] parts = message.split(":");
                if (parts.length == 2) {
                    String label = parts[0];
                    String content = parts[1];

                                       // 发送广播
                    if (label.equals("newCenter1")) {
                        sendUpdatedCenterBroadcast(label,content);
                    }
                    if (label.equals("conditional1")) {
                        sendUpdatedCenterBroadcast(label,content);
                    }
                    if (label.equals("update1")) {
                        sendUpdatedDataBroadcast(label, content);
                    }
                    if(label.equals("reset1")){
                        sendUpdatedDataBroadcast(label, content);

                    }
                    if(label.equals("Conflict")){
                        sendUpdatedCenterBroadcast(label,content);
                        //sendUpdatedDataBroadcast(label, content);
                    }
                    if(label.equals("Conflict2")){
                        //sendUpdatedCenterBroadcast(label,content);
                        //sendUpdatedDataBroadcast(label, content);
                    }
                }



            }

            private void sendUpdatedDataBroadcast(final String label, final String content) {

                // 创建意图
                Intent intent = new Intent(User2ConnectionService.ACTION_UPDATE_DATABASE);
                // 添加额外的信息
                intent.putExtra(User2ConnectionService.EXTRA_LABEL, label);
                intent.putExtra(User2ConnectionService.EXTRA_UPDATED_DATA, content);
                // 发送局部广播
                LocalBroadcastManager.getInstance(User2ConnectionService.this).sendBroadcast(intent);
            }
            private void sendUpdatedCenterBroadcast(final String label, final String content) {
                // 创建意图
                Intent intent = new Intent("com.example.ACTION_DRAW_FRAME");

                // 添加额外的信息
                intent.putExtra(User2ConnectionService.EXTRA_LABEL, label);
                intent.putExtra(User2ConnectionService.EXTRA_UPDATED_DATA, content);
                // 发送局部广播
                LocalBroadcastManager.getInstance(User2ConnectionService.this).sendBroadcast(intent);
            }


            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i("WebSocket", "User1 Connection closed");
                // 在连接关闭时执行的操作
            }

            @Override
            public void onError(Exception ex) {
                Log.e("WebSocket", "User2 Error: " + ex.getMessage());
                ex.printStackTrace();  // 打印异常堆栈信息
            }
            private void sendUserIdToServer() {
                // 获取用户ID，可以是用户登录后从服务器获取的身份标识
                String userId = "User ID:User2";  // 替换为实际的用户ID
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
        public User2ConnectionService getService() {
            return User2ConnectionService.this;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }
}
