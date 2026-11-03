
@echo off
cls

echo ==============================
echo Building Angular application
echo ==============================

cd /d "web"

call npm install
if errorlevel 1 exit /b 1

call npm run build
if errorlevel 1 exit /b 1

echo Angular build completed!

cd /d ".."

echo ==============================
echo Building Spring Boot application
echo ==============================

cd /d "service"

call mvn clean package -DskipTests
if errorlevel 1 exit /b 1

echo Spring Boot build completed!

echo ==============================
echo Starting applications
echo ==============================

cd /d ".."

start "Angular Application" cmd /k "cd /d "web" && npm start"

start "Spring Boot Application" cmd /k "cd /d "service" && java -jar target/library-management-service.jar"

echo Both applications started!
