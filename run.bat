@echo off
if not exist dist\Hotel_Management_System.jar (
  echo Project is not built yet. Run compile.bat first or build from NetBeans.
  pause
  exit /b 1
)
java -cp "dist\Hotel_Management_System.jar;lib\*" hotel.management.system.HotelManagementSystem
pause
