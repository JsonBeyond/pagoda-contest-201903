//package com.pagoda.hdtt.websocket;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.concurrent.ConcurrentHashMap;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//
///**
// * @ClassName BotWebSocket
// * @Description 机器人长连接
// * @Author Alex
// * @CreateDate 1/4/2020 11:04 AM
// * @Version 1.0
// * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
// * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
// */
//@ServerEndpoint("/websocket/{userno}")
//public class BotWebSocket {
//
//    /**
//     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//     */
//    private static int ONLINE_COUNT = 0;
//
//    /**
//     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
//     * 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
//     */
//    private static ConcurrentHashMap<String, BotWebSocket> webSocketSet = new ConcurrentHashMap<>();
//
//    /**
//     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
//     */
//    private Session session;
//
//    /**
//     * 当前发消息的人员编号
//     */
//    private String userno = "";
//
//    /**
//     * 连接建立成功调用的方法
//     *
//     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
//     */
//    @OnOpen
//    public void onOpen(@PathParam(value = "userno") String param, Session session) throws UnsupportedEncodingException {
//        param = URLDecoder.decode(param, "utf-8");
//        System.out.println(param);
//        // 接收到发送消息的人员编号
//        userno = param;
//        this.session = session;
//        // 加入map中
//        webSocketSet.put(param, this);
//        // 在线数加1
//        addOnlineCount();
//        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
//    }
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose() {
//        // 从set中删除
//        webSocketSet.remove(userno);
//        // 在线数减1
//        subOnlineCount();
//        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     *
//     * @param message 客户端发送过来的消息
//     * @param session 可选的参数
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        System.out.println("来自客户端的消息:" + message);
////      session.get
//        String sendUserno = message.split("[|]")[1];
//
//        if (false) {
//            //群发消息
//            sendAll(message);
//        } else {
//            //给指定的人发消息
//            sendToUser(message);
//        }
//    }
//
//
//    /**
//     * 给指定的人发送消息
//     *
//     * @param message 消息
//     */
//    private void sendToUser(String message) {
//        String sendUserno = message.split("[|]")[1];
//        String sendMessage = message.split("[|]")[0];
//        System.out.println(sendUserno);
//        String now = getNowTime();
//        try {
//            if (webSocketSet.get(sendUserno) != null) {
//                if (sendMessage.equals("在吗")) {
//                    webSocketSet.get(sendUserno).sendMessage("在");
//                } else {
//                    webSocketSet.get(sendUserno).sendMessage(now + "用户" + userno + "发来消息：" + " <br/> " + sendMessage);
//                }
//            } else {
//                System.out.println("当前用户不在线");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 给所有人发消息
//     *
//     * @param message 消息
//     */
//    private void sendAll(String message) {
//        String now = getNowTime();
//        String sendMessage = message.split("[|]")[0];
//        // 遍历HashMap
//        for (String key : webSocketSet.keySet()) {
//            try {
//                // 判断接收用户是否是当前发消息的用户
//                if (!userno.equals(key)) {
//                    webSocketSet.get(key).sendMessage(now + "用户" + userno + "发来消息：" + " <br/> " + sendMessage);
//                    System.out.println("key = " + key);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    /**
//     * 获取当前时间
//     */
//    private String getNowTime() {
//        Date date = new Date();
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return format.format(date);
//    }
//
//    /**
//     * 发生错误时调用
//     *
//     * @param session 会话
//     * @param error   异常
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        System.out.println("发生错误");
//        error.printStackTrace();
//    }
//
//    /**
//     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
//     *
//     * @param message 消息
//     */
//    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//        //this.session.getAsyncRemote().sendText(message);
//    }
//
//    public static synchronized int getOnlineCount() {
//        return ONLINE_COUNT;
//    }
//
//    public static synchronized void addOnlineCount() {
//        BotWebSocket.ONLINE_COUNT++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        BotWebSocket.ONLINE_COUNT--;
//    }
//}
