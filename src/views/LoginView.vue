<template>
  <div
    style="
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #f5f7fa;
    "
  >
    <el-card style="width: 400px; padding: 16px">
      <div style="text-align: center; margin-bottom: 28px">
        <div style="font-size: 32px; margin-bottom: 8px">🎓</div>
        <div style="font-size: 22px; font-weight: 600; color: #303133">校园人才管理系统</div>
        <div style="color: #909399; font-size: 13px; margin-top: 6px">请登录后继续</div>
      </div>

      <el-form :model="form" @keyup.enter="handleLogin">
        <el-form-item>
          <el-input v-model="form.username" placeholder="用户名" size="large" prefix-icon="User" />
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="form.password"
            type="password"
            placeholder="密码"
            size="large"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            style="width: 100%"
            :loading="loading"
            @click="handleLogin"
          >
            登 录
          </el-button>
        </el-form-item>
      </el-form>

      <div style="text-align: center; color: #bbb; font-size: 12px; margin-top: 8px">
        默认账号：admin &nbsp;|&nbsp; 密码：admin123
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api/auth'

const router = useRouter()
const loading = ref(false)
const form = ref({ username: '', password: '' })

const handleLogin = async () => {
  if (!form.value.username || !form.value.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }
  loading.value = true
  try {
    const res = await login(form.value)
    // 存到 localStorage
    localStorage.setItem('token', res.data.token)
    localStorage.setItem('username', res.data.username)
    ElMessage.success('登录成功')
    router.push('/members')
  } finally {
    loading.value = false
  }
}
</script>
