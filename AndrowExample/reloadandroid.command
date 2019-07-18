#!/bin/bash
ROOT_DIR=$(cd "$(dirname "$0")"; pwd)"/" 

cd $ROOT_DIR
adb shell am force-stop air.no.jensen.adjustablesleep && adb shell am start -n air.no.jensen.adjustablesleep/air.no.jensen.adjustablesleep.MainActivity
exit