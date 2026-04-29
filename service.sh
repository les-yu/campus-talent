#!/bin/bash
# ================================================
#  校园人才管理系统 - 服务管理脚本
#  用法:
#    bash service.sh start    # 启动服务
#    bash service.sh stop     # 停止服务
#    bash service.sh restart  # 重启服务
#    bash service.sh status   # 查看状态
#    bash service.sh log      # 查看日志
# ================================================

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

JAR_FILE=$(ls target/*.jar 2>/dev/null | grep -v "original" | head -1)
LOG_FILE="logs/app.log"

# 获取进程 PID
get_pid() {
  pgrep -f "talent.*\.jar"
}

# 启动服务
start() {
  PID=$(get_pid)
  if [ -n "$PID" ]; then
    echo -e "${YELLOW}服务已在运行，PID: $PID${NC}"
    return
  fi
  mkdir -p logs
  echo -e "${GREEN}启动服务...${NC}"
  nohup java -jar $JAR_FILE > $LOG_FILE 2>&1 &
  sleep 3
  PID=$(get_pid)
  if [ -n "$PID" ]; then
    echo -e "${GREEN}启动成功，PID: $PID${NC}"
  else
    echo -e "${RED}启动失败，查看日志: tail -f $LOG_FILE${NC}"
  fi
}

# 停止服务
stop() {
  PID=$(get_pid)
  if [ -z "$PID" ]; then
    echo -e "${YELLOW}服务未运行${NC}"
    return
  fi
  echo "停止服务，PID: $PID"
  kill -15 $PID
  sleep 3
  if kill -0 $PID 2>/dev/null; then
    kill -9 $PID
    echo -e "${GREEN}服务已强制停止${NC}"
  else
    echo -e "${GREEN}服务已正常停止${NC}"
  fi
}

# 查看状态
status() {
  PID=$(get_pid)
  if [ -n "$PID" ]; then
    echo -e "${GREEN}服务运行中，PID: $PID${NC}"
    # 显示内存和CPU占用
    ps -p $PID -o pid,pcpu,pmem,etime --no-headers
  else
    echo -e "${RED}服务未运行${NC}"
  fi
}

# 查看日志
log() {
  if [ -f "$LOG_FILE" ]; then
    tail -f $LOG_FILE
  else
    echo -e "${RED}日志文件不存在: $LOG_FILE${NC}"
  fi
}

# 主命令分发
case "$1" in
  start)   start ;;
  stop)    stop ;;
  restart) stop; sleep 2; start ;;
  status)  status ;;
  log)     log ;;
  *)
    echo "用法: bash service.sh {start|stop|restart|status|log}"
    exit 1
    ;;
esac