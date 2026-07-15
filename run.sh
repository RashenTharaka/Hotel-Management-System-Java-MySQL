#!/usr/bin/env bash
if [ ! -f dist/Hotel_Management_System.jar ]; then
  echo "Project is not built yet. Run ./compile.sh first or build from NetBeans."
  exit 1
fi
java -cp "dist/Hotel_Management_System.jar:lib/*" hotel.management.system.HotelManagementSystem
