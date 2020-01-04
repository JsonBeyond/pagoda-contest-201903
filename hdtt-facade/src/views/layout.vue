<template>
  <div id="portal">
    <pagoda-layout
      :side-menu="sideMenu"
      :user-info="userInfo"
      :on-logout="handleLogout"
      :on-operate-item-click="handleOperateItemClick"
      :side-menu-props="sideMenuProps"
      fullscreen
      @tabs-change="handleTabsChange"
    >
      <!-- 头部的logo名称插槽 -->
      <div slot="header-left" slot-scope="scope" style="font-size: 0">
        <img
          src="http://erp2.hwdev.pagoda.com.cn/store/static/img/left_nav_logo.5da9ac7.png"
          class="logo"
        />
        <span class="logo-text" v-show="!scope.collapse">互动天团</span>
      </div>
      <!-- 内容插槽 -->
      <div class="content" v-loading="loading">
        <router-view></router-view>
      </div>
    </pagoda-layout>
  </div>
</template>

<script>

export default {
  name: 'app',
  props: ['appContent', 'loading', 'showLayout'],
  data: () => ({
    // 在当前没有菜单激活的情况下 默认展开第一个菜单
    sideMenuProps: {
      defaultOpeneds: ['0']
    },
    // 实际项目中的url字段应为路由路径，该处只是示例
    sideMenu: [
      {
        icon: 'el-icon-location',
        label: '配置主结构页',
        subMenu: [
          {
            tabName: '1111',
            label: '11111',
            url: '/home'
          },
          {
            tabName: '2222',
            label: '22222',
            url: '/tools'
          },
          {
            tabName: '33333',
            label: '33333',
            url: '/joint'
          }
        ]
      }
    ],
    userInfo: {
      userName: JSON.parse(window.localStorage.getItem('user_info')).userName
    }
  }),
  methods: {
    handleTabsChange(data) {
      // 在此处可以进行路由跳转
      // window.history.pushState({}, data.label, data.url);
      this.$router.push(data.url);
    },
    handleLogout() {
      window.localStorage.clear()
      this.$router.replace({path: 'login'})
    },
    handleOperateItemClick() {
      // 当前点击的操作项配置
    }
  }
};
</script>

<style lang="scss">
.el-container.is-vertical {
  height: 100vh;
}
#portal .content {
  height: 100%;
  padding: 20px;
}

</style>
