<template>
<div>
  <!-- <pagoda-card-list v-infinite-scroll="load" infinite-scroll-disabled="disabled"> -->
  <pagoda-card-list>
    <pagoda-card-list-item v-for="item in 10" :key="item">
      <div class="pagoda-card-list-item-body">
        
      </div>
      <div class="pagoda-card-list-item-footer">
        <div class="pagoda-card-list-operate">
          <el-button type="text">下单</el-button>
        </div>
        <!-- <div class="pagoda-card-list-operate" @click="setScene(item.sceneID)">
          <el-button type="text" :disabled="item.status === '3'">配置</el-button>
        </div>
        <div class="pagoda-card-list-operate">
          <el-button type="text" :disabled="item.status === '3'" @click="cancel(item)">作废</el-button>
        </div>
        <div class="pagoda-card-list-operate">
          <el-button type="text" icon="el-icon-share" disabled></el-button>
        </div> -->
      </div>
    </pagoda-card-list-item>
  </pagoda-card-list>
  <!-- <p v-if="loading">加载中...</p>
  <p v-if="noMore">没有更多了</p> -->
</div>

</template>

<script>
import { login } from '@/api'
import { log } from 'util'
export default {
  name: 'login',
  data () {
    return {
      username: '',
      password: '',
      errMsg: '',
      submitted: false
    }
  },
  methods: {
    submit () {
      if (!this.username) {
        this.errMsg = '请输入用户名'
      } else if (!this.password) {
        this.errMsg = '请输入密码'
      } else {
        console.log(this.username)
        // this.submitted = true
        login({phone: this.username, password: this.password})
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style lang="scss">
$primary: #666;
.login-wrap,
.login-wrap * {
  box-sizing: border-box;
}
.login-wrap {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background-color: $primary;
  padding: 20px;
  background-image: url('../assets/bg_login.jpg');
}
.login-content {
  width: 100%;
  max-width: 640px;
  .iconfont {
    font-size: 18px;
    margin-left: 2px;
    color: $primary;
  }
  .login-input input {
    border: none;
    background-color: none;
    border-bottom: 1px solid $primary;
    border-radius: 0;
    background: transparent;
    &:focus {
      border-color: $primary;
      + .el-input__prefix .iconfont {
        color: $primary;
      }
    }
  }
}
.login-title {
  font-size: 24px;
  margin-bottom: 15px;
  text-align: center;
  color: #fff;
}
.login-body {
  height: 300px;
  border-radius: 4px;
  background-color: rgba(255, 255, 255, 0.95);
  padding: 0 40px;
  -webkit-box-shadow: 0px 0px 5px #999;
  box-shadow: 0px 0px 5px #999;
  display: flex;
  align-items: center;
  justify-content: center;
  .el-form-item--small.el-form-item {
    margin-bottom: 25px;
  }
}
@media screen and (max-width: 767px) {
  .login-title {
    font-size: 18px;
  }
}
@media screen and (min-width: 768px) {
  .login-content {
    max-width: 375px;
    margin-left: 50%;
    margin-top: 10%;
  }
}
</style>
