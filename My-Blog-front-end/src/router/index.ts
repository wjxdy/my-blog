import { createRouter, createWebHistory } from 'vue-router'
import IndexView from '../views/index/index.vue'
import homePageView from "@/views/homePage/index.vue";
import blogTestView from '@/views/blogText/index.vue'
import blogEditView from '@/views/blogEdit/index.vue'
import loginView from "@/views/login/index.vue";
import newsView from "@/views/mapInfo/IpDeviceDetector.vue";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'index',
      component: IndexView,
      redirect: '/homePage',
      children: [
        {
          // 二级路由路径（注意：不要加 /，否则会被解析为根路径）
          path: 'homePage', 
          name: 'homePage',
          component: homePageView // 对应组件
        },
        {
          path: 'article/:id', 
          component: blogTestView // 对应组件
        },
        {
          path: 'ArticleEdit/:articleId?',
          name: 'ArticleEdit',
          component: blogEditView,

        }
      ]
      
    },
    {
      path: '/login',
      name: 'login',
      component: loginView
    },
    {
      path: '/news',
      name: 'news',
      component: newsView

    }
    

  ],
})

export default router
