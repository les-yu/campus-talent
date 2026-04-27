import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),
      meta: { requiresAuth: false },
    },
    {
      path: '/',
      redirect: '/members',
    },
    {
      path: '/members',
      name: 'members',
      component: () => import('../views/MemberList.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/members/:id',
      name: 'member-detail',
      component: () => import('../views/MemberDetail.vue'),
      meta: { requiresAuth: true },
    },
    {
      path: '/projects',
      name: 'projects',
      component: () => import('../views/ProjectList.vue'),
      meta: { requiresAuth: true },
    },
  ],
})

// 全局路由守卫：未登录跳转到 /login
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else if (to.path === '/login' && token) {
    next('/members') // 已登录不用再去登录页
  } else {
    next()
  }
})

export default router
