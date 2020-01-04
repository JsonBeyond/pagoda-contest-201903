<template>
    <div class="consult">
      <el-container class="box">
        <el-main>
          <el-container>
            <el-main class="consult-box">
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
                  <el-button type="primary" @click="websocketsend">发送</el-button>
                </el-aside>
              </el-container>
            </el-footer>
          </el-container>
        </el-main>
        <el-aside width="240px">
          <el-button type="primary" @click="dialogVisible = true">搜索商品</el-button>
        </el-aside>
      </el-container>
      <el-dialog
        title="搜索商品"
        :visible.sync="dialogVisible"
        width="80%"
      >
        <el-form :inline="true" :model="params" class="panel" label-width="100px">
          <el-form-item label="商品名称">
             <el-input v-model="params.goodsName" placeholder="交易序号"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="seachGoods">查询</el-button>
          </el-form-item>
        </el-form>
        <el-table
          :data="goodsList"
          max-height="600"
          show-summary>
          <el-table-column prop="goodsName" label="商品名称"></el-table-column>
          <el-table-column prop="price" label="单价"></el-table-column>
          <el-table-column prop="totalMoney" label="总价"></el-table-column>
          <el-table-column prop="count" label="数量"></el-table-column>
          <el-table-column prop="countUnit" label="数量单位"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button @click="consultGoods(scope)" type="text">咨询商品
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <el-row class="page" type="flex" justify="center">
          <el-pagination v-show="goodsList.length>0" @size-change="handleSizeChange" @current-change="handleCurrentChange"
                         background layout="total, sizes, prev, pager, next, jumper"
                        :total="page.total" :current-page.sync="page.currentPage"></el-pagination>
        </el-row>
      </el-dialog>
    </div>
</template>
<script>
import {getGoodsList} from '@/api'
export default {
  data() {
    return {
      websocket: null,
      textarea: '',
      msgsList: [],
      msge: '',
      img: JSON.parse(window.localStorage.getItem('user_info')).icon,
      dialogVisible: false,
      goodsList: [],
      page: {
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      params: {
        gooodsName: ''
      }
    }
  },
  created(){
    //页面刚进入时开启长连接
    this.initWebSocket()
    this.seachGoods()
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
      this.websocket.send({});
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
    },
    websocketsend(agentData){ //数据发送 
      this.websocket.send({
        message: this.textarea,
        userId: JSON.parse(window.localStorage.getItem('user_info')).id,
        type: 'common'
      });
      this.msge = this.textarea;
      this.textarea = '';
    },
    websocketclose(e){ //关闭 
      console.log("connection closed (" + e.code + ")"); 
    },
    questionSend(name) {
      this.websocket.send({
        message: this.textarea,
        userId: JSON.parse(window.localStorage.getItem('user_info')).id,
        type: 'common'
      });
      this.msge = name;
    },
    seachGoods() {
      getGoodsList({
        page: this.page.currentPage,
        pageSize: this.page.pageSize,
        userId: JSON.parse(window.localStorage.getItem('user_info')).id,
        goodsName: this.params.gooodsName
      }).then(res => {
        this.goodsList = res.result.list
        this.page.total = res.result.totalRow
      })
    },
    handleSizeChange (val) {
      this.page.pageSize = val
      this.page.currentPage = 1
      this.seachGoods()
    },
    handleCurrentChange (val) {
      this.page.currentPage = val;
      this.seachGoods()
    },
    consultGoods(row) {
      this.websocket.send({
        message: row.row.id,
        userId: JSON.parse(window.localStorage.getItem('user_info')).id,
        type: 'order'
      });
      this.msge = row.row.goodsName
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