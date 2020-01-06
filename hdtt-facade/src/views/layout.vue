<template>
  <div id="hdtt">
    <el-container>
      <el-header>
        <el-row>
          <el-col :span="4" class="title">互动天团</el-col>
          <el-col :span="16" class="nav">
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
              <el-menu-item index="/goods-list">商品列表</el-menu-item>
              <el-menu-item index="/consult">咨询</el-menu-item>
              <!-- <el-menu-item index="/complain">群发</el-menu-item> -->
            </el-menu>
          </el-col>
          <el-col :span="4" class="user-info">
            <div style="display: inline-block">
              <!-- <el-image :src="userInfo.img"></el-image> -->
              <span>{{userInfo.userName}}</span>
            </div>
            <el-button type="text" @click="leaveLogin">退出</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>

export default {
  name: 'app',
  props: ['appContent', 'loading', 'showLayout'],
  data: () => ({
    // 实际项目中的url字段应为路由路径，该处只是示例
    activeIndex: '',
    userInfo: {
      img: JSON.parse(window.localStorage.getItem('user_info')).icon,
      userName: JSON.parse(window.localStorage.getItem('user_info')).nickName,
      info: [{
        name: '手机号码',
        content: JSON.parse(window.localStorage.getItem('user_info')).phone
      }]
    }
  }),
  created() {
    this.activeIndex = this.$route.path
  },
  methods: {
    handleSelect(key) {
      this.$router.replace({path: key})
      console.log(key)
    },
    leaveLogin() {
      window.localStorage.clear()
      this.$router.replace({path: 'login'})
    }
  }
};
</script>

<style lang="scss">
#hdtt {
  .el-container {
    height: 100%;
  }
  height: 100%;
  padding: 0 20px 20px 20px;
  box-sizing: border-box;
  .el-header {
    padding: 0;
    box-sizing: border-box;
  }
  .el-main {
    padding: 5px 0 0 0;
  }
  .title {
    height: 60px;
    font-size: 16px;
    line-height: 60px;
    font-weight: 600;
    padding-left: 10px;
    border-bottom: 1px solid rgb(230, 230, 230);
  }
  .user-info {
    height: 60px;
    /* line-height: 60px; */
    text-align: right;
    padding-right: 10px;
    font-size: 14px;
    padding-top: 10px;
    box-sizing: border-box;
    border-bottom: 1px solid rgb(230, 230, 230);
    .el-button {
      font-size: 14px;
      margin-left: 10px;
    }
    span: {
      overflow: hidden;
      display: inline-block;
      border-radius: 50%;
    }
    .el-image {
      width: 40px;
      height: 40px;
    }
  }
}

</style>
