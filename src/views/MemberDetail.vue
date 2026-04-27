<template>
  <div v-if="member">
    <el-page-header
      @back="$router.back()"
      :content="member.name + ' 的详情'"
      style="margin-bottom: 20px"
    />

    <el-row :gutter="16">
      <!-- 基本信息 -->
      <el-col :span="10">
        <el-card header="基本信息">
          <div style="display: flex; align-items: center; gap: 16px; margin-bottom: 20px">
            <el-avatar :size="64" style="background: #409eff; font-size: 24px">
              {{ member.name?.charAt(0) }}
            </el-avatar>
            <div>
              <div style="font-size: 20px; font-weight: 600">{{ member.name }}</div>
              <div style="color: #999; margin-top: 4px">{{ member.school }}</div>
            </div>
          </div>
          <el-descriptions :column="1" border size="small">
            <el-descriptions-item label="专业">{{ member.major }}</el-descriptions-item>
            <el-descriptions-item label="年级">{{ member.grade }}</el-descriptions-item>
            <el-descriptions-item label="邮箱">{{ member.email }}</el-descriptions-item>
            <el-descriptions-item label="手机">{{ member.phone || '未填写' }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{
              ['未知', '男', '女'][member.gender]
            }}</el-descriptions-item>
          </el-descriptions>
          <div style="margin-top: 16px; color: #666; font-size: 14px; line-height: 1.8">
            {{ member.bio || '暂无简介' }}
          </div>
        </el-card>
      </el-col>

      <el-col :span="14">
        <!-- 技能标签 -->
        <el-card header="技能标签" style="margin-bottom: 16px">
          <div v-if="member.skills?.length" style="display: flex; flex-wrap: wrap; gap: 8px">
            <el-tag v-for="s in member.skills" :key="s.id" size="large">
              {{ s.name }}
              <span v-if="s.level" style="margin-left: 4px; color: #aaa; font-size: 11px">
                {{ ['', '了解', '熟悉', '熟练', '精通'][s.level] }}
              </span>
            </el-tag>
          </div>
          <el-empty v-else description="暂无技能" :image-size="60" />
        </el-card>

        <!-- 项目经历 -->
        <el-card header="项目经历">
          <div v-if="member.projects?.length">
            <el-timeline>
              <el-timeline-item
                v-for="p in member.projects"
                :key="p.id"
                :timestamp="p.startDate + (p.endDate ? ' ~ ' + p.endDate : ' ~ 至今')"
                placement="top"
              >
                <el-card shadow="never" style="border: 1px solid #eee">
                  <div style="display: flex; justify-content: space-between; align-items: center">
                    <span style="font-weight: 600">{{ p.name }}</span>
                    <el-tag size="small" :type="p.status === 1 ? 'success' : 'info'">
                      {{ p.status === 1 ? '进行中' : '已结束' }}
                    </el-tag>
                  </div>
                  <div style="color: #666; font-size: 13px; margin-top: 6px">
                    {{ p.description }}
                  </div>
                  <div v-if="p.role" style="margin-top: 6px">
                    <el-tag size="small" type="warning">{{ p.role }}</el-tag>
                  </div>
                  <div v-if="p.contribution" style="color: #888; font-size: 12px; margin-top: 6px">
                    {{ p.contribution }}
                  </div>
                </el-card>
              </el-timeline-item>
            </el-timeline>
          </div>
          <el-empty v-else description="暂无项目经历" :image-size="60" />
        </el-card>
      </el-col>
    </el-row>
  </div>
  <el-skeleton v-else :rows="8" animated />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getMemberById } from '../api/member'

const route = useRoute()
const member = ref(null)

onMounted(async () => {
  const res = await getMemberById(route.params.id)
  member.value = res.data
})
</script>
