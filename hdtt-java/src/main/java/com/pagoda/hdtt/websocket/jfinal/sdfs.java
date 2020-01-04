//package morality.ws;
//import java.io.IOException;
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//
//
//@ServerEndpoint("/ws/getsingle")
//public class webSocket {
//    private Session session;
//
//    @OnOpen
//    public void onOpen(Session session) {
//        this.session = session;
//    }
//
//    /**
//     * 收到客户端消息时触发
//     * @param relationId
//     * @param userCode
//     * @param message
//     * @return
//     * @throws IOException
//     * @throws InterruptedException
//     */
//    @OnMessage
//    public void onMessage(Session session, String key) throws IOException {
//        //向客户端返回发送过来的消息
//        session.getBasicRemote().sendText(key);
//    }
//
//    /**
//     * 异常时触发
//     * @param relationId
//     * @param userCode
//     * @param session
//     */
//    @OnError
//    public void onError(Throwable throwable,Session session) {}
//
//    /**
//     * 关闭连接时触发
//     * @param relationId
//     * @param userCode
//     * @param session
//     */
//    @OnClose
//    public void onClose(Session session) {}
//}