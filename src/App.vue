<template>
  <div v-if="isLoginPage">
    <RouterView />
  </div>
  <el-container v-else style="min-height: 100vh">
    <el-header style="background: #409eff; display: flex; align-items: center; gap: 32px">
      <span style="color: #fff; font-size: 18px; font-weight: 600">🎓 校园人才管理系统</span>
      <el-menu
        mode="horizontal"
        :ellipsis="false"
        background-color="#409EFF"
        text-color="#fff"
        active-text-color="#ffd04b"
        :router="true"
        :default-active="$route.path"
        style="border: none; flex: 1"
      >
        <el-menu-item index="/members">成员管理</el-menu-item>
        <el-menu-item index="/projects">项目管理</el-menu-item>
      </el-menu>
      <div style="display: flex; align-items: center; gap: 12px">
        <span style="color: #fff; font-size: 13px">{{ username }}</span>
        <el-button size="small" @click="handleLogout">退出登录</el-button>
      </div>
    </el-header>
    <el-main style="background: #f5f7fa">
      <RouterView />
    </el-main>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { RouterView, useRoute, useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const isLoginPage = computed(() => route.path === '/login')
const username = computed(() => localStorage.getItem('username') || '')

const handleLogout = async () => {
  await ElMessageBox.confirm('确认退出登录？', '提示', { type: 'warning' })
  localStorage.removeItem('token')
  localStorage.removeItem('username')
  router.push('/login')
}
</script>
