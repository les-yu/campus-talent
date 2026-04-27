<template>
  <div>
    <div style="margin-bottom: 12px; display: flex; justify-content: space-between">
      <span style="color: #666">共 {{ projects.length }} 个项目</span>
    </div>

    <el-row :gutter="16">
      <el-col :span="12" v-for="p in projects" :key="p.id" style="margin-bottom: 16px">
        <el-card shadow="hover">
          <div
            style="
              display: flex;
              justify-content: space-between;
              align-items: center;
              margin-bottom: 10px;
            "
          >
            <span style="font-size: 16px; font-weight: 600">{{ p.name }}</span>
            <el-tag :type="p.status === 1 ? 'success' : 'info'" size="small">
              {{ p.status === 1 ? '进行中' : '已结束' }}
            </el-tag>
          </div>
          <div style="color: #666; font-size: 13px; margin-bottom: 10px; min-height: 40px">
            {{ p.description }}
          </div>
          <div style="font-size: 12px; color: #999">技术栈：{{ p.techStack }}</div>
          <div style="font-size: 12px; color: #bbb; margin-top: 6px">
            {{ p.startDate }} ~ {{ p.endDate || '至今' }}
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-empty v-if="projects.length === 0" description="暂无项目" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getProjects } from '../api/member'

const projects = ref([])

onMounted(async () => {
  const res = await getProjects()
  projects.value = res.data
})
</script>
