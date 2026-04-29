# 校园人才管理系统 - 部署文档

## 项目简介

校园人才管理系统，基于 Spring Boot + MyBatis + Vue3 实现前后端分离架构。支持成员信息管理、技能标签多对多关系、多条件组合搜索、JWT 登录鉴权、分页展示等功能。

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | Spring Boot 3.2、MyBatis、PageHelper |
| 数据库 | MySQL 8.0 |
| 前端 | Vue3、Element Plus、Axios |
| 鉴权 | JWT + BCrypt |
| 部署 | Railway（后端+数据库）、Vercel（前端） |

## 仓库结构

```
campus-talent/
├── master 分支    # 后端 Spring Boot 代码
└── frontend 分支  # 前端 Vue3 代码
```

---

## 部署流程

### 一、数据库部署（Railway MySQL）

1. 访问 [railway.app](https://railway.app)，用 GitHub 账号登录
2. 点击 **New Project** → 选择 **Deploy MySQL**，等待数据库启动
3. 点击 MySQL 服务 → **Variables** 标签，获取连接信息：
   - `MYSQL_PUBLIC_URL`：公网连接地址
   - `MYSQLPASSWORD`：数据库密码
   - `MYSQLUSER`：root
   - `MYSQLPORT`：外网端口（非 3306）
4. 在 DataGrip 新建连接，填入以上信息，连接 Railway 数据库
5. 新建查询窗口，执行建表 SQL 脚本（从 `USE railway` 开始，不要执行 DROP/CREATE DATABASE）

### 二、后端部署（Railway Spring Boot）

1. 修改本地 `application.yml` 数据库连接为 Railway 地址：
```yaml
spring:
  datasource:
    url: jdbc:mysql://shuttle.proxy.rlwy.net:端口/railway?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root
    password: Railway数据库密码
```
2. 删除 MyBatis `log-impl` 日志配置（生产环境不需要）
3. 提交代码到 GitHub master 分支
4. Railway 控制台 → **New** → **GitHub Repo** → 选 `campus-talent` 仓库，选 `master` 分支 → Deploy
5. 构建完成后进入 **Settings → Networking → Generate Domain**，获取后端公网域名

### 三、前端部署（Vercel）

1. 修改 `src/api/request.js` 中的 `baseURL` 为 Railway 后端域名：
```js
const request = axios.create({
  baseURL: 'https://你的Railway后端域名.up.railway.app',
  timeout: 10000
})
```
2. 在项目根目录新建 `vercel.json`，解决刷新 404 问题：
```json
{
  "rewrites": [
    { "source": "/(.*)", "destination": "/index.html" }
  ]
}
```
3. 提交代码到 GitHub frontend 分支
4. 访问 [vercel.com](https://vercel.com)，用 GitHub 登录 → **New Project** → 导入 `campus-talent` 仓库
5. Framework 选 **Vite**，Branch 选 **frontend** → Deploy
6. 部署完成后获得域名：`campus-talent.vercel.app`

---

## 部署过程中遇到的问题及解决方案

### 问题 1：git push 推送失败（Connection was reset）

**原因**：国内网络访问 GitHub 不稳定，连接被重置。

**解决方案**：
```bash
# 设置代理（需要开启梯子，端口改为实际端口）
git config --global http.proxy http://127.0.0.1:7890
git config --global https.proxy http://127.0.0.1:7890
git push
```

---

### 问题 2：git push 报错 `src refspec main does not match any`

**原因**：本地默认分支名是 `master`，不是 `main`。

**解决方案**：
```bash
git push -u origin master
```

---

### 问题 3：前端访问子路径刷新出现 404

**原因**：Vue3 使用 History 路由模式，Vercel 不知道把所有路径都指向 `index.html`。

**解决方案**：在项目根目录新建 `vercel.json`：
```json
{
  "rewrites": [
    { "source": "/(.*)", "destination": "/index.html" }
  ]
}
```

---

### 问题 4：登录时提示"用户名或密码错误"

**原因**：数据库中 `sys_user` 表的密码字段存储格式与后端比对方式不一致（BCrypt 哈希 vs 明文）。

**解决方案**：
- 统一使用明文存储时，后端用 `equals()` 比对
- 统一使用 BCrypt 时，需通过 `/api/auth/encode` 接口生成正确哈希后更新数据库
- 线上数据库密码通过 DataGrip 连接 Railway MySQL 后执行 UPDATE 语句更新：
```sql
UPDATE sys_user SET password = 'admin123' WHERE username = 'admin';
```

---

### 问题 5：Railway 数据库导入 SQL 时报错

**原因**：SQL 脚本中包含 `DROP DATABASE` 和 `CREATE DATABASE`，Railway 不允许操作数据库本身。

**解决方案**：从 `USE railway` 开始执行，跳过建库语句，同时在脚本开头加：
```sql
SET sql_mode = '';
SET GLOBAL sql_mode = '';
SET foreign_key_checks = 0;
USE railway;
```

---

### 问题 6：本地新增的数据在线上不存在

**原因**：本地开发连接的是本地 MySQL，线上使用 Railway MySQL，两个数据库相互独立。

**解决方案**：在本地数据库查询需要迁移的数据，手动生成 INSERT 语句，在 DataGrip 连接 Railway 数据库后执行导入。

---

### 问题 7：JWT 拦截器导致跨域（CORS）失败

**原因**：浏览器发送跨域请求前会先发送 OPTIONS 预检请求，JWT 拦截器将其拦截并返回 401，导致后续真实请求无法发出。

**解决方案**：在 `JwtFilter.java` 中放行 OPTIONS 请求：
```java
if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
    response.setStatus(HttpServletResponse.SC_OK);
    filterChain.doFilter(request, response);
    return;
}
```

---

## 线上访问地址

| 服务 | 地址 |
|------|------|
| 前端 | https://campus-talent.vercel.app |
| 后端 | https://desirable-presence-production-80d8.up.railway.app |

## 默认账号

| 用户名 | 密码 |
|--------|------|
| admin | admin123 |
