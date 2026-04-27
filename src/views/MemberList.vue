<template>
  <div>
    <!-- 搜索区 -->
    <el-card style="margin-bottom:16px">
      <el-form :model="searchForm" inline>
        <el-form-item label="姓名">
          <el-input v-model="searchForm.name" placeholder="请输入姓名" clearable style="width:140px"/>
        </el-form-item>
        <el-form-item label="学校">
          <el-input v-model="searchForm.school" placeholder="请输入学校" clearable style="width:160px"/>
        </el-form-item>
        <el-form-item label="技能">
          <el-select v-model="searchForm.skillIds" multiple placeholder="可多选" style="width:220px" clearable>
            <el-option v-for="s in allSkills" :key="s.id" :label="s.name" :value="s.id"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 工具栏 -->
    <div style="margin-bottom:12px; display:flex; justify-content:space-between; align-items:center">
      <span style="color:#666">共 {{ total }} 位成员</span>
      <el-button type="primary" @click="openDialog()">+ 新增成员</el-button>
    </div>

    <!-- 成员卡片列表 -->
    <el-row :gutter="16">
      <el-col :span="8" v-for="m in members" :key="m.id" style="margin-bottom:16px">
        <el-card shadow="hover" style="cursor:pointer" @click="goDetail(m.id)">
          <div style="display:flex; align-items:center; gap:12px; margin-bottom:12px">
            <el-avatar :size="48" style="background:#409EFF; font-size:18px">
              {{ m.name?.charAt(0) }}
            </el-avatar>
            <div>
              <div style="font-weight:600; font-size:15px">{{ m.name }}</div>
              <div style="color:#999; font-size:12px">{{ m.school }} · {{ m.grade }}</div>
            </div>
          </div>
          <div style="color:#666; font-size:13px; margin-bottom:10px; min-height:38px">
            {{ m.bio || '暂无简介' }}
          </div>
          <div style="display:flex; flex-wrap:wrap; gap:4px">
            <el-tag v-for="s in m.skills?.slice(0,4)" :key="s.id" size="small" type="info">
              {{ s.name }}
            </el-tag>
            <el-tag v-if="m.skills?.length > 4" size="small" type="info">
              +{{ m.skills.length - 4 }}
            </el-tag>
          </div>
          <div style="margin-top:10px; text-align:right" @click.stop>
            <el-button size="small" @click="openDialog(m)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(m.id)">删除</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-empty v-if="members.length === 0" description="暂无成员数据"/>

    <!-- 分页组件 -->
    <div style="display:flex; justify-content:center; margin-top:16px">
      <el-pagination
        v-model:current-page="searchForm.pageNum"
        v-model:page-size="searchForm.pageSize"
        :page-sizes="[6, 12, 24]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadMembers"
        @current-change="loadMembers"
      />
    </div>

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑成员' : '新增成员'" width="560px">
      <el-form :model="form" label-width="70px">
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="姓名"><el-input v-model="form.name"/></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别">
              <el-select v-model="form.gender" style="width:100%">
                <el-option label="未知" :value="0"/>
                <el-option label="男" :value="1"/>
                <el-option label="女" :value="2"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱"><el-input v-model="form.email"/></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机"><el-input v-model="form.phone"/></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学校"><el-input v-model="form.school"/></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="专业"><el-input v-model="form.major"/></el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年级"><el-input v-model="form.grade" placeholder="如：2022级"/></el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="技能">
              <el-select v-model="selectedSkillIds" multiple style="width:100%">
                <el-option v-for="s in allSkills" :key="s.id" :label="s.name" :value="s.id"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="简介">
              <el-input v-model="form.bio" type="textarea" :rows="3"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { searchMembers, saveMember, updateMember, deleteMember, getSkills } from '../api/member'

const router = useRouter()
const members = ref([])
const total = ref(0)
const allSkills = ref([])
const dialogVisible = ref(false)
const form = ref({})
const selectedSkillIds = ref([])
const searchForm = ref({
  name: '', school: '', skillIds: [],
  pageNum: 1, pageSize: 6
})

onMounted(async () => {
  await loadSkills()
  await loadMembers()
})

const loadSkills = async () => {
  const res = await getSkills()
  allSkills.value = res.data
}

const loadMembers = async () => {
  const dto = { ...searchForm.value }
  if (!dto.name) delete dto.name
  if (!dto.school) delete dto.school
  if (!dto.skillIds?.length) delete dto.skillIds
  const res = await searchMembers(dto)
  members.value = res.data.list
  total.value = res.data.total
}

const handleSearch = () => {
  searchForm.value.pageNum = 1  // 搜索时回到第一页
  loadMembers()
}

const handleReset = () => {
  searchForm.value = { name: '', school: '', skillIds: [], pageNum: 1, pageSize: 6 }
  loadMembers()
}

const goDetail = (id) => router.push(`/members/${id}`)

const openDialog = (member = null) => {
  if (member) {
    form.value = { ...member }
    selectedSkillIds.value = member.skills?.map(s => s.id) || []
  } else {
    form.value = { gender: 1 }
    selectedSkillIds.value = []
  }
  dialogVisible.value = true
}

const handleSave = async () => {
  const payload = { member: form.value, skillIds: selectedSkillIds.value }
  if (form.value.id) {
    await updateMember(form.value.id, payload)
  } else {
    await saveMember(payload)
  }
  ElMessage.success('保存成功')
  dialogVisible.value = false
  loadMembers()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确认删除该成员？', '提示', { type: 'warning' })
  await deleteMember(id)
  ElMessage.success('删除成功')
  loadMembers()
}
</script>
