<template>
    <div class="consult">
      <el-container class="box">
        <el-main>
          <el-container>
            <el-main class="consult-box" id="chatBox-content-demo">
              <div v-for="(item, idx) of msgsList" :key="idx" class="news-box">
                <div class="right" v-if="item.sendNews">
                  <el-container>
                    <el-main>
                      <el-row>
                        {{item.sendNews}}
                      </el-row>
                    </el-main>
                    <el-aside width="50px">
                      <img :src="img" />
                    </el-aside>
                  </el-container>
                </div>
                <el-container class="left">
                  <el-aside width="50px">
                    <img src="../assets/logo.png" />
                  </el-aside>
                  <el-main>
                    <el-row v-if="item.news">
                      <div v-for="(it, index) in item.news" :key="index">
                        {{it}}
                      </div>
                    </el-row>
                    <el-row v-for="(it, index) in item.questionList" :key="index">
                      <el-button type="text" @click="questionSend(it.question)">{{it.question}}</el-button>
                    </el-row>
                  </el-main>
                </el-container>
              </div>
            </el-main>
            <el-footer height="66px" class="consult-input">
              <el-container>
                <el-main>
                  <el-input
                    type="textarea"
                    rows="3"
                    placeholder="请输入内容"
                    v-model="textarea">
                  </el-input>
                </el-main>
                <el-aside width="100px">
                  <el-button type="primary" @click="sendAll">发送</el-button>
                </el-aside>
              </el-container>
            </el-footer>
          </el-container>
        </el-main>
      </el-container>
    </div>
</template>
<script>
import {sendAll} from '@/api'
export default {
  data() {
    return {
      websocket: null,
      textarea: '',
      msgsList: [],
      msge: '',
      img: JSON.parse(window.localStorage.getItem('user_info')).icon,
    }
  },
  created(){
    //页面刚进入时开启长连接
    this.initWebSocket()
  },
  destroyed() {//页面销毁时关闭长连接
    this.websocketclose();
  },
  methods: {
    initWebSocket(){ //初始化weosocket 
      const wsuri = 'ws://192.168.7.218:8081/websocket';//ws地址
      this.websocket = new WebSocket(wsuri); 
      this.websocket.onopen = this.websocketonopen;
      this.websocket.onerror = this.websocketonerror;
      this.websocket.onmessage = this.websocketonmessage; 
      this.websocket.onclose = this.websocketclose;
    }, 
    websocketonopen() {
      console.log("WebSocket连接成功");
      // this.websocket.send(JSON.stringify({}));
    },
    websocketonerror(e) { //错误
      console.log("WebSocket连接发生错误");
    },
    websocketonmessage(e){
      //数据接收 
      const data = JSON.parse(e.data)
      console.log(data.replyMessage, data.replyMessage.split('@@'))
      let obj = {
        news: data.replyMessage ? data.replyMessage.split('@@') : '',
        questionList: data.questionList,
        sendNews: this.msge
      }
      this.msge = '';
      this.msgsList.push(obj)
      this.$nextTick(() => {
        let container = this.$el.querySelector("#chatBox-content-demo");
        container.scrollTop = container.scrollHeight;
      })
    },
    websocketclose(e){ //关闭 
      console.log("connection closed (" + e.code + ")"); 
    },
    questionSend(name) {
      this.websocket.send(JSON.stringify({
        message: name,
        userId: JSON.parse(window.localStorage.getItem('user_info')).id,
        type: 'common'
      }));
      this.msge = name;
    },
    sendAll(agentData){ //数据发送 
      sendAll({message: this.textarea}).then(res => {
        console.log(res)
      });
      this.msge = '';
      this.textarea = '';
    }
  }
}
</script>

<style scoped lang="scss">
.consult {
  height: 100%;
  background-image: url('../assets/timg.jpeg');
  background-repeat: repeat;
  padding: 20px 0 40px;
  box-sizing: border-box;
  .el-container {
    height: 100%;
  }
  .box {
    background: #fff;
    width: 80%;
    margin: auto;
    .consult-box {
      background: #fafafa;
      box-shadow: -20px -2px 15px -13px rgba(221,223,230,0.6) inset;
      padding: 10px;
    }
    .el-footer {
      padding: 0;
      .el-textarea {
        border: none;
        width: 100%;
        height: 100%;
      }
      /deep/ .el-textarea__inner {
        /* height: 100%; */
        border: none;
      }
      .el-aside {
        text-align: center;
        line-height: 66px;
      }
    }
  }
  .news-box {
    font-size: 12px;
    color: #999;
    line-height: 16px;
    .left {
      height: auto;
      margin-bottom: 10px;
      .el-main {
        max-width: 400px;
        border: 1px solid #afafaf;
      }
      img {
        width: 30px;
        height: 30px;
        border-radius: 50%;
      }
      .el-row {
        padding-left: 10px;
        border-bottom: 1px solid #afafaf;
      }
      .el-row:last-child {
        border: none;
      }
      .el-aside {
        text-align: center;
      }
    }
    .right {
      overflow: hidden;
      margin-bottom: 10px;
      img {
        width: 30px;
        height: 30px;
        border-radius: 50%;
      }
      .el-container {
        max-width: 400px;
        float: right;
      }
      .el-aside {
        text-align: center;
      }
      .el-row {
        padding: 10px;
        line-height: 18px;
        border: 1px solid #d9d9d9;
        background: rgba(245, 247, 250, 1);
      }
    }
  }
}
</style>