import request from './request'

export const login = (data) => request.post('/api/auth/login', data)
