#!/bin/bash

set -e

# Resolve the root directory where this script is located
ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"

FRONTEND_DIR="$ROOT_DIR/web"
BACKEND_DIR="$ROOT_DIR/service"

echo "=================================="
echo " Building Angular application"
echo "=================================="

cd "$FRONTEND_DIR"

npm install
npm run build

echo "Angular build completed!"

echo "=================================="
echo " Building Spring Boot application"
echo "=================================="

cd "$BACKEND_DIR"

mvn clean package -DskipTests

echo "Spring Boot build completed!"

echo "=================================="
echo " Starting applications"
echo "=================================="

# Start Angular in the background
cd "$FRONTEND_DIR"
npm start &

ANGULAR_PID=$!

# Start Spring Boot in the background
cd "$BACKEND_DIR"
java -jar target/*.jar &

SPRING_PID=$!

echo "Angular PID: $ANGULAR_PID"
echo "Spring Boot PID: $SPRING_PID"

# Stop both applications when script receives Ctrl+C
cleanup() {
    echo "Stopping applications..."
    kill "$ANGULAR_PID" "$SPRING_PID" 2>/dev/null || true
}

trap cleanup SIGINT SIGTERM

wait
