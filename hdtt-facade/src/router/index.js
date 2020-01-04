import Vue from 'vue'
import VueRouter from 'vue-router'
import {Message} from 'element-ui'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: resolve => require(['../views/login.vue'], resolve),
  },
  {
    path: '/login',
    name: 'Login',
    component: resolve => require(['../views/login.vue'], resolve),
  },
  {
    name: '',
    path: '/layout',
    component: resolve => require(['../views/layout.vue'], resolve),
    children: [
      {
        name: '首页',
        path: '/home',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue')
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(route => {
    return route.name === 'Login'
  })) {
    next()
  } else {
    let userInfo = window.localStorage.getItem('user_info')
    if (userInfo) {
      next()
    } else {
      Message.error('请先登录！')
      router.replace({path: 'login'})
    }
  }
})
export default router
