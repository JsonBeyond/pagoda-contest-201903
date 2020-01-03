<template>
  <div class="login-wrap">
    <div class="login-content">
      <h1 class="login-title">登录</h1>
      <div class="login-body">
        <el-form style="width:100%;" label-width="0"
                 @submit.native.prevent>
          <el-alert :title="errMsg"
                    v-if="errMsg"
                    class="mar-b-xs"
                    type="error"
                    @close="resetMsg">
          </el-alert>
          <el-form-item>
            <el-input class="login-input"
                      placeholder="请输入用户名"
                      v-model="username"
                      size="medium">
              <i slot="prefix"
                 class="el-icon-user-solid"></i>
            </el-input>
          </el-form-item>
          <el-form-item style="margin-bottom:36px">
            <el-input class="login-input"
                      type="password"
                      v-model="password"
                      placeholder="请输入密码"
                      size="medium">
              <i slot="prefix"
                 class="el-icon-s-cooperation"></i>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="danger"
                       :loading="submitted"
                       native-type="submit"
                       size="medium"
                       @click="submit()"
                       style="width:100%;">{{submitted?'正在登录':'登&nbsp;&nbsp;录'}}</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
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
        login({phone: this.username, password: this.password}).then(res => {
          if (res.status === 10000) {
            this.$router.push()
          }
        })
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
