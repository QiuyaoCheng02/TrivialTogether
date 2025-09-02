package com.example.trival;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OverviewConnectionService extends Service {
    private static final String TAG = "OverviewConnectionService";

    public static final String EXTRA_LABEL = "label";
    public static final String EXTRA_CONTENT = "content";
    private Map<String, WebSocket> userConnections = new ConcurrentHashMap<>();

    public static final String ACTION_MESSAGE_RECEIVED = "com.example.app.ACTION_MESSAGE_RECEIVED";
    public static final String EXTRA_MESSAGE = "message";

    public static final String ACTION_UPDATE_DATABASE = "com.example.app.ACTION_UPDATE_DATABASE";
    public static final String EXTRA_UPDATED_DATA = "updated_data";
    private int times=0;

    public OverviewConnectionService() {
    }

    private WebSocketServer webSocketServer;


    @Override
    public void onCreate() {
        super.onCreate();
        startWebSocketServer();
    }

    private void startWebSocketServer() {
        webSocketServer = new WebSocketServer(new InetSocketAddress(8080)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {

                //String userId = getUserIdFromHandshake(handshake);
                Log.i("WebSocket", "Connection opened for User " );
                // 保存连接和用户ID的关联关系，以便将消息发送给特定用户
                //userConnections.put(userId, conn);

                // 在连接成功后发送一条欢迎消息
                //sendWelcomeMessage(conn, userId);

            }
            private void sendWelcomeMessage(WebSocket conn, String userId) {
                try {
                    String welcomeMessage = "Welcome, User " + userId + "!";
                    conn.send(welcomeMessage);
                    Log.i("WebSocket", "Connection opened for "+userId );
                    String message="Connection opened for "+userId;
                    sendMessageBroadcast("popup",message);
                    Log.i("WebSocket", "Sent welcome message: " + welcomeMessage);
                } catch (Exception e) {
                    Log.e("WebSocket", "Error sending welcome message: " + e.getMessage());
                }
            }
            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                String userId = getUserIdByConnection(conn);
                if (userId != null) {

                    String message="Connection closed for "+userId;
                    sendMessageBroadcast("popup",message);
                    System.out.println("Connection closed for User " + userId);
                    userConnections.remove(userId);
                }
            }
            private static final long TIME_THRESHOLD = 1000;
            public String content1;
            public String content2;
            @Override
            public void onMessage(WebSocket conn, String message) {

                times=0;
                // 解析标签和消息内容
                String[] parts = message.split(":");
                if (parts.length == 2) {
                    String label = parts[0];
                    String content = parts[1];
                    if (label.equals("User ID")) {
                        String userId=content;
                        userConnections.put(userId, conn);
                        // 在连接成功后发送一条欢迎消息
                        sendWelcomeMessage(conn, userId);
                    }

                    // 发送广播
                    if(label.equals("newCenter1")){
                        sendMessageBroadcast(label,content);
                        sendMessageToUser("User2", message);
                    }
                    if(label.equals("newCenter2")){
                        sendMessageBroadcast(label,content);
                        sendMessageToUser("User1", message);
                    }
                    if(label.equals("conditional2")){

                        sendMessageToUser("User1", message);
                    }
                    if(label.equals("conditional1")){

                        sendMessageToUser("User2", message);
                    }

                   /* if(label.equals("update1")){
                        sendUpdatedDataBroadcast(label, content);
                        sendMessageToUser("User2", message);
                    }*/if(label.equals("reset1")){
                        sendUpdatedDataBroadcast(label, content);
                        sendMessageToUser("User2", message);
                    }
                  /*  if(label.equals("update2")){
                        sendUpdatedDataBroadcast(label, content);
                        sendMessageToUser("User1", message);
                    }*/if(label.equals("reset2")){
                        sendUpdatedDataBroadcast(label, content);
                        sendMessageToUser("User1", message);
                    }
                    long time1 = System.currentTimeMillis();

                    if(label.equals("update1") ){
                        long time2 = getLastUpdateTime("update2");
                        lastUpdateTimeMap.put("update1", time1);
                        Log.d(TAG, "onMessage: "+message);
                        content1=content;
                        if (Math.abs(time1 - time2) <= TIME_THRESHOLD) {
                            // 时间接近，执行操作


                            Log.d(TAG, "onMessage: same1");
                            sendMessageToUser("User1", "Conflict:"+content2);
                            sendMessageToUser("User2", "Conflict:"+content1);



                        }
                        sendUpdatedDataBroadcast(label, content);
                        sendMessageToUser("User2", message);
                    }

                    if(label.equals("update2")){
                        long time2 = getLastUpdateTime("update1");
                        lastUpdateTimeMap.put("update2", time1);
                        content2=content;
                        if (Math.abs(time1 - time2) <= TIME_THRESHOLD) {                           // 时间接近，执行操作

                            Log.d(TAG, "onMessage: same2");
                            sendMessageToUser("User2", "Conflict:"+content1);
                            sendMessageToUser("User1", "Conflict:"+content2);



                        }
                        // 时间不接近，仅执行当前消息的操作
                        sendUpdatedDataBroadcast(label, content);
                        sendMessageToUser("User1", message);

                    }

                }
            }
            private Map<String, Long> lastUpdateTimeMap = new HashMap<>();

            // 获取最后一次更新时间的方法
            private long getLastUpdateTime(String label) {
                if (lastUpdateTimeMap.containsKey(label)) {
                    return lastUpdateTimeMap.get(label);
                } else {
                    // 如果没有找到对应标签的时间戳，则返回一个默认值或者抛出异常，具体取决于您的需求
                    return 0; // 默认值为0
                }
            }
            public boolean isSame(String message1, String message2) {
                String[] parts1 = message1.split(";");
                String[] parts2 = message2.split(";");
                int length1=parts1.length;
                int length2=parts2.length;
                if (length1==length2&&length2==3) {
                    String name1 = parts1[0];
                    int selectedOrder1 = Integer.parseInt(parts1[1]);

                    String name2 = parts2[0];
                    int selectedOrder2 = Integer.parseInt(parts2[1]);

                    if(name1.equals(name2)||selectedOrder1==selectedOrder2){
                        return true;
                    }}
                else if (length1==length2&&length2==4) {
                    String name1 = parts1[0];
                    String name2 = parts2[0];
                    if(name1.equals(name2)){
                        return true;
                    }

                }
                return false;
            }

            private void sendMessageToUser(String userId, String message) {

                WebSocket conn = userConnections.get(userId);

                if (conn != null) {
                    try {
                        conn.send(message);
                        Log.i("WebSocket", "Message sent to User " + userId + ": " + message);
                    } catch (Exception e) {
                        Log.e("WebSocket", "Error sending message to User " + userId + ": " + e.getMessage());
                    }
                } else {
                    Log.e("WebSocket", "User " + userId + " is not connected.");
                }
            }




            //发送给本地数据库
            private void sendUpdatedDataBroadcast(final String label, final String content) {

                if(times==0){
                    // 创建意图
                    Intent intent = new Intent(OverviewConnectionService.ACTION_UPDATE_DATABASE);
                    // 添加额外的信息
                    intent.putExtra(OverviewConnectionService.EXTRA_LABEL, label);
                    intent.putExtra(OverviewConnectionService.EXTRA_UPDATED_DATA, content);
                    // 发送局部广播
                    LocalBroadcastManager.getInstance(OverviewConnectionService.this).sendBroadcast(intent);

                    times+=1;
                                   }


            }


            @Override
            public void onError(WebSocket conn, Exception ex) {
                Log.e("WebSocket", "Error: " + ex.getMessage());
               String message="Error: " + ex.getMessage();
               sendMessageBroadcast("popup",message);

            }

            @Override
            public void onStart() {
                Log.i("WebSocket", "Server started on " +":" + getPort());
                String message="Server started on " +":" + getPort();
                sendMessageBroadcast("popup",message);
            }
            private String getUserIdFromHandshake(ClientHandshake handshake) {

                return UUID.randomUUID().toString();
            }
            private String getUserIdByConnection(WebSocket conn) {
                // 根据连接查找用户ID
                for (Map.Entry<String, WebSocket> entry : userConnections.entrySet()) {
                    if (entry.getValue().equals(conn)) {
                        String userId = entry.getKey();
                                               return userId;
                    }
                }

                return null;
            }

        };

        webSocketServer.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    public class LocalBinder extends Binder {
        public OverviewConnectionService getService() {
            return OverviewConnectionService.this;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            webSocketServer.stop();

            String message="service stopped";
            sendMessageBroadcast("stop","stop");
            sendMessageBroadcast("popup",message);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void sendMessageBroadcast(final String label, final String content) {
        // 创建意图
        Intent intent = new Intent(OverviewConnectionService.ACTION_MESSAGE_RECEIVED);
        // 添加额外的信息
        intent.putExtra(OverviewConnectionService.EXTRA_LABEL, label);
        intent.putExtra(OverviewConnectionService.EXTRA_CONTENT, content);
        // 发送局部广播
        LocalBroadcastManager.getInstance(OverviewConnectionService.this).sendBroadcast(intent);
    }
}