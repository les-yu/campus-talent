#!/bin/bash
# ================================================
#  校园人才管理系统 - 后端自动化部署脚本
#  用法: bash deploy.sh
# ================================================

# 颜色输出
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # 无颜色

echo -e "${GREEN}===== 校园人才管理系统 - 开始部署 =====${NC}"
echo "时间: $(date '+%Y-%m-%d %H:%M:%S')"

# ----------------
# 1. 拉取最新代码
# ----------------
echo -e "\n${YELLOW}[1/4] 拉取最新代码...${NC}"
git pull origin master
if [ $? -ne 0 ]; then
  echo -e "${RED}代码拉取失败，终止部署${NC}"
  exit 1
fi
echo -e "${GREEN}代码拉取成功${NC}"

# ----------------
# 2. Maven 打包
# ----------------
echo -e "\n${YELLOW}[2/4] Maven 打包中（跳过测试）...${NC}"
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
  echo -e "${RED}打包失败，终止部署${NC}"
  exit 1
fi
echo -e "${GREEN}打包成功${NC}"

# ----------------
# 3. 停止旧进程
# ----------------
echo -e "\n${YELLOW}[3/4] 检查并停止旧进程...${NC}"
PID=$(pgrep -f "talent.*\.jar")
if [ -n "$PID" ]; then
  echo "发现旧进程 PID: $PID，正在停止..."
  kill -15 $PID
  sleep 3
  # 检查进程是否还存在，强制杀死
  if kill -0 $PID 2>/dev/null; then
    echo "进程未响应，强制终止..."
    kill -9 $PID
  fi
  echo -e "${GREEN}旧进程已停止${NC}"
else
  echo "未发现旧进程，跳过"
fi

# ----------------
# 4. 启动新版本
# ----------------
echo -e "\n${YELLOW}[4/4] 启动新版本...${NC}"

# 创建日志目录
mkdir -p logs

# 找到打包好的 jar 文件
JAR_FILE=$(ls target/*.jar 2>/dev/null | grep -v "original" | head -1)
if [ -z "$JAR_FILE" ]; then
  echo -e "${RED}未找到 jar 文件，部署失败${NC}"
  exit 1
fi

echo "启动文件: $JAR_FILE"

# 后台启动，日志写入 logs/app.log
nohup java -jar $JAR_FILE \
  --spring.profiles.active=prod \
  > logs/app.log 2>&1 &

NEW_PID=$!
echo "新进程 PID: $NEW_PID"

# 等待 5 秒确认启动成功
sleep 5
if kill -0 $NEW_PID 2>/dev/null; then
  echo -e "\n${GREEN}===== 部署成功 =====${NC}"
  echo "PID: $NEW_PID"
  echo "日志: tail -f logs/app.log"
else
  echo -e "\n${RED}===== 启动失败，请查看日志 =====${NC}"
  echo "日志: cat logs/app.log"
  exit 1
fi