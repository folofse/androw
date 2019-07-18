#!/bin/bash
ROOT_DIR=$(cd "$(dirname "$0")"; pwd)"/" 

cd $ROOT_DIR
react-native run-android log-android
exit