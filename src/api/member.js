import request from './request'

// 获取所有成员
export const getMembers = () => request.get('/api/members')

// 获取成员详情
export const getMemberById = (id) => request.get(`/api/members/${id}`)

// 多条件搜索
export const searchMembers = (dto) => request.post('/api/members/search', dto)

// 新增成员
export const saveMember = (data) => request.post('/api/members', data)

// 更新成员
export const updateMember = (id, data) => request.put(`/api/members/${id}`, data)

// 删除成员
export const deleteMember = (id) => request.delete(`/api/members/${id}`)

// 获取所有技能标签
export const getSkills = () => request.get('/api/skills')

// 获取所有项目
export const getProjects = () => request.get('/api/projects')
