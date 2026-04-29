#!/bin/bash
# ================================================
#  校园人才管理系统 - 前端构建脚本
#  用法: bash build.sh
# ================================================

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${GREEN}===== 校园人才管理系统 - 前端构建 =====${NC}"
echo "时间: $(date '+%Y-%m-%d %H:%M:%S')"

# ----------------
# 1. 检查 Node 环境
# ----------------
echo -e "\n${YELLOW}[1/3] 检查环境...${NC}"
node -v
npm -v
if [ $? -ne 0 ]; then
  echo -e "${RED}Node.js 未安装，请先安装 Node.js${NC}"
  exit 1
fi

# ----------------
# 2. 安装依赖
# ----------------
echo -e "\n${YELLOW}[2/3] 安装依赖...${NC}"
npm install
if [ $? -ne 0 ]; then
  echo -e "${RED}依赖安装失败${NC}"
  exit 1
fi
echo -e "${GREEN}依赖安装成功${NC}"

# ----------------
# 3. 构建生产包
# ----------------
echo -e "\n${YELLOW}[3/3] 构建生产包...${NC}"
npm run build
if [ $? -ne 0 ]; then
  echo -e "${RED}构建失败${NC}"
  exit 1
fi

# 统计构建产物大小
DIST_SIZE=$(du -sh dist/ 2>/dev/null | cut -f1)
echo -e "\n${GREEN}===== 构建成功 =====${NC}"
echo "产物目录: dist/"
echo "产物大小: $DIST_SIZE"
echo "部署方式: 将 dist/ 目录上传至服务器或推送至 Vercel"