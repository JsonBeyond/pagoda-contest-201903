<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebSocket</title>
</head>
<body>
Welcome<br/>
接收人名称：<input id="jsname" type="text"/>
内容：<input id="text" type="text"/>
<button οnclick="send()">发送消息</button>
<hr/>
<!--userno:发送消息人的编号-->
自己名称：<div id="userno">测试人员1</div>
<button οnclick="closeWebSocket()">关闭WebSocket连接</button>
<hr/>
<div id="message"></div>
</body>

<script type="text/javascript">
    var lockReconnect = false;  //避免ws重复连接
    var userno=document.getElementById('userno').innerHTML;//用户编号
    var ws = null;          // 判断当前浏览器是否支持WebSocket
    var wsUrl = "ws://192.168.0.113:8080/webSocketTest/websocket/"+userno;
    createWebSocket(wsUrl);   //连接ws


    function createWebSocket(url) {
        try{
            if('WebSocket' in window){
                ws = new WebSocket(url);
            }else if('MozWebSocket' in window){
                ws = new MozWebSocket(url);
            }else{
                console.log("您的浏览器不支持websocket协议,建议使用新版谷歌、火狐等浏览器，请勿使用IE10以下浏览器，360浏览器请使用极速模式，不要使用兼容模式！");
            }
            initEventHandle();
        }catch(e){
            reconnect(url);
            console.log(e);
        }
    }


    function initEventHandle() {
        ws.onclose = function () {
            reconnect(wsUrl);
            console.log("WebSocket连接关闭!"+new Date().toUTCString());
        };
        ws.onerror = function () {
            reconnect(wsUrl);
            console.log("WebSocket连接发生错误!");
            setMessageInnerHTML("WebSocket连接发生错误");
        };
        ws.onopen = function () {
            heartCheck.reset().start();      //心跳检测重置
            console.log("WebSocket连接成功!"+new Date().toUTCString());
            setMessageInnerHTML("WebSocket连接成功");
        };
        ws.onmessage = function (event) {    //如果获取到消息，心跳检测重置
            heartCheck.reset().start();      //拿到任何消息都说明当前连接是正常的
            console.log("WebSocket收到消息啦:" +event.data);
            if(event.data!="在"){
                setMessageInnerHTML(event.data);
            }

        }
    }
    // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function() {
        ws.close();
    }

    function reconnect(url) {
        if(lockReconnect) return;
        lockReconnect = true;
        setTimeout(function () {     //没连接上会一直重连，设置延迟避免请求过多
            createWebSocket(url);
            lockReconnect = false;
        }, 2000);
    }

    //心跳检测
    var heartCheck = {
        timeout: 10000,        //发一次心跳时间
        timeoutObj: null,
        serverTimeoutObj: null,
        reset: function(){
            clearTimeout(this.timeoutObj);
            clearTimeout(this.serverTimeoutObj);
            return this;
        },
        start: function(){
            var self = this;
            this.timeoutObj = setTimeout(function(){
                //这里发送一个心跳，后端收到后，返回一个心跳消息，
                //onmessage拿到返回的心跳就说明连接正常
                ws.send("在吗|"+userno);
                console.log("在吗")
                self.serverTimeoutObj = setTimeout(function(){//如果超过一定时间还没重置，说明后端主动断开了
                    ws.close();     //如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
                }, self.timeout)
            }, this.timeout)
        }
    }


    //将消息显示在网页上
    function setMessageInnerHTML(sendMessage) {
        document.getElementById('message').innerHTML += sendMessage + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        ws.close();
    }

    //发送消息
    function send() {
        var message = document.getElementById('text').value;//要发送的消息内容
        var jsname = document.getElementById('jsname').value;//接收人
        var now=getNowFormatDate();//获取当前时间
        document.getElementById('message').innerHTML += (now+"发送人："+userno+'<br/>'+"---"+message) + '<br/>';
        document.getElementById('message').style.color="red";
        var ToSendUserno=jsname;//接收人编号：xxx
        message=message+"|"+ToSendUserno//将要发送的信息和内容拼起来，以便于服务端知道消息要发给谁
        ws.send(message);
    }

    //获取当前时间
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
        return currentdate;
    }



</script>
</html>