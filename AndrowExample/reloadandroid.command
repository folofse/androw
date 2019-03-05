#!/bin/bash
ROOT_DIR=$(cd "$(dirname "$0")"; pwd)"/" 

cd $ROOT_DIR
adb shell am force-stop se.folof.androwexample && adb shell am start -n se.folof.androwexample/se.folof.androwexample.MainActivity
exit